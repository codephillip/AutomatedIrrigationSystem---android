package com.codephillip.app.automatedirrigationsystem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableCursor;
import com.codephillip.app.automatedirrigationsystem.services.UserService;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class ConfigurationFragment extends Fragment implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    Map<String, Integer> cropsMap = new Hashtable<>();

    TextView nameView, addressView, phoneView;
    Spinner spinner;

    public ConfigurationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuration, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        nameView = (TextView) rootView.findViewById(R.id.name);
        addressView = (TextView) rootView.findViewById(R.id.address);
        phoneView = (TextView) rootView.findViewById(R.id.phone);
        Utils.getInstance();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(3, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), CroptableColumns.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        CroptableCursor cursor = new CroptableCursor(data);
        List<String> categories = new ArrayList<String>();
        if (cursor.moveToFirst()){
            do {
                categories.add(cursor.getName());
                cropsMap.put(cursor.getName(), cursor.getKey());
            }
            while(cursor.moveToNext());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        //todo display current crop in the spinner

        try {
            nameView.setText("Name: " + Utils.user.getName());
            phoneView.setText("Phone Number: " + Utils.user.getPhoneNumber());
            addressView.setText("Address: " + Utils.user.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (isConnectedToInternet())
            getActivity().startService(new Intent(getContext(), UserService.class).putExtra("crop_id", cropsMap.get(item)));
        else
            Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
