package org.oriole.document.repository;

import java.util.List;

import org.oriole.document.DeployRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeployRequestRepository extends MongoRepository<DeployRequest, String> {
    
    public List<DeployRequest> findByStatus(String status);
        
    public List<DeployRequest> findBySqlCiId(String sqlCiId);
    
    public List<DeployRequest> findByTargetDatabase(String targetDatabase);

    public List<DeployRequest> findByTargetDatabaseAndStatus(String targetDatabase, String status);

}