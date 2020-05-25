package com.example.reactivex.laihuola;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/laihuola")
public class LHLController {

    private static final Logger logger = LoggerFactory.getLogger(LHLController.class);

    @Autowired
    private LHLService lhlService;

    @GetMapping("/login")
    public Mono<LoginResp<UserData>> login(@RequestParam("phone") String phone,
                                           @RequestParam("captcha") String captcha) {
        return lhlService.login(phone, captcha);
    }

    @PostMapping("/login-with-body")
    public Mono<LoginResp<UserData>> loginWithBody(@RequestBody LoginBody body) {
        return lhlService.login(body.getPhoneNumber(), body.getCaptchas());
    }

    @GetMapping("/print")
    public Mono<?> print(@RequestParam("phone") String phone,
                         @RequestParam("captcha") String captcha) {
        lhlService.print(phone, captcha);
        return Mono.just("ok");
    }

}
