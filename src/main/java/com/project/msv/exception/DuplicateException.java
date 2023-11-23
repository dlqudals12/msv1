package com.project.msv.exception;

public class DuplicateException extends CommonException{
    public DuplicateException(String msg) {
        this.code = "-3";
        this.msg = msg + " 중복되었습니다.";
    }
}
