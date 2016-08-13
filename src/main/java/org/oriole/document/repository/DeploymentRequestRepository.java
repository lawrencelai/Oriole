package org.oriole.document.repository;

import java.util.List;

import org.oriole.document.DeploymentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeploymentRequestRepository extends MongoRepository<DeploymentRequest, String> {
    
    public List<DeploymentRequest> findByStatus(String status);
        
    public List<DeploymentRequest> findBySqlCiId(String sqlCiId);
    
    public List<DeploymentRequest> findByTargetDatabase(String targetDatabase);

}