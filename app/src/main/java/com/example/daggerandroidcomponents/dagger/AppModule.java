package com.example.daggerandroidcomponents.dagger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.daggerandroidcomponents.api.UserWebService;
import com.example.daggerandroidcomponents.db.MyDatabase;
import com.example.daggerandroidcomponents.db.UserDao;
import com.example.daggerandroidcomponents.ui.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule
{
    private final static String BASE_URL = "https://reqres.in/";

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, MyDatabase.class, "user.db").build();
    }

    @Provides
    @Singleton
    UserDao provideTickerDao(MyDatabase database) {
        return database.userDao();
    }

    @Provides
    @Singleton
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    UserRepository provideTickerRepository(UserWebService webservice, UserDao userDao, Executor executor) {
        return new UserRepository(webservice, userDao, executor);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(
                GsonConverterFactory.create(gson)).baseUrl(BASE_URL).build();
        return retrofit;
    }

    @Provides
    @Singleton
    UserWebService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(UserWebService.class);
    }
}
