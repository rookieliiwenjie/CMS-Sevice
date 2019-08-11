package com.xuecheng.mange_cms.service.Impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.mange_cms.dao.CmsConfigRespostitory;
import com.xuecheng.mange_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CmsConfiglmpl implements CmsConfigService {
   @Autowired
    CmsConfigRespostitory cmsConfigRespostitory;
    @Override
    public CmsConfig findById(String id) {
       Optional<CmsConfig> cmsConfig =  cmsConfigRespostitory.findById(id);
       if(cmsConfig.isPresent()){
         return cmsConfig.get();
       }
          return null;


    }
}
