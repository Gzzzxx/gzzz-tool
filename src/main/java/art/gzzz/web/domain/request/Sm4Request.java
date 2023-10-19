package art.gzzz.web.domain.request;

import lombok.Data;

/**
 * @author gz
 */
@Data
public class Sm4Request {

    private String algorithmName;

    private String mode;

    private String key;

    private String keyType;

    private String data;

    private String dataType;

}
