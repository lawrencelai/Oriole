package org.oriole.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.oriole.common.CommonEnum.DeployRequestState;
import org.oriole.document.DeployRequest;
import org.oriole.document.repository.DatabasePoolRepository;
import org.oriole.document.repository.DeployRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private DeployRequestRepository deploymentRequestRepository;
	
	@Autowired
	private DatabasePoolRepository databasePoolRepository;
    
    @Scheduled(fixedRate = 1000)
    public void executePendingDeploymentRequest() {
    	System.out.println("[" + dateFormat.format(new Date()) + "]" + "ScheduleTask Start");
    	List<DeployRequest> deploymentRequestList =  deploymentRequestRepository.findByStatus(DeployRequestState.SCHEDULE.name());
    	
    	for(DeployRequest  deploymentRequest :deploymentRequestList){
    		System.out.println("[" + dateFormat.format(new Date()) + "]" + deploymentRequest.getId());
    	}
    	System.out.println("[" + dateFormat.format(new Date()) + "]" + "ScheduleTask End");
    }
}