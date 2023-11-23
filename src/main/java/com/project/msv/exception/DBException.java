package com.project.msv.exception;

public class DBException extends CommonException{
    public DBException(String msg) {
        super();
        this.code = "-1";
        this.msg = msg + " 처리하는데 이상이 생겼습니다.";
    }
}
