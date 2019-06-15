package com.wangzh.app.weixin.pa.exception;

import com.wangzh.app.commons.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/06/15 18:38
 */
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 500 错误s
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public R exceptionHandler(Exception ex) {
        return R.error(500, "服务器内部错误");
    }
}
