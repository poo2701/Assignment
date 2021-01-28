package com.p.assignmentp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.p.assignmentp.database.DataBase;
import com.p.assignmentp.database.ProductData;
import com.p.assignmentp.retrofit.ProductModel;
import com.p.assignmentp.retrofit.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    MutableLiveData<List<ProductModel>> productListMutableLiveData;
    MutableLiveData<List<ProductData>> productCAtegoryListMutableLiveData;


    MutableLiveData<List<ProductData>> productDbLiveData;
    Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        init();
    }

    public void init() {

        productListMutableLiveData = new MutableLiveData<>(new ArrayList<>());
        productDbLiveData = new MutableLiveData<>(new ArrayList<>());
        productCAtegoryListMutableLiveData = new MutableLiveData<>(new ArrayList<>());
        repository = new Repository(getApplication());
    }

    public void getProductList() {
        repository.getProductList(productListMutableLiveData);
    }

    public void getProductCategoryWiseFromDb(String  category){
        DataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                productCAtegoryListMutableLiveData.postValue(DataBase.getDatabaseInstance(getApplication()).getProductDao().getCategoryWiseProduct(category));

            }
        });
    }

    public MutableLiveData<List<ProductModel>> getProductListMutableLiveData() {
        return productListMutableLiveData;
    }

    public void setProductListMutableLiveData(MutableLiveData<List<ProductModel>> productListMutableLiveData) {
        this.productListMutableLiveData = productListMutableLiveData;
    }

    public MutableLiveData<List<ProductData>> getProductDbLiveData() {
        return productDbLiveData;
    }

    public void setProductDbLiveData(MutableLiveData<List<ProductData>> productDbLiveData) {
        this.productDbLiveData = productDbLiveData;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<ProductData>> getProductCAtegoryListMutableLiveData() {
        return productCAtegoryListMutableLiveData;
    }

    public void setProductCAtegoryListMutableLiveData(MutableLiveData<List<ProductData>> productCAtegoryListMutableLiveData) {
        this.productCAtegoryListMutableLiveData = productCAtegoryListMutableLiveData;
    }
}
