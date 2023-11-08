package art.gzzz.util;

import art.gzzz.web.exception.base.BusinessException;
import java.util.regex.Pattern;

import static art.gzzz.common.constant.CommonConstant.*;

/**
 * @author gz
 */
public class DecideUtil {

  public static void decideBase64(String data ,String type) {
    String base64Rule = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
    if (!Pattern.matches(base64Rule, data)) {
      if (DATA.equals(type)) {
        throw new BusinessException("加密/解密内容不是Base64格式！");
      }
      if (KEY.equals(type)) {
        throw new BusinessException("密钥不是Base64格式！");
      }
      if (IV.equals(type)) {
        throw new BusinessException("向量不是Base64格式！");
      }
    }
  }
}
