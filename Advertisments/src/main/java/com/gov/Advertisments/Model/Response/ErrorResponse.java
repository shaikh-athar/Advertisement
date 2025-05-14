package com.gov.Advertisments.Model.Response;

public class ErrorResponse {

    private String exception;
    private String msg;

    public ErrorResponse(String msg,String exception) {
        this.msg = msg;
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
