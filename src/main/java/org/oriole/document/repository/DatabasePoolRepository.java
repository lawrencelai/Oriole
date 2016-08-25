package org.oriole.document.repository;

import java.util.List;

import org.oriole.document.DatabasePool;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatabasePoolRepository extends MongoRepository<DatabasePool, String> {

    public DatabasePool findById(String id);
    
    public DatabasePool findByName(String name);
    
    public List<DatabasePool> findByActive(boolean active);

}