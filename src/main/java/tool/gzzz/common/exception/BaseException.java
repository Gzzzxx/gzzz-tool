package tool.gzzz.common.exception;

import tool.gzzz.common.enums.exception.BaseExceptionEnum;

/**
 * @author gz
 */
public abstract class BaseException extends RuntimeException {

    private final String errMessage;
    private final int errCode;

    public BaseException(BaseExceptionEnum exceptionEnum) {
        super(exceptionEnum.getErrMessage());
        this.errCode = exceptionEnum.getErrCode();
        this.errMessage = exceptionEnum.getErrMessage();
    }

    public BaseException(BaseExceptionEnum exceptionEnum, Throwable cause) {
        super(exceptionEnum.getErrMessage(), cause);
        this.errCode = exceptionEnum.getErrCode();
        this.errMessage = exceptionEnum.getErrMessage();
    }

    public BaseException(String message) {
        super(message);
        this.errMessage = message;
        this.errCode = 999999;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errMessage = message;
        this.errCode = 999999;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.errMessage = cause.getMessage();
        this.errCode = 999999;
    }
}
