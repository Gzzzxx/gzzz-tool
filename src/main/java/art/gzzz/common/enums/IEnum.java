package art.gzzz.common.enums;

import art.gzzz.common.enums.exception.AlgExceptionEnum;
import art.gzzz.common.exception.AlgException;
import java.util.Arrays;

/**
 * @author gz
 */
public interface IEnum {

    /**
     * getAlgorithm
     * @return String
     */
    String getAlgorithm();

    public static enum CipherAlgorithmEnum implements IEnum {

        /**
         * AES_CBC_PKCS7PADDING
         */
        AES_CBC_PKCS7PADDING("AES/CBC/PKCS7Padding", "CBC", "PKCS7Padding", true, IEnum.KeyAlgorithmEnum.AES_128),
        /**
         * AES_CBC_PKCS5PADDING
         */
        AES_CBC_PKCS5PADDING("AES/CBC/PKCS5Padding", "CBC", "PKCS5Padding", true, IEnum.KeyAlgorithmEnum.AES_128),
        /**
         * AES_ECB_PKCS7PADDING
         */
        AES_ECB_PKCS7PADDING("AES/ECB/PKCS7Padding", "ECB", "PKCS7Padding", false, IEnum.KeyAlgorithmEnum.AES_128),
        /**
         * AES_ECB_PKCS5PADDING
         */
        AES_ECB_PKCS5PADDING("AES/ECB/PKCS5Padding", "ECB", "PKCS5Padding", false, IEnum.KeyAlgorithmEnum.AES_128),
        /**
         * SM4_CBC_PKCS7PADDING
         */
        SM4_CBC_PKCS7PADDING("SM4/CBC/PKCS7Padding", "CBC", "PKCS7Padding", true, IEnum.KeyAlgorithmEnum.SM4_128),
        /**
         * SM4_CBC_PKCS5PADDING
         */
        SM4_CBC_PKCS5PADDING("SM4/CBC/PKCS5Padding", "CBC", "PKCS5Padding", true, IEnum.KeyAlgorithmEnum.SM4_128),
        /**
         * SM4_ECB_PKCS7PADDING
         */
        SM4_ECB_PKCS7PADDING("SM4/ECB/PKCS7Padding", "ECB", "PKCS7Padding", false, IEnum.KeyAlgorithmEnum.SM4_128),
        /**
         * SM4_ECB_PKCS5PADDING
         */
        SM4_ECB_PKCS5PADDING("SM4/ECB/PKCS5Padding", "ECB", "PKCS5Padding", false, IEnum.KeyAlgorithmEnum.SM4_128),
        /**
         * SM4_CTR_NOPADDING
         */
        SM4_CTR_NOPADDING("SM4/CTR/NoPadding", "CTR", "NoPadding", true, IEnum.KeyAlgorithmEnum.SM4_128);

        private final String algorithm;
        private final String mode;
        private final String padding;
        private final boolean isNeedIv;
        private final KeyAlgorithmEnum keyAlgorithmEnum;

        public static CipherAlgorithmEnum match(AlgEnums.ModeEnum modeEnum, String algorithm) throws AlgException {
            KeyAlgorithmEnum keyAlgorithmEnum = IEnum.KeyAlgorithmEnum.match(algorithm);
            return (Arrays.stream(values()).parallel()).filter((anEnum) -> {
                return anEnum.mode.equals(modeEnum.name()) && anEnum.keyAlgorithmEnum == keyAlgorithmEnum;
            }).findFirst().orElseThrow(() -> {
                return new AlgException(AlgExceptionEnum.NOT_SUPPORT_ERROR);
            });
        }

        private CipherAlgorithmEnum(String algorithm, String mode, String padding, boolean isNeedIv, KeyAlgorithmEnum keyAlgorithmEnum) {
            this.algorithm = algorithm;
            this.mode = mode;
            this.padding = padding;
            this.isNeedIv = isNeedIv;
            this.keyAlgorithmEnum = keyAlgorithmEnum;
        }

        public String getAlgorithm() {
            return this.algorithm;
        }

        public String getMode() {
            return this.mode;
        }

        public String getPadding() {
            return this.padding;
        }

        public boolean isNeedIv() {
            return this.isNeedIv;
        }

        public KeyAlgorithmEnum getKeyAlgorithmEnum() {
            return this.keyAlgorithmEnum;
        }

    }


    public static enum KeyAlgorithmEnum implements IEnum {

        /**
         * AES_128
         */
        AES_128("AES", 128),
        /**
         * SM4_128
         */
        SM4_128("SM4", 128);

        private final String algorithm;
        private final int keyLength;

        public static KeyAlgorithmEnum match(String alg) throws AlgException {
            return (Arrays.stream(values()).parallel()).filter((anEnum) -> {
                return anEnum.algorithm.equals(alg);
            }).findFirst().orElseThrow(() -> {
                return new AlgException(AlgExceptionEnum.NOT_SUPPORT_ERROR);
            });
        }

        private KeyAlgorithmEnum(String algorithm, int keyLength) {
            this.algorithm = algorithm;
            this.keyLength = keyLength;
        }

        public String getAlgorithm() {
            return this.algorithm;
        }

        public int getKeyLength() {
            return this.keyLength;
        }
    }

}
