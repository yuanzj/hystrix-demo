package com.example.hystrixdemo.contorller;

import com.example.hystrixdemo.model.dto.AddressBean;
import com.example.hystrixdemo.retrofit.BaiduMapApi;
import com.example.hystrixdemo.util.HystrixCommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/address")
@RestController
public class TestController {

    @Autowired
    private BaiduMapApi baiduMapApi;

    @GetMapping("/decode")
    public AddressBean test(Double lon, Double lat) {

        return HystrixCommandUtil.execute(
                "BaiduMapApi",
                "decode",
                baiduMapApi.decode("v1Xba4zeGLr6CScN39OFgvhiADPaXezd",
                        "json",
                        "wgs84ll",
                        lat + "," + lon)
                , throwable -> {
                    log.error("触发出错返回，告警！", throwable);
                    return null;
                });


    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
