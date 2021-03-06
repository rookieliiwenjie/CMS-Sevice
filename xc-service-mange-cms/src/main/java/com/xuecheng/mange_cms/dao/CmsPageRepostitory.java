package com.xuecheng.mange_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CmsPageRepostitory extends MongoRepository<CmsPage, String> {
    @Override
    List<CmsPage> findAll();

    Optional<CmsPage> findByPageName(String pageName);


    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
