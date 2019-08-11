package com.xuecheng.api.config.api;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface CmsConfigControllerApi {
    public CmsConfig getModel(String id);
}
