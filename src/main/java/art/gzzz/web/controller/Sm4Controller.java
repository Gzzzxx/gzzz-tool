package art.gzzz.web.controller;

import art.gzzz.common.annotation.Log;
import art.gzzz.common.annotation.Repeat;
import art.gzzz.common.enums.BusinessType;
import art.gzzz.util.Sm4Util;
import art.gzzz.web.domain.request.Sm4Request;
import art.gzzz.web.domain.response.Sm4Response;
import art.gzzz.web.domain.response.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gz
 */
@RestController
@RequestMapping("/SM4")
public class Sm4Controller {

    @Repeat
    @PostMapping("/sm4Encrypt")
    @Log(title = "SM4加密", describe = "SM4加密", type = BusinessType.ENCRYPT)
    public Result<Sm4Response> sm4Encrypt(@RequestBody Sm4Request request) throws Exception {
        return Result.success(Sm4Util.encrypt(request));
    }

    @Repeat
    @PostMapping("/sm4Decrypt")
    @Log(title = "SM4解密", describe = "SM4解密", type = BusinessType.DECRYPT)
    public Result<Sm4Response> sm4Decrypt(@RequestBody Sm4Request request) throws Exception {
        return Result.success(Sm4Util.decrypt(request));
    }

}
