package com.xuecheng.mange_cms.controller;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.mange_cms.service.CmsPageService;
import com.xuecheng.mange_cms.service.Impl.CmsPageImpl;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Controller

public class CmsPagePreviewController extends BaseController {
    //页面预览
    @Autowired
    CmsPageService cmsPageService;
    @GetMapping("/cms/page/preview/{pageId}")
    public void preview(@PathVariable String pageId) throws IOException, TemplateException {
        String pageHtml = cmsPageService.getPageId(pageId);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(pageHtml.getBytes());
    }

}
