package com.p.assignmentp.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.material.navigation.NavigationView;
import com.p.assignmentp.R;
import com.p.assignmentp.database.DataBase;
import com.p.assignmentp.database.ProductData;
import com.p.assignmentp.databinding.ActivityMainBinding;
import com.p.assignmentp.retrofit.ProductModel;
import com.p.assignmentp.viewmodel.MainActivityViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    MainActivityViewModel mViewModel;
    ActivityMainBinding mBinding;
    DataBase db;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setModel(mViewModel);

        db = DataBase.getDatabaseInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = mBinding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.category,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        loadingDialog = new SpotsDialog.Builder()
                .setContext(MainActivity.this)
                .setMessage("Downloading product Data... ")
                .setCancelable(false)
                .build();

        handleDbList();
        observeLiveData();

    }

    private void handleDbList() {
        DataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<ProductData> pData = db.getProductDao().getProduct();
                if (pData!=null && pData.size()>0){
                    Log.i("TAG5", "run: "+pData.size());

                    mViewModel.getProductDbLiveData().postValue(pData);
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.show();
                    }
                });

                mViewModel.getProductList();

            }
        });

    }

    private void observeLiveData() {
        mViewModel.getProductListMutableLiveData().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                if (productModels!=null && productModels.size()>0){
                    loadingDialog.dismiss();
                    addListToDB(productModels);
                }
            }
        });
    }

    private void addListToDB(List<ProductModel> productModels) {
        DataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {


                for (ProductModel productModel:productModels){
                    ProductData productData = new ProductData(productModel.getTitle(),
                            productModel.getPrice(),
                            productModel.getImage(),
                            productModel.getCategory());
                    Log.i("TAG5", "run: "+productData.getName() );
                   db.getProductDao().addProduct(productData);

                }

                handleDbList();

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}