package com.xuecheng.mange_cms.controller;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsSiteServer;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.mange_cms.service.CmsSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping
public class PageSiteController {
    @Autowired
    CmsSiteService cmsSiteService;
    @ApiOperation("站点查询")
    @GetMapping("/cms/site")
    public QueryResponseResult findSiteListAll(){
        return cmsSiteService.findAll();
    }
}
