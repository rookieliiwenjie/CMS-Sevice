package com.xuecheng.mange_cms.service.Impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import com.xuecheng.api.config.api.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.Base.RequestPageable;
import com.xuecheng.mange_cms.dao.CmsPageRepostitory;
import com.xuecheng.mange_cms.dao.CmsTempletRepostitory;
import com.xuecheng.mange_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class CmsPageImpl implements CmsPageService {
    @Autowired
    CmsPageRepostitory cmsPageRepostitory;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CmsTempletRepostitory cmsTempletRepostitory;
    @Autowired
    GridFSBucket gridFSBucket;
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
    //页面静态化
    public String getPageId(String pageId) throws IOException, TemplateException {
        //获取数据模型
        Map modelDate = getDateByPageId(pageId);
        //
        if(modelDate==null){
            ExceptionCast.cost(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String templetByPageId = getHtmlTemplate(pageId);
        if(!templetByPageId.isEmpty()){
            String html = generateHtml(templetByPageId,modelDate);
            return html;
        }

        return null;
        //执行静态化

    }
    //执行静态化
    private String generateHtml(String template , Map model) throws IOException, TemplateException {
        //生成配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",template);
        //向configuriton配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        Template templatehtml = configuration.getTemplate("template");
        //调用api进行模板静态化
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(templatehtml,model);
        return html;
    }
    //获取页面模型数据
    private Map getDateByPageId(String pageId){
        CmsPage cmsPage = this.findById(pageId);
        if(cmsPage==null){
            ExceptionCast.cost(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        }
        String dataUrl = cmsPage.getDataUrl();
        if(dataUrl.isEmpty()){
           ExceptionCast.cost(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(dataUrl,Map.class);
        if(responseEntity.getBody()==null){
            ExceptionCast.cost(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        return responseEntity.getBody();

    }
    //获取页面模板
    private String getHtmlTemplate(String pageId) throws IOException {
        Optional<CmsPage> cmsPage = cmsPageRepostitory.findById(pageId);
        if(cmsPage.isPresent()){
            CmsPage cmsPage1 = cmsPage.get();
            if(!cmsPage1.getTemplateId().isEmpty()){
                Optional<CmsTemplate> cmsTemplate = cmsTempletRepostitory.findById(cmsPage1.getTemplateId());
                if(cmsTemplate.isPresent()){
                    CmsTemplate cmsTemplate1 = cmsTemplate.get();
                    //根据id查询对象
                    GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate1.getTemplateFileId())));
                    //打开下载流
                    GridFSDownloadStream gridFSDownloadStream  = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
                    //获取流对象
                    GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
                    String content = IOUtils.toString(gridFsResource.getInputStream());
                    return content.isEmpty()?null:content;

                }
            }
            return null;

        }
        return null;

    }
}
