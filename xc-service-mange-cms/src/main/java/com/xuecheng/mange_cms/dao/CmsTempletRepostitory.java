package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsTempletRepostitory extends MongoRepository<CmsTemplate,String > {
}
