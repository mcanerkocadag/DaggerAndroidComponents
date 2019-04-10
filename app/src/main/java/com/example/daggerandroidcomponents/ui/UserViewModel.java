package com.example.daggerandroidcomponents.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.daggerandroidcomponents.db.Data;

import java.util.List;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    private LiveData<List<Data>> ticker;
    private UserRepository tickerRepo;

    @Inject
    public UserViewModel(UserRepository tickerRepo) {
        this.tickerRepo = tickerRepo;
    }

    public void init(String symbol) {
        if (this.ticker != null) {
            return;
        }
        ticker = tickerRepo.getUsers();
    }

    public LiveData<List<Data>> getTicker() {
        return this.ticker;
    }

}
