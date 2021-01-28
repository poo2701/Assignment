package com.p.assignmentp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginDao {

    @Insert
    public void registerUser(LoginData loginData);

    @Query("SELECT * FROM login WHERE email == :email")
    public List<LoginData> getUserOfEmail(String email);
}
