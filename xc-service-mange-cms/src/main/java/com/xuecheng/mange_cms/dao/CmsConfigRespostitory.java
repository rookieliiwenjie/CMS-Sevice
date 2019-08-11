package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsConfigRespostitory extends MongoRepository<CmsConfig, String> {
}
