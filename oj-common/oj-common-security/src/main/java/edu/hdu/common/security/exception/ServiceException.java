package edu.hdu.common.security.exception;

import edu.hdu.commom.core.enums.ResultCode;
import lombok.Data;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private ResultCode resultCode;
    public ServiceException(ResultCode resultCode){
        this.resultCode=resultCode;
    }
}
