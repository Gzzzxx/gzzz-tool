package tool.gzzz.web.service;

import tool.gzzz.web.domain.request.Sm4Request;
import tool.gzzz.web.domain.response.Sm4Response;

/**
 * @author gz
 */
public interface Sm4Service {

  /**
   * encrypt
   *
   * @param request request
   * @return Sm4Response
   * @throws Exception Exception
   */
  Sm4Response encrypt(Sm4Request request) throws Exception;

  /**
   * decrypt
   *
   * @param request request
   * @return Sm4Response
   * @throws Exception Exception
   */
  Sm4Response decrypt(Sm4Request request) throws Exception;
}
