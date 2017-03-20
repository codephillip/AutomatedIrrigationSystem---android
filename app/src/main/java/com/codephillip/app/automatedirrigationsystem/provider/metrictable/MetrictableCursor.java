package com.codephillip.app.automatedirrigationsystem.provider.metrictable;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.automatedirrigationsystem.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code metrictable} table.
 */
public class MetrictableCursor extends AbstractCursor implements MetrictableModel {
    public MetrictableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MetrictableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code water_volume} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getWaterVolume() {
        Double res = getDoubleOrNull(MetrictableColumns.WATER_VOLUME);
        return res;
    }

    /**
     * Get the {@code isirrigating} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getIsirrigating() {
        Boolean res = getBooleanOrNull(MetrictableColumns.ISIRRIGATING);
        return res;
    }

    /**
     * Get the {@code time} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTime() {
        String res = getStringOrNull(MetrictableColumns.TIME);
        return res;
    }

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDate() {
        String res = getStringOrNull(MetrictableColumns.DATE);
        return res;
    }
}
