package com.xuecheng.mange_cms.service.Impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.mange_cms.dao.CmsPageResository;
import com.xuecheng.mange_cms.dao.CmsSiteResository;
import com.xuecheng.mange_cms.service.PageServer;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
@Log4j
public class PageServiceImpl implements PageServer {
    @Autowired
    CmsPageResository cmsPageResository;
    @Autowired
    CmsSiteResository cmsSiteResository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    public void savePageToServerPath(String pageId) throws IOException {
        //得到html文件的id，从cmspage中获取htmlFileId
        CmsPage cmsPage = findCmsPageById(pageId);
        if(cmsPage!=null){
            String htmlFileId = cmsPage.getHtmlFileId();
            InputStream inputStream =  getHtmlFileById(pageId);
            if(inputStream==null){
                log.error("FileInputStreamIsNull [{}]"+pageId);
                return;
            }
            //获取站点物理路径
            String siteId = cmsPage.getSiteId();
            if(siteId.isEmpty()){
                log.error("siteId is null [{}]"+htmlFileId);
                return;
            }
            CmsSite cmsSite = this.findCmsSiteById(siteId);
            if(cmsSite==null){
                log.error("cmsSite is null id is [{}]"+siteId);
                return;
            }
            String pagePath = cmsSite.getSitePhysicalPath() + cmsPage.getPagePhysicalPath()+cmsPage.getPageName();
            //写入本地
            FileOutputStream fileOutputStream = null;
            try {
                 fileOutputStream = new FileOutputStream(new File(pagePath));
                IOUtils.copy(inputStream,fileOutputStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                inputStream.close();
                fileOutputStream.close();
            }


        }
        //1.从GridFs中查询html文件
        //2.将html文件保存到服务器当中
    }
    //根据htmlFileId查询文件详细信息
    public InputStream getHtmlFileById(String htmlFileId){
        //获取文件对象
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        //打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //操作流
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    //根据页面id查询页面信息
    public CmsPage findCmsPageById(String pageId){
        Optional<CmsPage> cmsPage = cmsPageResository.findById(pageId);
        if(cmsPage.isPresent()){
            return cmsPage.get();

        }
        return null;
    }
    //根据页面id查询页面信息
    public CmsSite findCmsSiteById(String siteId){
        Optional<CmsSite> cmsSite = cmsSiteResository.findById(siteId);
        if(cmsSite.isPresent()){
            return cmsSite.get();

        }
        return null;
    }
}
