package org.oriole.document.repository;

import java.util.List;

import org.oriole.document.SqlCI;
import org.springframework.data.mongodb.repository.MongoRepository;	

public interface SqlCIRepository extends MongoRepository<SqlCI, String> {

    public SqlCI findById(long id);
 
    public List<SqlCI> findByGroupID(long groupID);
}