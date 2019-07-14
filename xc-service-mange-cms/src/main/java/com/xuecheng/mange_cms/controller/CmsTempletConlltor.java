package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.config.api.CmsTempletControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.mange_cms.service.CmsTempletService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/templete")
public class CmsTempletConlltor implements CmsTempletControllerApi {
    @Autowired
    CmsTempletService cmsTempletService;
    @GetMapping("/all")
    @ApiOperation("模板查询")
    public QueryResponseResult findAll(){
        return cmsTempletService.findAll();
    }
}
