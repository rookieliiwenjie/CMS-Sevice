package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.model.response.ResponseResult;
import freemarker.template.TemplateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface CmsPageService {



    QueryResponseResult findList(int pagesize, int pagesize1, QueryPageRequest queryResponseResult);

    CmsPageResult saveCmsPage(CmsPage cmsPage);

    CmsPage findById(String id);

    CmsPageResult updateCmsPage(CmsPage cmsPage, String id);

    ResponseResult deleteById(String id);
    String getPageId(String id) throws IOException, TemplateException;
}
