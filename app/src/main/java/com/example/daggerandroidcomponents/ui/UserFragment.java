package com.example.daggerandroidcomponents.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daggerandroidcomponents.R;
import com.example.daggerandroidcomponents.db.Data;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class UserFragment extends Fragment {

    public static final String UID_KEY = "uid";
    private UserViewModel viewModel;
    private TextView textView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public UserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        textView = view.findViewById(R.id.textView);
        textView.setText("Loading...");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureDagger() {

        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
        String symbol = getArguments().getString(UID_KEY);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        viewModel.init(symbol);
        viewModel.getTicker().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(@Nullable List<Data> data) {

                updateUI(data);
            }
        });
    }

    private void updateUI(List<Data> dataList) {

        if (dataList == null)
            return;

        StringBuilder builder = new StringBuilder();
        for (Data data : dataList) {
            if (data == null)
                continue;

            builder.append("İsim: ").append(data.getFirst_name()).append("\n\n");
        }

        textView.setText(builder.toString());
    }
}
