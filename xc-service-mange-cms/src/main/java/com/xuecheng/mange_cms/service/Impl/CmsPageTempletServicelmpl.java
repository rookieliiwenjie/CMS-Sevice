package com.xuecheng.mange_cms.service.Impl;

import com.xuecheng.api.config.api.CmsTempletControllerApi;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.mange_cms.dao.CmsTempletRepostitory;
import com.xuecheng.mange_cms.service.CmsTempletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsPageTempletServicelmpl implements CmsTempletService {
    @Autowired
    CmsTempletRepostitory cmsTempletRepostitory;
    @Override
    public QueryResponseResult findAll() {
        List<CmsTemplate> cmsTemplateList = cmsTempletRepostitory.findAll();
        if(!cmsTemplateList.isEmpty()){
            QueryResult<CmsTemplate> cmsTemplateQueryResult = new QueryResult<>();
            cmsTemplateQueryResult.setList(cmsTemplateList);
            cmsTemplateQueryResult.setTotal(cmsTemplateList.size());

            return new QueryResponseResult(CommonCode.SUCCESS,cmsTemplateQueryResult);
        }
       return new QueryResponseResult(CommonCode.FAIL,null);
    }
}
