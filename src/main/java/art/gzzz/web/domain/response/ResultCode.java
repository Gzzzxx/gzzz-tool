package art.gzzz.web.domain.response;

import lombok.Getter;

/**
 * 统一返回状态码
 * @author gz
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 失败
     */
    FAILURE(500, "操作失败"),

    /**
     * 重复提交
     * */
    REPEAT_SUBMIT(701,"不允许重复提交，请稍后再试");

    /**
     * 状态码
     */
    @Getter
    private final int code;

    /**
     * 携带消息
     */
    @Getter
    private final String message;

    /**
     * 构造方法
     */
    ResultCode(int code, String message) {

        this.code = code;

        this.message = message;
    }

}
