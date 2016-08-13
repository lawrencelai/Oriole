package org.oriole.document.repository;

import org.oriole.document.MantisInfo;
import org.springframework.data.mongodb.repository.MongoRepository;	

public interface MantisInfoRepository extends MongoRepository<MantisInfo, String> {
 
    public MantisInfo findByGroupId(long groupId);
}