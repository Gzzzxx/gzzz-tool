package tool.gzzz.web.domain.response.result;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * Ajax返回JSON结果封装数据
 * @author gz
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误状态
     */
    private int code;

    /**
     * 错误消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功操作
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功操作,携带数据
     */
    public static <T> Result<T> success(T data) {
        return success(ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功操作,携带消息
     */
    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    /**
     * 成功操作,携带消息和携带数据
     */
    public static <T> Result<T> success(String message, T data) {
        return success(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功操作,携带自定义状态码和消息
     */
    public static <T> Result<T> success(int code, String message) {
        return success(code, message, null);
    }

    /**
     * 成功操作,携带自定义状态码,消息和数据
     */
    public static <T> Result<T> success(int code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 失败操作,默认数据
     */
    public static <T> Result<T> failure() {
        return failure(ResultCode.FAILURE.getMessage());
    }

    /**
     * 失败操作,携带自定义消息
     */
    public static <T> Result<T> failure(String message) {
        return failure(message, null);
    }

    /**
     * 失败操作,携带自定义消息和数据
     */
    public static <T> Result<T> failure(String message, T data) {
        return failure(ResultCode.FAILURE.getCode(), message, data);
    }

    /**
     * 失败操作,携带自定义状态码和自定义消息
     */
    public static <T> Result<T> failure(int code, String message) {
        return failure(ResultCode.FAILURE.getCode(), message, null);
    }

    /**
     * 失败操作,携带自定义状态码,消息和数据
     */
    public static <T> Result<T> failure(int code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }

    /**
     * Boolean返回操作,携带默认返回值
     */
    public static <T> Result<T> decide(boolean b) {
        return decide(b, ResultCode.SUCCESS.getMessage(), ResultCode.FAILURE.getMessage());
    }

    /**
     * Boolean返回操作,携带自定义消息
     */
    public static <T> Result<T> decide(boolean b, String success, String failure) {
        if (b) {
            return success(success);
        } else {
            return failure(failure);
        }
    }

}
