package org.oriole.document.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.oriole.common.CommonUtils;
import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.document.DatabasePool;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.DatabasePoolRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabasePoolController {
	
	//private static final Logger logger = Logger.getLogger(DatabasePoolController.class);
		
	@Autowired
	private DatabasePoolRepository databasePoolRepository;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	//Exception
	
	@ExceptionHandler(InputDataException.class)
	public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
	    ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(),
	    		exception.getLocalizedMessage(), 
	    		request.getRequestURL().append("/error").toString());
	    return error;
	}
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/database/searchAll")
	public @ResponseBody List<DatabasePool> getDatabasePoolList() {	  	   
	   return databasePoolRepository.findAll();
	}
	 
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/database/searchByName")
    public @ResponseBody DatabasePool getDatabasePoolByName(
    		@RequestParam String databaseName) {
    	return databasePoolRepository.findByName(databaseName);
    }
    
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/database/create")
    public @ResponseBody DatabasePool createDatabasePool(
    		@RequestParam String name,
    		@RequestParam boolean active,
    		@RequestParam String host,
    		@RequestParam String port,
    		@RequestParam String username,
    		@RequestParam String password,
    		String description,
    		String serviceName,
    		String sid,
    		String tns) {
     	
    	if(databasePoolRepository.findByName(name)!=null){
    		throw new InputDataException("Database name is defined");
    	}
    	
    	DatabasePool databasePool = new DatabasePool(sequenceDao.getNextSequenceId(DatabaseSequence.SQL_CI_GROUP.name()));
    	databasePool.setName(name);  
    	databasePool.setActive(active);
    	databasePool.setHost(host);
    	databasePool.setPort(port);
    	databasePool.setServiceName(serviceName);
    	databasePool.setSid(sid);
    	databasePool.setUsername(username);
    	databasePool.setPassword(password);
    	databasePool.setDescription(description);
    	
    	return databasePoolRepository.insert(databasePool);
    	
    }
    @CrossOrigin(origins = "http://localhost")    
    @RequestMapping("/database/change")
    public @ResponseBody DatabasePool updateDatabasePool(
    		@RequestParam String name,
    		@RequestParam String host,
    		@RequestParam String port,
    		@RequestParam String username,
    		@RequestParam String password,
    		String description,
    		String serviceName,
    		String sid,
    		String tns) {
   	
    	DatabasePool databasePool = databasePoolRepository.findByName(name);
    	
    	CommonUtils.validateNullObj(databasePool,"Database name is not exist");        
    
    	databasePool.setName(name);
    	databasePool.setHost(host);
    	databasePool.setPort(port);
    	databasePool.setServiceName(serviceName);
    	databasePool.setSid(sid);
    	databasePool.setUsername(username);
    	databasePool.setPassword(password);
    	databasePool.setDescription(description);
    	
    	return databasePoolRepository.save(databasePool);   	
    }    
}