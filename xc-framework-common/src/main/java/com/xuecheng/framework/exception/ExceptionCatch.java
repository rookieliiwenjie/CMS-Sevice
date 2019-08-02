package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 */
@Slf4j
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    //   CmsEx類一場
    @ExceptionHandler(CmstomException.class)
    @ResponseBody
    public ResponseResult cutomException(CmstomException cms){
        log.error("catch excption {}",cms.getMessage());
        ResultCode resultCode =cms.getResultCode();
        return new ResponseResult(resultCode);
    }
}
