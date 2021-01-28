package com.p.assignmentp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {

    @GET("products/")
    Call<List<ProductModel>> getProduct();
}
