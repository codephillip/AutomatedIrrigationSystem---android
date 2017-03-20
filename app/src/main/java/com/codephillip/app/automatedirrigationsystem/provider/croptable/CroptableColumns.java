package com.codephillip.app.automatedirrigationsystem.provider.croptable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.codephillip.app.automatedirrigationsystem.provider.AISystemProvider;

/**
 * Columns for the {@code croptable} table.
 */
public class CroptableColumns implements BaseColumns {
    public static final String TABLE_NAME = "croptable";
    public static final Uri CONTENT_URI = Uri.parse(AISystemProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String NAME = "name";

    public static final String OPTIMAL_WATER_LEVEL = "optimal_water_level";

    public static final String CROP_TYPE = "crop_type";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            OPTIMAL_WATER_LEVEL,
            CROP_TYPE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(OPTIMAL_WATER_LEVEL) || c.contains("." + OPTIMAL_WATER_LEVEL)) return true;
            if (c.equals(CROP_TYPE) || c.contains("." + CROP_TYPE)) return true;
        }
        return false;
    }

}
