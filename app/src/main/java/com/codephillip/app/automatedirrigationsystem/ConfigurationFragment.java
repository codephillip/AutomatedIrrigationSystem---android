package com.codephillip.app.automatedirrigationsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableCursor;

import java.util.ArrayList;
import java.util.List;


public class ConfigurationFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public ConfigurationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuration, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


        CursorLoader cursorLoader = new CursorLoader(getContext(), CroptableColumns.CONTENT_URI,null,null,null,null);
        CroptableCursor cursor = new CroptableCursor(cursorLoader.loadInBackground());

        List<String> categories = new ArrayList<String>();
        if (cursor.moveToFirst()){
            do {
                categories.add(cursor.getName());
            }
            while(cursor.moveToNext());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
