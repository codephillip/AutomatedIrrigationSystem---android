package com.codephillip.app.automatedirrigationsystem.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.codephillip.app.automatedirrigationsystem.ColourQueue;
import com.codephillip.app.automatedirrigationsystem.R;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableCursor;

/**
 * Created by codephillip on 20/03/17.
 */

public class MetricAdapter extends RecyclerView.Adapter<MetricAdapter.ViewHolder> {
    Cursor dataCursor;
    private Context context;
    private ColourQueue colourQueue;

    public MetricAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        private TextView waterVolumeView;
        private TextView timeView;
        private TextView dateView;
        private ImageView imageView;
        private ViewHolder(View v) {
            super(v);
            timeView = (TextView) v.findViewById(R.id.timeView);
            dateView = (TextView) v.findViewById(R.id.dateView);
            waterVolumeView = (TextView) v.findViewById(R.id.waterVolumeView);
            imageView = (ImageView) v.findViewById(R.id.iSIrrigatingImageView);
        }
    }

    public MetricAdapter(Context mContext, Cursor cursor) {
        dataCursor = cursor;
        context = mContext;
        colourQueue = new ColourQueue();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.metrics_row, parent, false);
        return new ViewHolder(cardview);
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
        Log.d("Metric#", "onBindViewHolder: "+position);
        try{
            dataCursor.moveToPosition(position);
            MetrictableCursor metric = new MetrictableCursor(dataCursor);
            holder.waterVolumeView.setText(String.valueOf(metric.getWaterVolume()));
            holder.dateView.setText(metric.getDate());
            holder.timeView.setText(metric.getTime());

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getColor(colourQueue.getCount());
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(140)  // width in px
                    .height(140) // height in px
                    .endConfig()
                    .buildRound(metric.getIsirrigating().toString().substring(0,1), color);
            holder.imageView.setImageDrawable(drawable);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // any number other than zero will cause a bug
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }
}