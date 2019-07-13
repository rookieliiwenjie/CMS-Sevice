package com.xuecheng.api.config.api;

import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CmsPageControllerApi {

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryResponseResult);

}
