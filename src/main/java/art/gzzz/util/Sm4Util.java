package art.gzzz.util;

import art.gzzz.common.constant.CommonConstant;
import art.gzzz.common.enums.AlgEnums;
import art.gzzz.common.enums.IEnum;
import art.gzzz.web.domain.request.Sm4Request;
import art.gzzz.web.domain.response.Sm4Response;
import art.gzzz.web.exception.base.BusinessException;
import cn.hutool.core.util.StrUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import static art.gzzz.common.constant.CommonConstant.*;

/**
 * sm4加密算法工具类
 * sm4加密、解密与加密结果验证 可逆算法
 *
 * @author gz
 */
public class Sm4Util {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";
    public static final String ALGORITHM_NAME = "SM4";
    public static final int DEFAULT_KEY_BIT_SIZE = 128;
    public static final int DEFAULT_KEY_BYTE_SIZE = 16;
    public static final String ENCRYPT = "ENCRYPT";
    public static final String DECRYPT = "DECRYPT";

    /**
     * sm4加密
     *
     * @param request request
     * @return Sm4Response
     * @throws Exception Exception
     */
    public static Sm4Response encrypt(Sm4Request request) throws Exception {

        checkParam(request);

        Sm4Response response = new Sm4Response();

        byte[] keyData = handleKey(request);

        byte[] srcData = handleData(request, ENCRYPT);

        byte[] ivData = handleIv(request);

        byte[] encryptArray = encryptPadding(request.getAlgorithmName(), request.getMode(), keyData, srcData, ivData);

        response.setData(new String(Base64.encode(encryptArray)));

        return response;
    }

    /**
     * sm4解密
     *
     * @return Sm4Response
     * @throws Exception Exception
     */
    public static Sm4Response decrypt(Sm4Request request) throws Exception {

        checkParam(request);

        Sm4Response response = new Sm4Response();

        byte[] keyData = handleKey(request);

        byte[] srcData = handleData(request, DECRYPT);

        byte[] ivData = handleIv(request);

        byte[] decryptArray = decryptPadding(request.getAlgorithmName(), request.getMode(), keyData, srcData, ivData);

        response.setData(new String(decryptArray));

        return response;
    }

    /**
     * 加密
     *
     * @param algorithmName algorithmName
     * @param mode          mode
     * @param key           key
     * @param data          data
     * @param iv            iv
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] encryptPadding(String algorithmName, String mode, byte[] key, byte[] data, byte[] iv) throws Exception {
        Cipher cipher = generateCipher(algorithmName, mode, key, Cipher.ENCRYPT_MODE, iv);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param algorithmName algorithmName
     * @param mode          mode
     * @param key           key
     * @param data          data
     * @param iv            iv
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] decryptPadding(String algorithmName, String mode, byte[] key, byte[] data, byte[] iv) throws Exception {
        Cipher cipher = generateCipher(algorithmName, mode, key, Cipher.DECRYPT_MODE, iv);
        try {
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * generateCipher
     *
     * @param algorithmName 算法名称
     * @param mode          模式
     * @param key           key
     * @return Cipher
     * @throws Exception Exception
     */
    private static Cipher generateCipher(String algorithmName, String mode, byte[] key, int opMode, byte[] iv) throws Exception {
        IEnum.CipherAlgorithmEnum cipherAlgorithmEnum = IEnum.CipherAlgorithmEnum.match(AlgEnums.ModeEnum.match(mode), algorithmName);
        Cipher cipher = Cipher.getInstance(cipherAlgorithmEnum.getAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
        SecretKeySpec sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        try {
            if (ECB.equals(mode)) {
                cipher.init(opMode, sm4Key);
            }
            if (CBC.equals(mode)) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(opMode, sm4Key, ivParameterSpec);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return cipher;
    }

    /**
     * 自动生成密钥
     *
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] generateKey() throws Exception {
        return generateKey(DEFAULT_KEY_BIT_SIZE);
    }

    /**
     * 自动生成密钥
     *
     * @param keySize keySize
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * 校验加密前后的字符串是否为同一数据
     *
     * @param hexKey     16进制密钥（忽略大小写）
     * @param cipherText 16进制加密后的字符串
     * @param paramStr   加密前的字符串
     * @return boolean 是否为同一数据
     * @throws Exception Exception
     */
    public static boolean verifyEcb(String algorithmName, String mode, String hexKey, String cipherText, String paramStr) throws Exception {
        // 用于接收校验结果
        boolean flag = false;
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // 将16进制字符串转换成数组
        byte[] cipherData = ByteUtils.fromHexString(cipherText);

        byte[] iv = new byte[0];
        // 解密
        byte[] decryptData = decryptPadding(algorithmName, mode, keyData, cipherData, iv);
        // 将原字符串转换成byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 判断2个数组是否一致
        flag = Arrays.equals(decryptData, srcData);
        return flag;
    }

    private static byte[] handleKey(Sm4Request request) {

        byte[] keyData = new byte[0];

        if (CommonConstant.TEXT.equals(request.getKeyType())) {
            keyData = request.getKey().getBytes();
        }

        if (CommonConstant.HEX.equals(request.getKeyType())) {
            keyData = ByteUtils.fromHexString(request.getKey());
        }

        if (CommonConstant.BASE64.equals(request.getKeyType())) {
            DecideUtil.decideBase64(request.getKey(), KEY);
            keyData = Base64.decode(request.getKey());
        }

        if (keyData.length != DEFAULT_KEY_BYTE_SIZE) {
            throw new BusinessException("密钥长度必须为128位！");
        }

        return keyData;
    }

    private static byte[] handleData(Sm4Request request, String type) {

        byte[] data = new byte[0];

        if (CommonConstant.TEXT.equals(request.getDataType())) {
            if (ENCRYPT.equals(type)) {
                data = request.getData().getBytes();
            }
            if (DECRYPT.equals(type)) {
                DecideUtil.decideBase64(request.getData(), DATA);
                data = Base64.decode(request.getData());
            }
        }

        if (CommonConstant.HEX.equals(request.getDataType())) {
            data = ByteUtils.fromHexString(request.getData());
        }

        if (CommonConstant.BASE64.equals(request.getDataType())) {
            DecideUtil.decideBase64(request.getData(), DATA);
            data = Base64.decode(request.getData());
        }

        return data;
    }

    private static byte[] handleIv(Sm4Request request) {

        byte[] data = new byte[0];

        if (CBC.equals(request.getMode())) {

            if (CommonConstant.HEX.equals(request.getKeyType())) {
                data = ByteUtils.fromHexString(request.getIv());
            }

            if (CommonConstant.BASE64.equals(request.getKeyType())) {
                DecideUtil.decideBase64(request.getIv(), IV);
                data = Base64.decode(request.getIv());
            }
        }

        return data;
    }

    private static void checkParam(Sm4Request request) {

        if (StrUtil.isBlank(request.getData())) {
            throw new BusinessException("请输入加密/解密内容！");
        }

        if (StrUtil.isBlank(request.getKey())) {
            throw new BusinessException("请输入密钥！");
        }

        if (CBC.equals(request.getMode()) && StrUtil.isBlank(request.getIv())) {
            throw new BusinessException("CBC模式向量不能为空！");
        }
    }

}


