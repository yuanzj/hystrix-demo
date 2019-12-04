package com.example.hystrixdemo.retrofit;

import com.example.hystrixdemo.model.dto.AddressBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaiduMapApi {

    @GET("reverse_geocoding/v3/")
    Call<AddressBean> decode(@Query("ak") String ak,
                                       @Query("output") String output,
                                       @Query("coordtype") String coordtype,
                                       @Query("location") String location);

}
