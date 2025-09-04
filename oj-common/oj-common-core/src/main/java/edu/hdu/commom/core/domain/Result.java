package edu.hdu.commom.core.domain;


import edu.hdu.commom.core.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {
    private T data;
    private int code;
    private String msg;

    public static <T> Result<T> success() {
        return assembleResult(null, ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return assembleResult(data,ResultCode.SUCCESS);
    }

    public static <T> Result<T> error() {
        return assembleResult(null, ResultCode.ERROR);
    }

    public static <T> Result<T> failed(ResultCode resultCode) {
        return assembleResult(null,resultCode);
    }

    public static <T> Result<T> failed(T  data, ResultCode resultCode) {
        return assembleResult(data,resultCode);
    }

    public static <T> Result<T> assembleResult(T data, ResultCode resultCode) {
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        return result;
    }
}
