package com.xuecheng.mange_cms.service.Impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.mange_cms.dao.CmsSiteRespostitor;
import com.xuecheng.mange_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsSiteServicelmpl implements CmsSiteService {
    @Autowired
    CmsSiteRespostitor cmsSiteRespostitor;
    public QueryResponseResult findAll() {
        QueryResult queryResult = new QueryResult();
        List<CmsSite> cmsSiteList = cmsSiteRespostitor.findAll();
        queryResult.setList(cmsSiteList);
        queryResult.setTotal(cmsSiteList.size());

        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
