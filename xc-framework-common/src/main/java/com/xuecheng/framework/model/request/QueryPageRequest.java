package com.xuecheng.framework.model.request;

import lombok.Data;

@Data
public class QueryPageRequest {
    private String siteId;
    private String pageId;
    private String pageName;
    private String PageAliase;
    private String templateId;
}
