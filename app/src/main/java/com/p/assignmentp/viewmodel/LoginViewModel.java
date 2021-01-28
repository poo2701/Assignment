package com.p.assignmentp.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.p.assignmentp.database.DataBase;
import com.p.assignmentp.database.LoginData;
import com.p.assignmentp.util.SharedPrefrence;
import com.p.assignmentp.view.MainActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LoginViewModel extends AndroidViewModel {

    MutableLiveData<String> emailLiveData;
    MutableLiveData<String> passwordLiveData;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    public void init() {
        emailLiveData = new MutableLiveData<>("");
        passwordLiveData =new MutableLiveData<>("");
    }


    public void validateData(){
        if (validate()) {
            DataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    List<LoginData> loginDetail = DataBase.getDatabaseInstance(getApplication()).getLoginDao().getUserOfEmail(emailLiveData.getValue());
                    handleData(loginDetail);
                }
            });
        }

    }

    private void handleData(List<LoginData> loginDetail) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (loginDetail != null) {
                    if (loginDetail.size() > 0) {
                        if (loginDetail.get(0).getPassword().equals(passwordLiveData.getValue())) {
                            SharedPrefrence.setLogin(getApplication(),"true");
                            getApplication().startActivity(new Intent(getApplication(), MainActivity.class)
                                    .addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        } else {
                            Toast.makeText(getApplication(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "User Doesn't Exists please Signup", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private boolean validate() {
        if (emailLiveData.getValue().trim().isEmpty()){
            Toast.makeText(getApplication(), "Please enter email Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!emailLiveData.getValue().contains("@")){
            Toast.makeText(getApplication(), "Please enter a valid email ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordLiveData.getValue().trim().isEmpty()){
            Toast.makeText(getApplication(), "Please enter your password ", Toast.LENGTH_SHORT).show();

        }

        return true;
    }


    //Getter and setter

    public MutableLiveData<String> getEmailLiveData() {
        return emailLiveData;
    }

    public void setEmailLiveData(MutableLiveData<String> emailLiveData) {
        this.emailLiveData = emailLiveData;
    }

    public MutableLiveData<String> getPasswordLiveData() {
        return passwordLiveData;
    }

    public void setPasswordLiveData(MutableLiveData<String> passwordLiveData) {
        this.passwordLiveData = passwordLiveData;
    }
}
