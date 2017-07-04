package com.codephillip.app.automatedirrigationsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codephillip.app.automatedirrigationsystem.adapters.MetricAdapter;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableCursor;


public class SystemStatusFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    MetricAdapter adapter;
    RecyclerView recyclerView;
    TextView irrigationgView;

    public SystemStatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system_status, container, false);
        irrigationgView = (TextView) rootView.findViewById(R.id.irrigating);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LineChartActivity.class));
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.status_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MetricAdapter(getContext(), null);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MetrictableColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        populateIrrigationViewData(data);
    }

    private void populateIrrigationViewData(Cursor data) {
        data.moveToFirst();
        MetrictableCursor metric = new MetrictableCursor(data);
//        irrigationgView.setText("IRRIGATION: " + metric.getIsirrigating());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
