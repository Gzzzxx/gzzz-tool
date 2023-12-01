package tool.gzzz.util;

import tool.gzzz.web.exception.base.BusinessException;
import tool.gzzz.common.constant.CommonConstant;

import java.util.regex.Pattern;

/**
 * @author gz
 */
public class DecideUtil {

  public static void decideBase64(String data ,String type) {
    String base64Rule = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
    if (!Pattern.matches(base64Rule, data)) {
      if (CommonConstant.DATA.equals(type)) {
        throw new BusinessException("加密/解密内容不是Base64格式！");
      }
      if (CommonConstant.KEY.equals(type)) {
        throw new BusinessException("密钥不是Base64格式！");
      }
      if (CommonConstant.IV.equals(type)) {
        throw new BusinessException("向量不是Base64格式！");
      }
    }
  }
}
