/*
 *    Copyright 2016 Lawrence Lai
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.oriole.document.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonUtils;
import org.oriole.document.SqlCI;
import org.oriole.document.SqlCIGroup;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.SqlCIGroupRepository;
import org.oriole.document.repository.SqlCIRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlCIController {

    @Autowired
    private SqlCIGroupRepository sqlCIGroupRepository;

    @Autowired
    private SqlCIRepository sqlCIRepository;

    @Autowired
    private SequenceDao sequenceDao;

    // Exception

    @ExceptionHandler(InputDataException.class)
    public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
        ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage(),
                request.getRequestURL().append("/error").toString());
        return error;
    }

    @RequestMapping("/api/sqlCI/searchByGroupId")
    public
    @ResponseBody
    List<SqlCI> getSqlCIListByGroupId(@RequestParam long sqlCIGroupId) {
        return sqlCIRepository.findByGroupID(sqlCIGroupId);
    }

    @RequestMapping("/api/sqlCI/searchById")
    public
    @ResponseBody
    SqlCI getSqlCIListById(@RequestParam long sqlCiId) {
        return sqlCIRepository.findById(sqlCiId);
    }

    @RequestMapping("/api/sqlCI/create")
    public
    @ResponseBody
    SqlCI createSqlCI(
            @RequestParam Long groupId,
            @RequestParam String createdBy,
            @RequestParam String type,
            @RequestParam String statement,
            @RequestParam Boolean active,
            String description) {

        SqlCIGroup sqlCIGroup = sqlCIGroupRepository.findById(groupId);

        CommonUtils.validateNullObj(sqlCIGroup, "SQL CI Group ID not found");

        List<SqlCI> sqlCIList = sqlCIRepository.findByGroupID(sqlCIGroup.getId());
        SqlCI sqlCI = new SqlCI(sequenceDao.getNextSequenceId(DatabaseSequence.SQL_CI.getSequenceName()),
                sqlCIGroup.getId());
        if (sqlCIList == null) {
            sqlCI.setSequence(1);
        } else {
            sqlCI.setSequence(sqlCIList.size() + 1);
        }

        sqlCI.setType(type);
        sqlCI.setStatement(statement);
        sqlCI.setDescription(description);
        sqlCI.setCreatedBy(createdBy);
        sqlCI.setCreatedTs(LocalDateTime.now());
        sqlCI.setUpdatedBy(createdBy);
        sqlCI.setUpdatedTs(LocalDateTime.now());

        return sqlCIRepository.insert(sqlCI);

    }

    @RequestMapping("/api/sqlCI/change")
    public
    @ResponseBody
    void updateSqlCI(@RequestParam long id,
                     @RequestParam int sequence,
                     @RequestParam String createdBy,
                     @RequestParam String updatedBy,
                     @RequestParam String type,
                     @RequestParam String statement,
                     @RequestParam Boolean active,
                     String description) {

        SqlCI sqlCI = sqlCIRepository.findById(id);
        sqlCI.setActive(active);
        sqlCI.setSequence(sequence);
        sqlCI.setCreatedBy(createdBy);
        sqlCI.setUpdatedBy(updatedBy);
        sqlCI.setType(type);
        sqlCI.setStatement(statement);
        sqlCI.setDescription(description);
        sqlCI.setUpdatedTs(LocalDateTime.now());

        sqlCIRepository.save(sqlCI);

    }

    @RequestMapping("/api/sqlCI/delete")
    public
    @ResponseBody
    void deleteById(@RequestParam Long id) {

        SqlCI sqlCI = sqlCIRepository.findById(id);

        CommonUtils.validateNullObj(sqlCI, "SQL CI not found");

        sqlCIRepository.delete(sqlCI);
    }
}