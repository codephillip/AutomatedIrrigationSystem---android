package com.codephillip.app.automatedirrigationsystem.provider.metrictable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.codephillip.app.automatedirrigationsystem.provider.AISystemProvider;
import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableColumns;

/**
 * Columns for the {@code metrictable} table.
 */
public class MetrictableColumns implements BaseColumns {
    public static final String TABLE_NAME = "metrictable";
    public static final Uri CONTENT_URI = Uri.parse(AISystemProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String WATER_VOLUME = "water_volume";

    public static final String ISIRRIGATING = "isIrrigating";

    public static final String TIME = "time";

    public static final String DATE = "date";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            WATER_VOLUME,
            ISIRRIGATING,
            TIME,
            DATE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(WATER_VOLUME) || c.contains("." + WATER_VOLUME)) return true;
            if (c.equals(ISIRRIGATING) || c.contains("." + ISIRRIGATING)) return true;
            if (c.equals(TIME) || c.contains("." + TIME)) return true;
            if (c.equals(DATE) || c.contains("." + DATE)) return true;
        }
        return false;
    }

}
