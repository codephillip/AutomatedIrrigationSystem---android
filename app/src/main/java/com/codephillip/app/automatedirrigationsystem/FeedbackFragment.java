package com.codephillip.app.automatedirrigationsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codephillip.app.automatedirrigationsystem.adapters.AboutAdapter;


public class FeedbackFragment extends Fragment{

    public FeedbackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedack, container, false);

        return rootView;
    }
}
