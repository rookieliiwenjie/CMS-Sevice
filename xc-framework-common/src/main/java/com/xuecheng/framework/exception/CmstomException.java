package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class CmstomException extends RuntimeException {
    ResultCode resultCode;

    public CmstomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }

}
