package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageResository extends MongoRepository<CmsPage,String> {
}
