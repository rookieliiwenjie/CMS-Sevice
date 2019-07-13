package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.config.api.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.mange_cms.service.CmsPageService;
import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class PageListController  implements CmsPageControllerApi   {
    @Autowired
    CmsPageService cmsPageService;
    @ApiOperation("分页查询页面列表")
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryResponseResult) {

        /*QueryResult<CmsPage> queryResult = new QueryResult<>();
        List<CmsPage> list = new ArrayList<>();
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试");
        list.add(cmsPage);
        queryResult.setList(list);
        queryResult.setTotal(1);
        QueryResponseResult responseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);*/
        /*QueryResult<CmsPage> cmsPageList = ;

        QueryResponseResult responseResult = new QueryResponseResult(CommonCode.SUCCESS,cmsPageList);*/
        return cmsPageService.findList(page,size,queryResponseResult);
    }
}
