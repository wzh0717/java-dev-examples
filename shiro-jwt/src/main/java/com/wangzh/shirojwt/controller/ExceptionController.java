package com.wangzh.shirojwt.controller;

import com.wangzh.app.commons.exception.BaseRuntimeException;
import com.wangzh.app.commons.result.R;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 异常处理
 * @Auther:wangzh
 * @Date: 2019/05/30 15:09
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * shiro exception
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public R handle401(ShiroException e) {
        return R.error(-1, "ShiroException");
    }

    /**
     * UnauthorizedException
     *
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public R handle401() {
        return R.error(-1, "UnauthorizedException");
    }

    /**
     * BaseRuntimeException
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BaseRuntimeException.class)
    public R handle401(BaseRuntimeException ex) {
        return R.error(-ex.getCode(), ex.getMsg());
    }

    /**
     * Exception
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R globalException(HttpServletRequest request, Throwable ex) {
        return R.error(-1, "Exception");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
