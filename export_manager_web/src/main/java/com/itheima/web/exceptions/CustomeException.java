package com.itheima.web.exceptions;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-06-16:57
 */
public class CustomeException extends Exception {
    private String message;

    public CustomeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
