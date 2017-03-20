package com.codephillip.app.automatedirrigationsystem.provider.metrictable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.automatedirrigationsystem.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code metrictable} table.
 */
public class MetrictableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MetrictableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MetrictableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MetrictableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MetrictableContentValues putWaterVolume(@Nullable Double value) {
        mContentValues.put(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableContentValues putWaterVolumeNull() {
        mContentValues.putNull(MetrictableColumns.WATER_VOLUME);
        return this;
    }

    public MetrictableContentValues putIsirrigating(@Nullable Boolean value) {
        mContentValues.put(MetrictableColumns.ISIRRIGATING, value);
        return this;
    }

    public MetrictableContentValues putIsirrigatingNull() {
        mContentValues.putNull(MetrictableColumns.ISIRRIGATING);
        return this;
    }

    public MetrictableContentValues putTime(@Nullable String value) {
        mContentValues.put(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableContentValues putTimeNull() {
        mContentValues.putNull(MetrictableColumns.TIME);
        return this;
    }

    public MetrictableContentValues putDate(@Nullable String value) {
        mContentValues.put(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableContentValues putDateNull() {
        mContentValues.putNull(MetrictableColumns.DATE);
        return this;
    }
}
