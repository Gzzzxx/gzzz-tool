package art.gzzz.web.service.impl;

import art.gzzz.util.Sm4Util;
import art.gzzz.web.domain.request.Sm4Request;
import art.gzzz.web.domain.response.Sm4Response;
import art.gzzz.web.service.Sm4Service;
import org.springframework.stereotype.Service;

/**
 * @author gz
 */
@Service
public class Sm4ServiceImpl implements Sm4Service {

  @Override
  public Sm4Response encrypt(Sm4Request request) throws Exception {
    return Sm4Util.encrypt(request);
  }

  @Override
  public Sm4Response decrypt(Sm4Request request) throws Exception {
    return Sm4Util.decrypt(request);
  }
}
