package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class ExceptionCast {

    public static void cost(ResultCode resultCode) {
        throw new CmstomException(resultCode);
    }

}
