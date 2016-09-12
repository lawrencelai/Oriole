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

import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonEnum.MongoDeployRequest;
import org.oriole.common.CommonUtils;
import org.oriole.common.JQueryDataTableObject;
import org.oriole.common.JsonObject;
import org.oriole.document.DatabasePool;
import org.oriole.document.MantisPool;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.DatabasePoolRepository;
import org.oriole.document.repository.MantisPoolRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ResourcePoolController {

	// private static final Logger logger =
	// Logger.getLogger(DatabasePoolController.class);

	@Autowired
	private DatabasePoolRepository databasePoolRepository;

	@Autowired
    private MantisPoolRepository mantisPoolRepository;

    @Autowired
    private SequenceDao sequenceDao;

	// Exception

	@ExceptionHandler(InputDataException.class)
	public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
		ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage(),
				request.getRequestURL().append("/error").toString());
		return error;
	}

	@RequestMapping(value = "/api/resource/database/dt/search", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JQueryDataTableObject<DatabasePool> getDatabasePoolForDT(@RequestParam int iDisplayStart,
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
		Page<DatabasePool> page = null;
		int iTotalRecords;
		int iTotalDisplayRecords;

		page = databasePoolRepository.findAll(pageable);

		iTotalRecords = (int) page.getTotalElements();
		iTotalDisplayRecords = page.getTotalPages() * iDisplayLength;

		JQueryDataTableObject<DatabasePool> dtPage = new JQueryDataTableObject<>(page.getContent(), iTotalRecords,
				iTotalDisplayRecords, Integer.toString(sEcho));

		return dtPage;
	}

	@RequestMapping("/api/resource/database/deployableList")
	public @ResponseBody List<JsonObject> getDeployableDatabasePools() {
		List<DatabasePool> databasePools = databasePoolRepository.findByActiveAndRestricted(true, false);
		List<JsonObject> databasePoolJObject = new ArrayList<JsonObject>();

		for (DatabasePool databasePool : databasePools) {
			databasePoolJObject.add(new JsonObject(String.valueOf(databasePool.getId()), databasePool.getName()));
		}
		return databasePoolJObject;
	}

	@RequestMapping("/api/resource/database/restrictedList")
	public @ResponseBody List<JsonObject> getRestrictDatabasePoolNameList() {
		List<DatabasePool> databasePools = databasePoolRepository.findByActiveAndRestricted(true, true);
		List<JsonObject> databasePoolJObject = new ArrayList<JsonObject>();

		for (DatabasePool databasePool : databasePools) {
			databasePoolJObject.add(new JsonObject(String.valueOf(databasePool.getId()), databasePool.getName()));
		}
		return databasePoolJObject;
	}

	@RequestMapping("/api/resource/database/searchAll")
	public @ResponseBody List<DatabasePool> getDatabasePoolList() {
		return databasePoolRepository.findAll();
	}

	@RequestMapping("/api/resource/database/searchByName")
	public @ResponseBody DatabasePool getDatabasePoolByName(@RequestParam String databaseName) {
		return databasePoolRepository.findByName(databaseName);
	}

	@RequestMapping("/api/resource/database/create")
	public @ResponseBody DatabasePool createDatabasePool(@RequestParam String name, 
			@RequestParam boolean active, @RequestParam boolean restricted,
			@RequestParam String host,
			@RequestParam String port, 
			@RequestParam String username,	
			@RequestParam String password,
			@RequestParam String createdBy,
			String serviceName, 
			String sid) {

		if (databasePoolRepository.findByName(name) != null) {
			throw new InputDataException("Database name is defined");
		}

		DatabasePool databasePool = new DatabasePool(
				sequenceDao.getNextSequenceId(DatabaseSequence.RESOURCE_POOL.getSequenceName()));
		databasePool.setName(name);
		databasePool.setActive(active);
		databasePool.setRestricted(restricted);
		databasePool.setHost(host);
		databasePool.setPort(port);
		databasePool.setServiceName(serviceName);
		databasePool.setSid(sid);
		databasePool.setUsername(username);
		databasePool.setPassword(password);
		databasePool.setCreatedBy(createdBy);
		databasePool.setCreatedTs(new Date());

		return databasePoolRepository.insert(databasePool);

	}

	@RequestMapping("/api/resource/database/change")
	public @ResponseBody DatabasePool updateDatabasePool(
			@RequestParam String name,
			@RequestParam boolean active, 
			@RequestParam boolean restricted,			
			@RequestParam String host,
			@RequestParam String port,
			@RequestParam String username, 
			@RequestParam String password,
			@RequestParam String updatedBy,
			String serviceName,
			String sid) {

		DatabasePool databasePool = databasePoolRepository.findByName(name);

		CommonUtils.validateNullObj(databasePool, "Database name is not exist");

		databasePool.setName(name);
		databasePool.setActive(active);
		databasePool.setRestricted(restricted);
		databasePool.setHost(host);
		databasePool.setPort(port);
		databasePool.setServiceName(serviceName);
		databasePool.setSid(sid);
		databasePool.setUsername(username);
		databasePool.setPassword(password);
		databasePool.setUpdatedBy(updatedBy);
		databasePool.setUpdatedTs(new Date());


		return databasePoolRepository.save(databasePool);
	}

    @RequestMapping("/api/resource/mantis/create")
    public
    @ResponseBody
    MantisPool createMantisPool(@RequestParam String name,
                                @RequestParam boolean active,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String createdBy,
                                @RequestParam String url) {

        if (mantisPoolRepository.findByName(name) != null) {
            throw new InputDataException("Mantis name is defined");
        }

        MantisPool mantisPool = new MantisPool(
                sequenceDao.getNextSequenceId(DatabaseSequence.RESOURCE_POOL.getSequenceName()));
        mantisPool.setName(name);
        mantisPool.setActive(active);
        mantisPool.setUrl(url);
        mantisPool.setUsername(username);
        mantisPool.setPassword(password);
        mantisPool.setCreatedBy(createdBy);
        mantisPool.setCreatedTs(new Date());

        return mantisPoolRepository.insert(mantisPool);

    }

    @RequestMapping("/api/resource/mantis/change")
    public
    @ResponseBody
    MantisPool updateMantisPool(
            @RequestParam String name,
            @RequestParam boolean active,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String updatedBy,
            @RequestParam String url) {

        MantisPool mantisPool = mantisPoolRepository.findByName(name);

        CommonUtils.validateNullObj(mantisPool, "Mantis name is not exist");

        mantisPool.setName(name);
        mantisPool.setActive(active);
        mantisPool.setUrl(url);

        mantisPool.setUsername(username);
        mantisPool.setPassword(password);
        mantisPool.setUpdatedBy(updatedBy);
        mantisPool.setUpdatedTs(new Date());

        return mantisPoolRepository.save(mantisPool);
    }
}