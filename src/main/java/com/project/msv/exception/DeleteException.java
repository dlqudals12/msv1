package com.project.msv.exception;

public class DeleteException extends CommonException {

    public DeleteException(String msg) {
        this.msg = msg + " 취소할 수 있습니다.";
        this.code = "-15";
    }
}
