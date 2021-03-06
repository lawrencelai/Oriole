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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.oriole.common.CommonEnum;
import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonEnum.DeployRequestState;
import org.oriole.common.CommonEnum.MongoDeployRequest;
import org.oriole.common.CommonUtils;
import org.oriole.common.JQueryDataTableObject;
import org.oriole.document.DatabasePool;
import org.oriole.document.DeployRequest;
import org.oriole.document.SqlCI;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.DatabasePoolRepository;
import org.oriole.document.repository.DeployRequestRepository;
import org.oriole.document.repository.SqlCIRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployRequestController {

    // private static final Logger logger =
    // Logger.getLogger(DatabasePoolController.class);

    @Autowired
    private DeployRequestRepository deployRequestRepository;

    @Autowired
    private DatabasePoolRepository databasePoolRepository;

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

    @RequestMapping(value = "/api/deployRequest/dt/search", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    JQueryDataTableObject<DeployRequest> getDatabasePoolForDT(@RequestParam int iDisplayStart,
                                                              @RequestParam int iDisplayLength,
                                                              @RequestParam int sEcho,
                                                              @RequestParam int iSortCol_0,
                                                              @RequestParam String sSortDir_0,
                                                              @RequestParam int iSortingCols,
                                                              @RequestParam String sSearch) throws IOException {

        int pageNumber = (iDisplayStart + 1) / iDisplayLength;
        String sortingField = MongoDeployRequest.findMongoFieldNameByColumnNum(iSortCol_0);
        Sort sortPageRequest = new Sort(Sort.Direction.fromString(sSortDir_0), sortingField);
        PageRequest pageable = new PageRequest(pageNumber, iDisplayLength, sortPageRequest);
        Page<DeployRequest> page = null;
        int iTotalRecords;
        int iTotalDisplayRecords;

        page = deployRequestRepository.findAll(pageable);

        iTotalRecords = (int) page.getTotalElements();
        iTotalDisplayRecords = page.getTotalPages() * iDisplayLength;

        JQueryDataTableObject<DeployRequest> dtPage = new JQueryDataTableObject<>(page.getContent(), iTotalRecords,
                iTotalDisplayRecords, Integer.toString(sEcho));

        return dtPage;
    }

    @RequestMapping("/api/deployRequest/searchAll")
    public
    @ResponseBody
    List<DeployRequest> getFullList() {
        return deployRequestRepository.findAll();
    }

    @RequestMapping("/api/deployRequest/searchBySqlCiId")
    public
    @ResponseBody
    List<DeployRequest> getListByStatus(@RequestParam String state) {
        DeployRequestState deployRequestState = DeployRequestState.find(state);

        CommonUtils.validateNullObj(deployRequestState, "state must be SUCCESS , FAIL, SCHEDULE, HOLD");
        return deployRequestRepository.findByStatus(deployRequestState.name());
    }

    @RequestMapping("/api/deployRequest/searchByDatabase")
    public
    @ResponseBody
    List<DeployRequest> searchByDatabase(@RequestParam String targetDatabase) {

        DatabasePool databasePool = databasePoolRepository.findByTypeAndName(CommonEnum.ResourceType.DATABASE.name(), targetDatabase);

        CommonUtils.validateNullObj(databasePool, "No Target Database Defined");

        return deployRequestRepository.findByTargetDatabase(databasePool.getName());
    }

    @RequestMapping("/api/deployRequest/create")
    public
    @ResponseBody
    DeployRequest createDeploymentRequest(
            @RequestParam Long ciId,
            @RequestParam String description,
            @RequestParam String targetDatabase,
            @RequestParam String requestBy) {

        SqlCI sqlCI = sqlCIRepository.findById(ciId);

        CommonUtils.validateNullObj(sqlCI, "SqlCI is not existed");

        DeployRequest deploymentRequest = new DeployRequest(
                sequenceDao.getNextSequenceId(DatabaseSequence.DEPOLYMENT_REQUEST.getSequenceName()));
        deploymentRequest.setSqlCiId(sqlCI.getId());
        deploymentRequest.setDescription(description);
        deploymentRequest.setTargetDatabase(targetDatabase);
        deploymentRequest.setStatus(DeployRequestState.SCHEDULE.name());
        deploymentRequest.setRequestBy(requestBy);
        deploymentRequest.setRequestTs(LocalDateTime.now());
        return deployRequestRepository.insert(deploymentRequest);

    }

    @RequestMapping("/api/deployRequest/createBySqlCIGroup")
    public
    @ResponseBody
    List<DeployRequest> createDeploymentRequestByGroup(
            @RequestParam Long id,
            @RequestParam String description,
            @RequestParam String targetDatabase,
            @RequestParam String requestBy) {

        List<SqlCI> sqlCIList = sqlCIRepository.findByGroupID(id);
        List<DeployRequest> deploymentRequestList = new ArrayList<DeployRequest>();
        CommonUtils.validateNullObj(id, "SqlCI Group is not existed");

        for (SqlCI sqlCI : sqlCIList) {
            DeployRequest deploymentRequest = new DeployRequest(
                    sequenceDao.getNextSequenceId(DatabaseSequence.DEPOLYMENT_REQUEST.getSequenceName()));

            deploymentRequest.setSqlCiId(sqlCI.getId());
            deploymentRequest.setDescription(description + ":" + sqlCI.getDescription());
            deploymentRequest.setTargetDatabase(targetDatabase);
            deploymentRequest.setStatus(DeployRequestState.SCHEDULE.name());
            deploymentRequest.setRequestBy(requestBy);
            deploymentRequest.setRequestTs(LocalDateTime.now());
            deploymentRequestList.add(deployRequestRepository.insert(deploymentRequest));

        }
        return deploymentRequestList;
    }

}