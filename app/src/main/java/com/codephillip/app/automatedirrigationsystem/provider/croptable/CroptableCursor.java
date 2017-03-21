package com.codephillip.app.automatedirrigationsystem.provider.croptable;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.codephillip.app.automatedirrigationsystem.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code croptable} table.
 */
public class CroptableCursor extends AbstractCursor implements CroptableModel {
    public CroptableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CroptableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(CroptableColumns.NAME);
        return res;
    }

    /**
     * Get the {@code key} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getKey() {
        Integer res = getIntegerOrNull(CroptableColumns.KEY);
        return res;
    }

    /**
     * Get the {@code optimal_water_level} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getOptimalWaterLevel() {
        Double res = getDoubleOrNull(CroptableColumns.OPTIMAL_WATER_LEVEL);
        return res;
    }

    /**
     * Get the {@code crop_type} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCropType() {
        String res = getStringOrNull(CroptableColumns.CROP_TYPE);
        return res;
    }
}
