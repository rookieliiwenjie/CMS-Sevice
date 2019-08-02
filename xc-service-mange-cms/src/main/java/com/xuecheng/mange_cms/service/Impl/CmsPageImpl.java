package com.xuecheng.mange_cms.service.Impl;

import com.xuecheng.api.config.api.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.mange_cms.dao.CmsPageRepostitory;
import com.xuecheng.mange_cms.service.CmsPageService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
        Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC,"pageCreateTime"));
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
        if (cmsPage1 != null) {
            ExceptionCast.cost(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        }
        cmsPage.setPageId(null);
        CmsPage cmsPage2 = cmsPageRepostitory.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage2);
    }

    @Override
    public CmsPage findById( String id) {
        Optional<CmsPage> optionalCmsPage =   cmsPageRepostitory.findById(id);
        if(optionalCmsPage.isPresent()){
            CmsPage cmsPage1 = optionalCmsPage.get();
            return cmsPage1;
        }
        return null;
    }
    //修改页面
    @Override
    public CmsPageResult updateCmsPage(CmsPage cmsPage, String id) {
        CmsPage cmsPage1 = this.findById(id);
        if(cmsPage1!=null){
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            cmsPage1.setPageName(cmsPage.getPageName());
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            cmsPage1.setSiteId(cmsPage.getSiteId());
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            cmsPageRepostitory.save(cmsPage1);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage1);
        }
        return new CmsPageResult(CommonCode.FAIL,cmsPage);
    }

    @Override
    public ResponseResult deleteById(String id) {
        Optional<CmsPage> cmsPage = cmsPageRepostitory.findById(id);
        if(cmsPage.isPresent()){
            cmsPageRepostitory.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
