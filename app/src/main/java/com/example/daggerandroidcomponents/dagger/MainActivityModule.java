package com.example.daggerandroidcomponents.dagger;

import com.example.daggerandroidcomponents.ui.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract UserFragment contributeTickerFragment();
}
