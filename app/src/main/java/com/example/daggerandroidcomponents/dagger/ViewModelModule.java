package com.example.daggerandroidcomponents.dagger;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.daggerandroidcomponents.ui.UserViewModel;
import com.example.daggerandroidcomponents.ui.UserViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule
{
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindTickerViewModel(UserViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(UserViewModelFactory factory);
}
