package com.xuecheng.mange_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")
@ComponentScan("com.xuecheng.api")
@ComponentScan
public class MangeCmsApplication {
    public static void main(String args[]){
        SpringApplication.run(MangeCmsApplication.class,args);
    }

}
