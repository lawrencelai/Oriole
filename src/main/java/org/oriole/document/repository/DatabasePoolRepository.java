package org.oriole.document.repository;

import org.oriole.document.DatabasePool;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatabasePoolRepository extends MongoRepository<DatabasePool, String> {

    public DatabasePool findById(String id);
    
    public DatabasePool findByName(String name);

}