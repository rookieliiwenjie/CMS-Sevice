package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.config.api.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.mange_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    CmsConfigService cmsConfigService;
    @Override
    @GetMapping("/one/{id}")
    public CmsConfig getModel(@PathVariable  String id) {

        return cmsConfigService.findById(id);
    }
}
