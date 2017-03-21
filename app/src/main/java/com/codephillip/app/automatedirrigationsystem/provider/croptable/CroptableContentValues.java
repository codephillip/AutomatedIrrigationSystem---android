package com.codephillip.app.automatedirrigationsystem.provider.croptable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.automatedirrigationsystem.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code croptable} table.
 */
public class CroptableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CroptableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CroptableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable CroptableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CroptableContentValues putName(@Nullable String value) {
        mContentValues.put(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableContentValues putNameNull() {
        mContentValues.putNull(CroptableColumns.NAME);
        return this;
    }

    public CroptableContentValues putKey(@Nullable Integer value) {
        mContentValues.put(CroptableColumns.KEY, value);
        return this;
    }

    public CroptableContentValues putKeyNull() {
        mContentValues.putNull(CroptableColumns.KEY);
        return this;
    }

    public CroptableContentValues putOptimalWaterLevel(@Nullable Double value) {
        mContentValues.put(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableContentValues putOptimalWaterLevelNull() {
        mContentValues.putNull(CroptableColumns.OPTIMAL_WATER_LEVEL);
        return this;
    }

    public CroptableContentValues putCropType(@Nullable String value) {
        mContentValues.put(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableContentValues putCropTypeNull() {
        mContentValues.putNull(CroptableColumns.CROP_TYPE);
        return this;
    }
}
