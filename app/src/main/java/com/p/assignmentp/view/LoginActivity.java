package com.p.assignmentp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.p.assignmentp.R;
import com.p.assignmentp.database.DataBase;
import com.p.assignmentp.databinding.ActivityLoginBinding;
import com.p.assignmentp.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding mBinding;
    LoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        mBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mBinding.setModel(mViewModel);
        mBinding.setClickHandler(new ClickHandler());


    }

    public class ClickHandler{

        public void submit(View view){
            mViewModel.validateData();
        }

        public void signup(View view){
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));

        }

    }
}