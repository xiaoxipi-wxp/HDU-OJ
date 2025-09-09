package edu.hdu.common.security.handler;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import edu.hdu.commom.core.domain.Result;
import edu.hdu.commom.core.enums.ResultCode;
import edu.hdu.common.security.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    //捕获任何类型的异常(兜底)
    @ExceptionHandler(Exception.class)
    public Result<?> handler(Exception e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生异常",requestURI,e.getCause());
        return  Result.error();
    }

    //请求⽅式不⽀持
    @ExceptionHandler(ServiceException.class)
    public Result<?> handler(ServiceException e, HttpServletRequest request){
        ResultCode resultCode = e.getResultCode();
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求",requestURI,resultCode.getMsg(),e);
        return Result.failed(resultCode);
    }

    //请求⽅式不⽀持
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handler(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生请求异常{}",requestURI,e.getMethod());
        return Result.failed(e.getMessage(), ResultCode.FAILED);
    }

    //运⾏时异常
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handler(RuntimeException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生运行时异常",requestURI,e.getCause());
        return  Result.error();
    }

    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        log.error(e.getMessage());
        String message = join(e.getAllErrors(),
                DefaultMessageSourceResolvable::getDefaultMessage, ", ");
        return Result.failed(ResultCode.FAILED_PARAMS_VALIDATE.getCode(), message);
    }
    private <E> String join(Collection<E> collection, Function<E, String>
            function, CharSequence delimiter) {
        if (CollUtil.isEmpty(collection)) {
            return StrUtil.EMPTY;
        }
        return
                collection.stream().map(function).filter(Objects::nonNull).collect(Collectors.joining(delimiter));
    }

}
