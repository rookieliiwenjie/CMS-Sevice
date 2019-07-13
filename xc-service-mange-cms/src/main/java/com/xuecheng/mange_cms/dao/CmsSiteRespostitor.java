package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsSiteRespostitor extends MongoRepository<CmsSite, String> {
}
