package com.project.msv.exception;

public class NoneException extends CommonException {
    public NoneException(String msg) {
        super();
        this.code = "-8";
        this.msg = msg + " 없습니다.";
    }
}
