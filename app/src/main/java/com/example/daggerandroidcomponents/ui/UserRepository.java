package com.example.daggerandroidcomponents.ui;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.daggerandroidcomponents.api.UserWebService;
import com.example.daggerandroidcomponents.db.Data;
import com.example.daggerandroidcomponents.db.MyPojo;
import com.example.daggerandroidcomponents.db.UserDao;
import com.example.daggerandroidcomponents.db.UserDetail;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserRepository {
    private final UserWebService webservice;
    private final UserDao userDao;
    private final Executor executor;

    @Inject
    public UserRepository(UserWebService webService, UserDao userDao, Executor executor) {
        this.webservice = webService;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<Data> getTicker(String symbol) {
        refreshTicker(symbol); // try to refresh data if possible from Bitfinex Api
        return userDao.load(Integer.valueOf(symbol)); // return a LiveData directly from the database.
    }

    private void refreshTicker(final String symbol) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Check if ticker was fetched recently
                Data tickerExists = userDao.hasTicker(Integer.valueOf(symbol));
                // If ticker have to be updated
                if (tickerExists == null) {
                    webservice.getUser(Integer.valueOf(symbol)).enqueue(new Callback<UserDetail>() {
                        @Override
                        public void onResponse(Call<UserDetail> call, final Response<UserDetail> response) {
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    UserDetail userDetail = response.body();
                                    //userDetail.setTimestamp((new Date()).getTime());
                                    //userDetail.setSymbol(symbol);
                                    userDao.save(userDetail.getData());
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<UserDetail> call, Throwable t) {
                            Log.e("_MK", t.getMessage(), t);
                        }
                    });
                }
            }
        });
    }

    public LiveData<List<Data>> getUsers() {
        refreshUsers(); // try to refresh data if possible from Bitfinex Api
        return userDao.loadAllUser(); // return a LiveData directly from the database.
    }

    public void refreshUsers() {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                webservice.getUsers().enqueue(new Callback<MyPojo>() {
                    @Override
                    public void onResponse(Call<MyPojo> call, final Response<MyPojo> response) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                MyPojo userDetail = response.body();
                                //userDetail.setTimestamp((new Date()).getTime());
                                //userDetail.setSymbol(symbol);
                                for (Data d : userDetail.getData()                               ) {

                                    if(d == null)
                                        continue;

                                    userDao.save(d);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<MyPojo> call, Throwable t) {
                        Log.e("_MK", t.getMessage(), t);
                    }
                });
            }
        });
    }

}
