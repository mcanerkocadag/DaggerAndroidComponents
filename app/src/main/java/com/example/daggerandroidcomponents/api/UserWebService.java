package com.example.daggerandroidcomponents.api;

import com.example.daggerandroidcomponents.db.MyPojo;
import com.example.daggerandroidcomponents.db.UserDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserWebService {

    @GET("api/users?page=2")
    Call<MyPojo> getUsers();

    @GET("api/users/{id}")
    Call<UserDetail> getUser(@Path("id") int userID);

}
