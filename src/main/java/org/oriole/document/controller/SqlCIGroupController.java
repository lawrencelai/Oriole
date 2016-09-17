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

import org.apache.log4j.Logger;
import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonEnum.MongoDbSqlCIGroup;
import org.oriole.common.CommonUtils;
import org.oriole.common.JQueryDataTableObject;
import org.oriole.document.MantisInfo;
import org.oriole.document.SqlCIGroup;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.SqlCIGroupRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class SqlCIGroupController {

    private static final Logger logger = Logger.getLogger(SqlCIGroupController.class);

    @Autowired
    private SqlCIGroupRepository sqlCIGroupRepository;

    @Autowired
    private SequenceDao sequenceDao;

    // Exception

    @ExceptionHandler(InputDataException.class)
    public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
        ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage(),
                request.getRequestURL().append("/error").toString());
        return error;
    }

    @RequestMapping(value = "/api/sqlCIGroup/dt/search", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    JQueryDataTableObject<SqlCIGroup> getSqlCIGroupByTypeForDT(
            @RequestParam int iDisplayStart,
            @RequestParam int iDisplayLength,
            @RequestParam int sEcho,
            @RequestParam int iSortCol_0,
            @RequestParam String sSortDir_0,
            @RequestParam int iSortingCols,
            @RequestParam String sSearch) throws IOException {

        int pageNumber = (iDisplayStart + 1) / iDisplayLength;
        String sortingField = MongoDbSqlCIGroup.findMongoFieldNameByColumnNum(iSortCol_0);
        Sort sortPageRequest = new Sort(Sort.Direction.fromString(sSortDir_0), sortingField);
        PageRequest pageable = new PageRequest(pageNumber, iDisplayLength, sortPageRequest);
        Page<SqlCIGroup> page = null;
        int iTotalRecords;
        int iTotalDisplayRecords;

        page = sqlCIGroupRepository.findAll(pageable);

        iTotalRecords = (int) page.getTotalElements();
        iTotalDisplayRecords = page.getTotalPages() * iDisplayLength;

        JQueryDataTableObject<SqlCIGroup> dtPage = new JQueryDataTableObject<>(page.getContent(), iTotalRecords,
                iTotalDisplayRecords, Integer.toString(sEcho));

        return dtPage;
    }

    @RequestMapping("/api/sqlCIGroup/searchByOwner")
    public List<SqlCIGroup> getSqlCIGroupByOwner(@RequestParam String owner) {
        logger.debug("getSQLCIGroup - Parameter :" + owner);
        return sqlCIGroupRepository.findByOwner(owner);
    }

    @RequestMapping("/api/sqlCIGroup/searchById")
    public
    @ResponseBody
    SqlCIGroup getSqlCIGroupById(@RequestParam Long groupId) {
        logger.debug("getSQLCIGroup - Parameter :" + groupId);

        return sqlCIGroupRepository.findById(groupId);
    }

    @RequestMapping("/api/sqlCIGroup/create")
    public
    @ResponseBody
    SqlCIGroup createSqlCIGroup(
            @RequestParam String owner,
            @RequestParam String createdBy,
            @RequestParam String description,
            long referenceNumber,
            String targetVersion) {

        logger.debug(String.format("[ws:createSqlCIGroup] [Parameter] %s %s %s", owner, createdBy, description));

        SqlCIGroup sqlCIGroup = new SqlCIGroup(
                sequenceDao.getNextSequenceId(DatabaseSequence.SQL_CI_GROUP.getSequenceName()));
        sqlCIGroup.setDescription(description);
        sqlCIGroup.setOwner(owner);
        sqlCIGroup.setCreatedBy(createdBy);
        sqlCIGroup.setCreatedTs(LocalDateTime.now());

        MantisInfo mantisInfo = new MantisInfo(referenceNumber, sqlCIGroup.getId());
        mantisInfo.setTargetVersion(targetVersion);
        sqlCIGroup.setMantisInfo(mantisInfo);

        return sqlCIGroupRepository.insert(sqlCIGroup);

    }

    @RequestMapping("/api/sqlCIGroup/change")
    public
    @ResponseBody
    void updateSqlCIGroup(@RequestParam Long id,
                          @RequestParam String owner,
                          @RequestParam String createdBy,
                          @RequestParam String updatedBy,
                          @RequestParam String description,
                          long mantisInfoId,
                          String mantisInfoTargetVersion) {

        logger.debug(String.format("[ws:updateSqlCI] [Parameter: %s %s %s %s %s %s]",
                id, description, updatedBy,
                description, mantisInfoId, mantisInfoTargetVersion));

        SqlCIGroup sqlCIGroup = sqlCIGroupRepository.findById(id);

        CommonUtils.validateNullObj(sqlCIGroup, "No SQL CI Group Exists");
        if (sqlCIGroup.getMantisInfo() == null) {
            MantisInfo mantisInfo = new MantisInfo(mantisInfoId, id);
            mantisInfo.setTargetVersion(mantisInfoTargetVersion);
            sqlCIGroup.setMantisInfo(mantisInfo);
        } else {
            sqlCIGroup.getMantisInfo().setId(mantisInfoId);
            sqlCIGroup.getMantisInfo().setTargetVersion(mantisInfoTargetVersion);
        }

        sqlCIGroup.setDescription(description);
        sqlCIGroup.setOwner(owner);
        sqlCIGroup.setCreatedBy(createdBy);
        sqlCIGroup.setUpdatedBy(updatedBy);
        sqlCIGroup.setUpdatedTs(LocalDateTime.now());
        sqlCIGroupRepository.save(sqlCIGroup);
    }

    @RequestMapping("/api/sqlCIGroup/active")
    public
    @ResponseBody
    void setSqlGroupActive(@RequestParam Long sqlCIGroupId, @RequestParam String updatedBy) {

        logger.debug(String.format("[ws:setSqlGroupActive] [Parameter: %s %s]", sqlCIGroupId, updatedBy));

        SqlCIGroup sqlCIGroup = sqlCIGroupRepository.findById(sqlCIGroupId);

        CommonUtils.validateNullObj(sqlCIGroup, "No SQL CI Group Exists");

        sqlCIGroup.setActive(Boolean.TRUE);

        sqlCIGroupRepository.save(sqlCIGroup);
    }

    @RequestMapping("/api/sqlCIGroup/deactive")
    public
    @ResponseBody
    void setSqlGroupDeactive(@RequestParam Long sqlCIGroupId, @RequestParam String updatedBy) {

        logger.debug(String.format("[ws:setSqlGroupActive] [Parameter: %s %s]", sqlCIGroupId, updatedBy));

        SqlCIGroup sqlCIGroup = sqlCIGroupRepository.findById(sqlCIGroupId);

        CommonUtils.validateNullObj(sqlCIGroup, "No SQL CI Group Exists");

        sqlCIGroup.setActive(Boolean.FALSE);

        sqlCIGroupRepository.save(sqlCIGroup);
    }
}