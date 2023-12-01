package tool.gzzz.web.domain.request;

import lombok.Data;

/**
 * @author gz
 */
@Data
public class Sm4Request {

    /**
     * 算法
     */
    private String algorithmName;

    /**
     * 模式
     */
    private String mode;

    /**
     * 向量
     */
    private String iv;

    /**
     * 密钥
     */
    private String key;

    /**
     * 密钥/IV数据格式
     */
    private String keyType;

    /**
     * 加解密数据
     */
    private String data;

    /**
     * 加解密数据格式
     */
    private String dataType;

}
