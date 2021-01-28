package com.p.assignmentp.retrofit;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.p.assignmentp.util.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {


    ProductApi productApi;
    Context context;

    public Repository(Context context) {
        this.context = context;
        productApi = RetrofitService.createService(ProductApi.class);

    }

    public void getProductList(MutableLiveData<List<ProductModel>> productListMutableLiveData){
        productApi.getProduct().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful())
                productListMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
