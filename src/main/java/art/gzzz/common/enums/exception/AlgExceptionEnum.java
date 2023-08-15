package art.gzzz.common.enums.exception;

/**
 * @author gz
 */

public enum AlgExceptionEnum implements BaseExceptionEnum {

    /**
     * NOT_SUPPORT_ERROR
     */
    NOT_SUPPORT_ERROR("算法不支持", 200001),
    /**
     * IV_LEN_ERROR
     */
    IV_LEN_ERROR("初始化向量长度错误", 200002);

    private final String errMessage;
    private final int errCode;

    public String getErrMessage() {
        return this.errMessage;
    }

    public int getErrCode() {
        return this.errCode;
    }

    private AlgExceptionEnum(String errMessage, int errCode) {
        this.errMessage = errMessage;
        this.errCode = errCode;
    }
}
