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
import com.codephillip.app.automatedirrigationsystem.R;

/**
 * Created by codephillip on 20/03/17.
 */

public class MetricAdapter extends RecyclerView.Adapter<MetricAdapter.ViewHolder> {
    Cursor dataCursor;
    private Context context;

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
        Log.d("Driver#", "onBindViewHolder: "+position);
//        try{
//            dataCursor.moveToPosition(position);
//            String driverName = dataCursor.getString(dataCursor.getColumnIndex(WithinhourColumns.DRIVERNAME));
//            String carName = dataCursor.getString(dataCursor.getColumnIndex(WithinhourColumns.CARNAME));
//            String driverImage = dataCursor.getString(dataCursor.getColumnIndex(WithinhourColumns.DRIVERIMAGE));
//            String carImage = dataCursor.getString(dataCursor.getColumnIndex(WithinhourColumns.CARIMAGE));
//            String time = dataCursor.getString(dataCursor.getColumnIndex(WithinhourColumns.TIME));
//
//            holder.nameView.setText(driverName);
//            holder.timeView.setText(time);
//            Utility.picassoLoader(context, holder.imageView, driverImage);
////            Utility.picassoCircleLoaderNoBorder(context, holder.imageView, driverImage);
////            Utility.picassoCircleLoaderBorder(context, holder.imageView, imageUrl);
//        } catch (Exception e){
//            e.printStackTrace();
//            holder.nameView.setText("Crystal");
//            holder.timeView.setText("07:00");
//            Utility.picassoCircleLoaderNoBorder(context, holder.imageView, R.drawable.profilepic);
//            Utility.picassoCircleLoaderBorder(context, holder.imageView, R.drawable.profilepic);
//        }

        //todo extract boolean from table
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(140)  // width in px
                .height(140) // height in px
                .endConfig()
//                .buildRound(textData.substring(0,1), color1);
                .buildRound("T", color1);
        holder.imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        // any number other than zero will cause a bug
//        return (dataCursor == null) ? 0 : dataCursor.getCount();
        return 10;
    }
}