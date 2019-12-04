package com.example.hystrixdemo;

import com.example.hystrixdemo.retrofit.BaiduMapApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SpringBootApplication
public class HystrixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDemoApplication.class, args);
    }

    @Bean
    public BaiduMapApi baiduMapApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BaiduMapApi.class);
    }

}
