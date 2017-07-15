package com.codephillip.app.automatedirrigationsystem.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.codephillip.app.automatedirrigationsystem.R;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.crops.Crop;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.crops.Crops;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metric;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metrics;
import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableContentValues;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableContentValues;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiClient;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();

    // Interval at which to sync with the server, in seconds.
    // 60 seconds (1 minute) * 60 * 2 = 2 hour
    private static int SYNC_INTERVAL = 60 * 60 * 24;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int NOTIFICATION_ID = 3004;
    private static ApiInterface apiInterface;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.d(TAG, "ONPERFORMSYNC");

        apiInterface = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

        deleteTables();

        try {
            loadMetrics();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadCrops();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMetrics() {
        Call<Metrics> call = apiInterface.allMetrics();
        call.enqueue(new Callback<Metrics>() {
            @Override
            public void onResponse(Call<Metrics> call, retrofit2.Response<Metrics> response) {
                Log.d("RETROFIT#", "onResponse: " + response.headers());
                Metrics metrics = response.body();
                saveMetrics(metrics);
            }

            @Override
            public void onFailure(Call<Metrics> call, Throwable t) {
                Log.d("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }

    private void saveMetrics(Metrics metrics) {
        Log.d("INSERT: ", "starting");
        if (metrics == null)
            throw new NullPointerException("Metrics not found");
        List<Metric> metricList = metrics.getMetrics();
        for (Metric metric : metricList) {
            Log.d(TAG, "saveMetric: " + metric.getId() + metric.getWaterVolume() + metric.getIsIrrigating());
            MetrictableContentValues values = new MetrictableContentValues();
            values.putWaterVolume(metric.getWaterVolume());
            values.putIsirrigating(metric.getIsIrrigating());
            values.putDate(metric.getTimeStamp().substring(0, 10));
            values.putTime(metric.getTimeStamp().substring(11, 16));
            final Uri uri = values.insert(getContext().getContentResolver());
            Log.d("INSERT: ", "inserting" + uri.toString());
        }
    }

    private void loadCrops() {
        Log.d("CROP#", "load crops");

        Call<Crops> call = apiInterface.allCrops();
        call.enqueue(new Callback<Crops>() {
            @Override
            public void onResponse(Call<Crops> call, retrofit2.Response<Crops> response) {
                Log.d("RETROFIT#", "onResponse: " + response.headers());
                Crops crops = response.body();
                saveCrops(crops);
            }

            @Override
            public void onFailure(Call<Crops> call, Throwable t) {
                Log.d("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }

    private void saveCrops(Crops crops) {
        Log.d("INSERT: CROP", "starting");
        double deleted = getContext().getContentResolver().delete(CroptableColumns.CONTENT_URI, null, null);
        Log.d(TAG, "delete crops: " + deleted);
        if (crops == null)
            throw new NullPointerException("Crops not found");
        List<Crop> cropList = crops.getCrops();
        for (Crop crop : cropList) {
            Log.d(TAG, "saveCrop: " + crop.getId() + crop.getName() + crop.getCropType() + crop.getOptimalWaterLevel());
            CroptableContentValues values = new CroptableContentValues();
            values.putKey(crop.getId());
            values.putName(crop.getName());
            values.putCropType(crop.getCropType());
            values.putOptimalWaterLevel(crop.getOptimalWaterLevel());
            final Uri uri = values.insert(getContext().getContentResolver());
            Log.d("INSERT: ", "inserting" + uri.toString());
        }
    }

    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        Log.d("SYNC_IMMEDIATELY", "syncImmediately: STARTED");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        SyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        Log.d("SYNCADAPTER", "initializeSyncAdapter: STARTED");
        getSyncAccount(context);
    }

//    private void notifyWeather() {
//        Context context = getContext();
//        //checking the last update and notify if it' the first of the day
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String lastNotificationKey = context.getString(R.string.pref_last_notification2);
//        long lastSync = prefs.getLong(lastNotificationKey, 0);
//        boolean displayNotifications = prefs.getBoolean("notifications_new_message", true);
//
//        if (displayNotifications) {
//
//            if (System.currentTimeMillis() - lastSync >= DAY_IN_MILLIS) {
//                Cursor cursor = context.getContentResolver().query(TodayweatherColumns.CONTENT_URI, null, null, null, null);
//                if (cursor.moveToFirst()) {
//                    int weatherId = cursor.getInt(cursor.getColumnIndex(TodayweatherColumns.WEATHER_ID));
//                    double high = cursor.getDouble(cursor.getColumnIndex(TodayweatherColumns.MAX_TEMP));
//                    double low = cursor.getDouble(cursor.getColumnIndex(TodayweatherColumns.MIN_TEMP));
//                    String desc = cursor.getString(cursor.getColumnIndex(TodayweatherColumns.MAIN));
//
//                    int iconId = Utility.getArtResourceForWeatherCondition(weatherId);
//                    String title = context.getString(R.string.app_name);
//
//                    // Define the text of the forecast.
//                    String contentText = String.format(context.getString(R.string.format_notification),
//                            desc,
//                            Utility.formatTemperature(context, high),
//                            Utility.formatTemperature(context, low));
//
//                    // NotificationCompatBuilder is a very convenient way to build backward-compatible
//                    // notifications.  Just throw in some data.
//                    NotificationCompat.Builder mBuilder =
//                            new NotificationCompat.Builder(getContext())
//                                    .setSmallIcon(iconId)
//                                    .setContentTitle(title)
//                                    .setContentText(contentText);
//
//                    Intent resultIntent = new Intent(context, MainActivity.class);
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//                    stackBuilder.addNextIntent(resultIntent);
//                    PendingIntent resultPendingIntent =
//                            stackBuilder.getPendingIntent(
//                                    0,
//                                    PendingIntent.FLAG_UPDATE_CURRENT
//                            );
//                    mBuilder.setContentIntent(resultPendingIntent);
//
//                    NotificationManager mNotificationManager =
//                            (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    mNotificationManager.notify(WEATHER_NOTIFICATION_ID, mBuilder.build());
//                    Uri notification = Uri.parse(prefs.getString("notifications_new_message_ringtone", "content://settings/system/notification_sound"));
//                    Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
//                    r.play();
//
//                    cursor.close();
//
//                    if (prefs.getBoolean("notifications_new_message_vibrate", true)) {
//                        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
//                        // Vibrate for 500 milliseconds
//                        v.vibrate(500);
//                    }
//
//                    //refreshing last sync
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.putLong(lastNotificationKey, System.currentTimeMillis());
//                    editor.apply();
//                }
//            }
//        }
//    }

    private void deleteTables() {
        long deleted;
        deleted = getContext().getContentResolver().delete(MetrictableColumns.CONTENT_URI, null, null);
        Log.d("CONTENT_QUERY_deleted#", String.valueOf(deleted));
        deleted = getContext().getContentResolver().delete(CroptableColumns.CONTENT_URI, null, null);
        Log.d("CONTENT_QUERY_deleted#", String.valueOf(deleted));
    }
}

