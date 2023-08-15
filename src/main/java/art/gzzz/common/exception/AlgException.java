package art.gzzz.common.exception;

import art.gzzz.common.enums.exception.BaseExceptionEnum;

/**
 * @author gz
 */
public class AlgException extends BaseException {

    public AlgException(BaseExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }

    public AlgException(BaseExceptionEnum exceptionEnum, Throwable cause) {
        super(exceptionEnum, cause);
    }

    public AlgException(String message) {
        super(message);
    }

    public AlgException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgException(Throwable cause) {
        super(cause);
    }
}
