package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.mange_cms.dao.CmsSiteRespostitor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public  interface  CmsSiteService {

    public QueryResponseResult findAll();
}
