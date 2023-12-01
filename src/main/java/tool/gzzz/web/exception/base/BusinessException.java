package tool.gzzz.web.exception.base;

/**
 * 业务异常
 * @author gz
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常消息
     */
    protected final String message;

    /**
     * 业务异常
     */
    public BusinessException(String message) {
        this.message = message;
    }

    /**
     * 异常获取
     */
    @Override
    public String getMessage() {
        return message;
    }

}
