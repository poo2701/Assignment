package com.p.assignmentp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.p.assignmentp.R;
import com.p.assignmentp.databinding.ActivitySignupBinding;
import com.p.assignmentp.viewmodel.LoginViewModel;
import com.p.assignmentp.viewmodel.SignupViewModel;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding mBinding;
    SignupViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        mBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        mBinding.setModel(mViewModel);
        mBinding.setClickhandller(new ClickHandler());


        observeLiveData();

    }

    private void observeLiveData() {

        mViewModel.getConfirmPasswordLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(mViewModel.getPasswordLiveData().getValue())){
                    mBinding.confirmPassword.setError(null);
                }else {
                    mBinding.confirmPassword.setError("Both password should match");
                }
            }
        });
    }

    public class ClickHandler{
        public void register(View veiw){
            mViewModel.registerUser();
        }
    }
}