package com.xuecheng.mange_cms.service.Impl;

import com.xuecheng.api.config.api.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.model.response.ResultCode;
import com.xuecheng.mange_cms.dao.CmsPageRepostitory;
import com.xuecheng.mange_cms.service.CmsPageService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CmsPageImpl implements CmsPageService {
    @Autowired
    CmsPageRepostitory cmsPageRepostitory;


    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryResponseResult) {
        //自定义查询
        if (queryResponseResult == null) {
            queryResponseResult = new QueryPageRequest();

        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        System.out.println("queryResponseResult =" + queryResponseResult.getPageAliase());

        CmsPage cmsPage = new CmsPage();
        //别名查询
        if (!(StringUtils.isBlank(queryResponseResult.getPageAliase()))) {
            cmsPage.setPageAliase(queryResponseResult.getPageAliase());
        }
        if (!StringUtils.isBlank(queryResponseResult.getPageId())) {
            cmsPage.setPageId(queryResponseResult.getPageId());
        }
        if (!StringUtils.isBlank(queryResponseResult.getPageName())) {
            cmsPage.setPageName(queryResponseResult.getPageName());
        }
        if (!StringUtils.isBlank(queryResponseResult.getTemplateId())) {
            cmsPage.setTemplateId(queryResponseResult.getTemplateId());
        }
        if (!StringUtils.isBlank(queryResponseResult.getSiteId())) {
            cmsPage.setSiteId(queryResponseResult.getSiteId());
        }
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        if (page <= 0) {
            page = 1;
        }
        page = page - 1; //为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> cmsPageList = cmsPageRepostitory.findAll(example, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(cmsPageList.getContent());
        cmsPageQueryResult.setTotal(cmsPageList.getTotalElements());


        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    @Override
    public CmsPageResult saveCmsPage(CmsPage cmsPage) {
        //验证页面位于性页面名称页面id页面webpath
        CmsPage cmsPage1 = cmsPageRepostitory.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 == null) {
            cmsPage.setPageId(null);
            CmsPage cmsPage2 = cmsPageRepostitory.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage2);

        }
        return new CmsPageResult(CommonCode.UNSAVE, null);
    }
}
