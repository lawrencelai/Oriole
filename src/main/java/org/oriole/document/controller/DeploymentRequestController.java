package org.oriole.document.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonEnum.DeployRequestState;
import org.oriole.document.DeploymentRequest;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.DeploymentRequestRepository;
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
public class DeploymentRequestController {
	
	//private static final Logger logger = Logger.getLogger(DatabasePoolController.class);
		
	@Autowired
	private DeploymentRequestRepository deploymentRequestRepository;
	
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
	@RequestMapping("/deploymentRequest/searchAll")
	public @ResponseBody List<DeploymentRequest> getDatabasePoolList() {	  	   
	   return deploymentRequestRepository.findAll();
	}
	 
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/deploymentRequest/searchBySqlCiId")
    public @ResponseBody List<DeploymentRequest> searchBySqlCiId(
    		@RequestParam String sqlCiId) {
    	return deploymentRequestRepository.findBySqlCiId(sqlCiId);
    }
    
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/deploymentRequest/searchByDatabase")
    public @ResponseBody List<DeploymentRequest> searchByDatabase(
    		@RequestParam String targetDatabase) {
    	return deploymentRequestRepository.findByTargetDatabase(targetDatabase);
    }
    
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/deploymentRequest/create")
    public @ResponseBody DeploymentRequest createDeploymentRequest(
    		@RequestParam String sqlCiId,
    		@RequestParam String targetDatabase,
    		@RequestParam String requestBy    	
    		) {
    	
    	DeploymentRequest deploymentRequest = new DeploymentRequest(
    			sequenceDao.getNextSequenceId(DatabaseSequence.DEPOLYMENT_REQUEST.name()));
    	deploymentRequest.setSqlCiId(sqlCiId);
    	deploymentRequest.setTargetDatabase(targetDatabase);
    	deploymentRequest.setStatus(DeployRequestState.SCHEDULED.name());
    	deploymentRequest.setRequestBy(requestBy);
    	deploymentRequest.setRequestTs(new Date());
    	return deploymentRequestRepository.insert(deploymentRequest);
    	
    }  
}