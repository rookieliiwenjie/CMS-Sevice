package com.xuecheng.mange_cms.service.Impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.model.response.ResultCode;
import com.xuecheng.mange_cms.dao.CmsPageRepostitory;
import com.xuecheng.mange_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CmsPageImpl implements CmsPageService {
    @Autowired
    CmsPageRepostitory cmsPageRepostitory;




    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryResponseResult) {
        if (page <= 0) { page = 1; }
        page = page -1; //为了适应mongodb的接口将页码减1
        if (size <= 0) { size = 20; }
        Pageable pageable = new PageRequest(page,size);
        Page<CmsPage> cmsPageList = cmsPageRepostitory.findAll(pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(cmsPageList.getContent());
        cmsPageQueryResult.setTotal(cmsPageList.getTotalElements());


        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }
}
