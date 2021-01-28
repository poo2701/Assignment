package com.p.assignmentp.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.p.assignmentp.database.ProductData;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {
    MutableLiveData<List<ProductData>> categoryProductLiveData;

    public GalleryViewModel(@NonNull Application application) {
        super(application);

        init();
    }

    public void init() {
        categoryProductLiveData = new MutableLiveData<>(new ArrayList<>());
    }


}