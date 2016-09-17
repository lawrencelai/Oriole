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

package org.oriole.schedule;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    @Scheduled(fixedRate = 50000)
    public void executePendingDeploymentRequest() {
        System.out.println("[" + LocalDateTime.now() + "]" + "ScheduleTask Start");
        List<DeployRequest> deploymentRequestList =  deploymentRequestRepository.findByStatus(DeployRequestState.SCHEDULE.name());
    	
    	for(DeployRequest  deploymentRequest :deploymentRequestList){
            System.out.println("[" + LocalDateTime.now() + "]" + deploymentRequest.getId());
        }
        System.out.println("[" + LocalDateTime.now() + "]" + "ScheduleTask End");
    }
}