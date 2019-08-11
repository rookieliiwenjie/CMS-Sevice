package com.xuecheng.mange_cms;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")
@ComponentScan("com.xuecheng.api")
@ComponentScan("com.xuecheng.framework.exception")
public class MangeCmsApplication {
    public static void main(String args[]){
        SpringApplication.run(MangeCmsApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate (){
        return  new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
