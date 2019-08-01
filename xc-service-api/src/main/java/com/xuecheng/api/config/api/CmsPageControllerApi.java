package com.xuecheng.api.config.api;

import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CmsPageControllerApi {

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryResponseResult);
    public CmsPageResult addPage(CmsPage cmsPage);
    public CmsPage findOneByStateId(String id);
    public CmsPageResult updateCmsPage(CmsPage cmsPage,String id);
    public ResponseResult deleltpageByid(String id);
}
