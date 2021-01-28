package com.p.assignmentp.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.p.assignmentp.database.DataBase;
import com.p.assignmentp.database.LoginData;
import com.p.assignmentp.view.LoginActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SignupViewModel extends AndroidViewModel {

    MutableLiveData<String> nameLiveData;
    MutableLiveData<String> lastNameLiveData;
    MutableLiveData<String> passwordLiveData;
    MutableLiveData<String> confirmPasswordLiveData;
    MutableLiveData<String> emailLiveData;

    public SignupViewModel(@NonNull Application application) {
        super(application);

        init();
    }

    public void init() {
        nameLiveData = new MutableLiveData<>("");
        lastNameLiveData = new MutableLiveData<>("");
        emailLiveData = new MutableLiveData<>("");
        passwordLiveData = new MutableLiveData<>("");
        confirmPasswordLiveData = new MutableLiveData<>("");
    }

    public void registerUser(){
        if (validate()) {
            DataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    LoginData loginData = new LoginData(nameLiveData.getValue(),
                            lastNameLiveData.getValue(),
                            emailLiveData.getValue(),
                            passwordLiveData.getValue());
                    DataBase.getDatabaseInstance(getApplication()).getLoginDao().registerUser(loginData);
                }
            });

            getApplication().startActivity(new Intent(getApplication(), LoginActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    private boolean validate() {
        if (!passwordLiveData.getValue().equals(confirmPasswordLiveData.getValue())  && !passwordLiveData.getValue().trim().isEmpty()){
            Toast.makeText(getApplication(), "Password not matched", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nameLiveData.getValue().trim().isEmpty()){
            Toast.makeText(getApplication(), "Please enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastNameLiveData.getValue().trim().isEmpty()){
            Toast.makeText(getApplication(), "Please enter Last Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (emailLiveData.getValue().trim().isEmpty()){
            Toast.makeText(getApplication(), "Please enter email Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!emailLiveData.getValue().contains("@")){
            Toast.makeText(getApplication(), "Please enter a valid email ", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    //Getter and Setter

    public MutableLiveData<String> getNameLiveData() {
        return nameLiveData;
    }

    public void setNameLiveData(MutableLiveData<String> nameLiveData) {
        this.nameLiveData = nameLiveData;
    }

    public MutableLiveData<String> getLastNameLiveData() {
        return lastNameLiveData;
    }

    public void setLastNameLiveData(MutableLiveData<String> lastNameLiveData) {
        this.lastNameLiveData = lastNameLiveData;
    }

    public MutableLiveData<String> getPasswordLiveData() {
        return passwordLiveData;
    }

    public void setPasswordLiveData(MutableLiveData<String> passwordLiveData) {
        this.passwordLiveData = passwordLiveData;
    }

    public MutableLiveData<String> getConfirmPasswordLiveData() {
        return confirmPasswordLiveData;
    }

    public void setConfirmPasswordLiveData(MutableLiveData<String> confirmPasswordLiveData) {
        this.confirmPasswordLiveData = confirmPasswordLiveData;
    }

    public MutableLiveData<String> getEmailLiveData() {
        return emailLiveData;
    }

    public void setEmailLiveData(MutableLiveData<String> emailLiveData) {
        this.emailLiveData = emailLiveData;
    }
}
