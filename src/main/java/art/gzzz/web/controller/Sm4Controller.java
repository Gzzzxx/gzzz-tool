package art.gzzz.web.controller;

import art.gzzz.common.annotation.Log;
import art.gzzz.common.annotation.Repeat;
import art.gzzz.common.enums.BusinessType;
import art.gzzz.web.domain.request.Sm4Request;
import art.gzzz.web.domain.response.Sm4Response;
import art.gzzz.web.domain.response.result.Result;
import art.gzzz.web.service.Sm4Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gz
 */
@RestController
@RequestMapping("/sm4")
public class Sm4Controller {

    @Resource
    private Sm4Service sm4Service;

    @Repeat
    @PostMapping("/encrypt")
    @Log(title = "SM4加密", describe = "SM4加密", type = BusinessType.ENCRYPT)
    public Result<Sm4Response> sm4Encrypt(@RequestBody Sm4Request request) throws Exception {
        return Result.success(sm4Service.encrypt(request));
    }

    @Repeat
    @PostMapping("/decrypt")
    @Log(title = "SM4解密", describe = "SM4解密", type = BusinessType.DECRYPT)
    public Result<Sm4Response> sm4Decrypt(@RequestBody Sm4Request request) throws Exception {
        return Result.success(sm4Service.decrypt(request));
    }

}
