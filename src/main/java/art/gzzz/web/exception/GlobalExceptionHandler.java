package art.gzzz.web.exception;

import art.gzzz.web.domain.response.Result;
import art.gzzz.web.exception.base.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * @author gz
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 不支持的请求类型
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Object handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Result.failure("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler({RuntimeException.class})
    public Object notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return Result.failure("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.failure("服务器错误，请联系管理员");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object businessException( BusinessException e) {
        log.error(e.getMessage(), e);
        return Result.failure(e.getMessage());
    }

}
