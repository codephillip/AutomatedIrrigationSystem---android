package com.codephillip.app.automatedirrigationsystem;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.users.User;

/**
 * Created by codephillip on 20/03/17.
 */

public class Utils {

    private static final Utils ourInstance = new Utils();

    static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    private static final String TAG = Utils.class.getSimpleName();
    public static User user;

    public static boolean isConnectedToInternet(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
