package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;

import java.io.IOException;

public interface PageServer {
    public void savePageToServerPath(String pageId) throws IOException;

    public CmsPage findCmsPageById(String pageId);
}
