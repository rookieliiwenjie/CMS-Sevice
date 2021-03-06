package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.config.api.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.mange_cms.service.CmsPageService;
import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/page")
public class PageListController implements CmsPageControllerApi {
    @Autowired
    CmsPageService cmsPageService;

    @ApiOperation("分页查询页面列表")
    @GetMapping("/list")
    public QueryResponseResult findList(@RequestParam("page") int page, @RequestParam("size") int size, QueryPageRequest queryResponseResult) {

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
        return cmsPageService.findList(page, size, queryResponseResult);
    }

    @PostMapping("/add")
    @ApiOperation("新增頁面")
    public CmsPageResult addPage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.saveCmsPage(cmsPage);
    }

    @ApiOperation("根据id查询")
    @GetMapping("/one/{id}")
    public CmsPage findOneByStateId(@PathVariable("id") String id) {
        return cmsPageService.findById(id);
    }

    @Override
    @PutMapping("/update/{id}")
    @ApiOperation("修改页面")
    public CmsPageResult updateCmsPage(@RequestBody CmsPage cmsPage, @PathVariable("id") String id) {
        return cmsPageService.updateCmsPage(cmsPage,id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除页面")
    public ResponseResult deleltpageByid(@PathVariable("id") String id) {
        return cmsPageService.deleteById(id);
    }
}
