package com.xuecheng.mange_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface CmsConfigService {
    CmsConfig findById(String id);
}
