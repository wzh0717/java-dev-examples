package com.wangzh.app.commons.exception;

/**
 * @Description: 运行时异常
 * @CreatedDate:2019-03-25 16:12
 * @Author:wangzh
 */
public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -1102606719960134442L;
    private String code;
    private String msg;

    public BaseRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseRuntimeException(String msg, Exception e) {
        super(msg, e);
        this.msg = msg;
    }

    public BaseRuntimeException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseRuntimeException(String code, String msg, Exception e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
