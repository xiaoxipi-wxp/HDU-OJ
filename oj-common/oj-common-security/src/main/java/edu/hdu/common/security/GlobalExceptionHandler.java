package edu.hdu.common.security;
import edu.hdu.commom.core.domain.Result;
import edu.hdu.commom.core.enums.ResultCode;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    //捕获任何类型的异常(兜底)
    @ExceptionHandler(Exception.class)
    public Result<?> handler(Exception e){
        return  Result.error();
    }

    //请求⽅式不⽀持
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handler(RuntimeException e){
        return Result.failed(e.getMessage(), ResultCode.FAILED);
    }

    //运⾏时异常
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handler(NullPointerException e){
        return  Result.error();
    }

}
