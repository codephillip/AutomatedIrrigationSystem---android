package com.codephillip.app.automatedirrigationsystem.adapters;

/**
 * Created by codephillip on 17/03/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codephillip.app.automatedirrigationsystem.R;

/**
 * Created by codephillip on 6/1/16.
 */
public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {
    private static final String TAG = "About Adapter#";
    Cursor dataCursor;
    private Context context;
    private String[] members = {
            "Kigenyi Phillip",
            "Nasasira Gloria",
            "Gulam Ahmed",
            "Onzia Sharon"
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.profile_text);
        }
    }

    public AboutAdapter(Context mContext, Cursor cursor) {
        dataCursor = cursor;
        context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_row, parent, false);
        return new ViewHolder(view);
    }

    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: insert " + position);
        switch (position){
            case 0:
                holder.textView.setText(members[position]);
                break;
            case 1:
                holder.textView.setText(members[position]);
                break;
            case 2:
                holder.textView.setText(members[position]);
                break;
            case 3:
                holder.textView.setText(members[position]);
                break;
            default:
                throw new UnsupportedOperationException("Wrong positon");
        }
    }

    @Override
    public int getItemCount() {
        return members.length;
    }
}
