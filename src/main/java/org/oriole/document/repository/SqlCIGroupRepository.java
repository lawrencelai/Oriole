 package org.oriole.document.repository;

import java.util.List;

import org.oriole.document.SqlCIGroup;
import org.springframework.data.mongodb.repository.MongoRepository;	

public interface SqlCIGroupRepository extends MongoRepository<SqlCIGroup, String> {

    public SqlCIGroup findById(long id);
    
    public List<SqlCIGroup> findByOwner(String owner);
    
}