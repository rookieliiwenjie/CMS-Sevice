package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 */
@Slf4j
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    public static ImmutableMap<Class<? extends Throwable>, ResultCode> Exception;

    //定义map buider对象去建造Map
   protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();


    //   CmsEx類一場
    @ExceptionHandler(CmstomException.class)
    @ResponseBody
    public ResponseResult cutomException(CmstomException cms) {
        log.error("catch excption {}", cms.getMessage());

        ResultCode resultCode = cms.getResultCode();
        return new ResponseResult(resultCode);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult CatchException(Exception exception) {

       if(Exception==null){
           Exception=builder.build();
       }
       ResultCode resultCode = Exception.get(exception.getClass());
       if(resultCode!=null){
           return new ResponseResult(resultCode);
       }
       return new ResponseResult(CommonCode.SERVER_ERROR);
    }
    static {
        //定义异常类型错误代码
        builder.put(HttpRequestMethodNotSupportedException.class,CommonCode.INVALUB_PARAM);

    }
}
