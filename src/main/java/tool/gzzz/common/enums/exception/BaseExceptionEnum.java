package tool.gzzz.common.enums.exception;

import tool.gzzz.common.enums.BaseEnums;

/**
 * @author gz
 */
public interface BaseExceptionEnum extends BaseEnums {

    int SYSTEM_ERR_CODE = 999999;

    /**
     * getErrMessage
     * @return String
     */
    String getErrMessage();

    /**
     * getErrCode
     * @return int
     */
    int getErrCode();
}
