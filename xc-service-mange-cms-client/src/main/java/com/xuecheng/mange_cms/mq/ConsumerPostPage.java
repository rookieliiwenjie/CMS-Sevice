package com.xuecheng.mange_cms.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.mange_cms.service.PageServer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ConsumerPostPage {
    @Autowired
    PageServer pageServer;
    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage  (String msg){
        Map map = JSON.parseObject(msg,Map.class);
        if(map.isEmpty()){
            return;
        }
        String pageId = (String) map.get("pageId");
        if(pageId.isEmpty()){
            return;
        }
        //校验cmspage
        CmsPage cmsPage  = pageServer.findCmsPageById(pageId);
        if(cmsPage==null){
            return;
        }
        try {
            pageServer.savePageToServerPath(pageId);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
