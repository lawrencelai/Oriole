package org.oriole.document.repository;



import org.oriole.document.SqlCategory;
import org.springframework.data.mongodb.repository.MongoRepository;	

public interface SqlCategoryRepository extends MongoRepository<SqlCategory, String> {

    public SqlCategory findById(long id);

}