package art.gzzz.common.enums;

import art.gzzz.common.enums.exception.AlgExceptionEnum;
import art.gzzz.common.exception.AlgException;
import org.bouncycastle.asn1.ASN1Enumerated;
import java.util.Arrays;

/**
 * @author gz
 */
public interface AlgEnums extends BaseEnums {

    public static enum ModeEnum implements AlgEnums {

        /**
         * ECB
         */
        ECB(false),
        /**
         * CBC
         */
        CBC(true);

        private final boolean isNeedIv;

        public static ModeEnum match(String mode) throws AlgException {
            return ( Arrays.stream(values()).parallel()).filter((anEnum) -> {
                return anEnum.name().equalsIgnoreCase(mode);
            }).findFirst().orElseThrow(() -> {
                return new AlgException(AlgExceptionEnum.NOT_SUPPORT_ERROR);
            });
        }

        public boolean isNeedIv() {
            return this.isNeedIv;
        }

        private ModeEnum(boolean isNeedIv) {
            this.isNeedIv = isNeedIv;
        }
    }

    public static enum PaddingTypeEnum implements AlgEnums {
        /**
         * NOPADDING
         */
        NOPADDING(new ASN1Enumerated(0), "NoPadding"),
        /**
         * PKCS7PADDING
         */
        PKCS7PADDING(new ASN1Enumerated(1), "PKCS7Padding");

        private final ASN1Enumerated type;
        private final String value;

        public static PaddingTypeEnum match(String padding) throws AlgException {
            return (Arrays.stream(values()).parallel()).filter((anEnum) -> {
                return anEnum.name().equalsIgnoreCase(padding);
            }).findFirst().orElseThrow(() -> {
                return new AlgException(AlgExceptionEnum.NOT_SUPPORT_ERROR);
            });
        }

        public static PaddingTypeEnum match(ASN1Enumerated padding) throws AlgException {
            return (Arrays.stream(values()).parallel()).filter((anEnum) -> {
                return anEnum.type.getValue().equals(padding.getValue());
            }).findFirst().orElseThrow(() -> {
                return new AlgException(AlgExceptionEnum.NOT_SUPPORT_ERROR);
            });
        }

        public ASN1Enumerated getType() {
            return this.type;
        }

        public String getValue() {
            return this.value;
        }

        private PaddingTypeEnum(ASN1Enumerated type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
