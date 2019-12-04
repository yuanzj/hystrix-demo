package com.example.hystrixdemo.retrofit;

import com.example.hystrixdemo.model.dto.AddressBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@Slf4j
@SpringBootTest
class BaiduMapApiTest {

    @Autowired
    private BaiduMapApi baiduMapApi;

    @Test
    void decode() throws IOException {

        AddressBean addressBean = baiduMapApi.decode(
                "v1Xba4zeGLr6CScN39OFgvhiADPaXezd",
                "json",
                "wgs84ll",
                "31.225696563611,121.49884033194").execute().body();
        if (addressBean != null) {
            log.info(addressBean.toString());
        }
    }
}