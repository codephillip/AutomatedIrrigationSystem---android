package com.codephillip.app.automatedirrigationsystem.provider.metrictable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.codephillip.app.automatedirrigationsystem.provider.base.AbstractSelection;

/**
 * Selection for the {@code metrictable} table.
 */
public class MetrictableSelection extends AbstractSelection<MetrictableSelection> {
    @Override
    protected Uri baseUri() {
        return MetrictableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MetrictableCursor} object, which is positioned before the first entry, or null.
     */
    public MetrictableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MetrictableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MetrictableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MetrictableCursor} object, which is positioned before the first entry, or null.
     */
    public MetrictableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MetrictableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MetrictableCursor query(Context context) {
        return query(context, null);
    }


    public MetrictableSelection id(long... value) {
        addEquals("metrictable." + MetrictableColumns._ID, toObjectArray(value));
        return this;
    }

    public MetrictableSelection idNot(long... value) {
        addNotEquals("metrictable." + MetrictableColumns._ID, toObjectArray(value));
        return this;
    }

    public MetrictableSelection orderById(boolean desc) {
        orderBy("metrictable." + MetrictableColumns._ID, desc);
        return this;
    }

    public MetrictableSelection orderById() {
        return orderById(false);
    }

    public MetrictableSelection waterVolume(Double... value) {
        addEquals(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableSelection waterVolumeNot(Double... value) {
        addNotEquals(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableSelection waterVolumeGt(double value) {
        addGreaterThan(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableSelection waterVolumeGtEq(double value) {
        addGreaterThanOrEquals(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableSelection waterVolumeLt(double value) {
        addLessThan(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableSelection waterVolumeLtEq(double value) {
        addLessThanOrEquals(MetrictableColumns.WATER_VOLUME, value);
        return this;
    }

    public MetrictableSelection orderByWaterVolume(boolean desc) {
        orderBy(MetrictableColumns.WATER_VOLUME, desc);
        return this;
    }

    public MetrictableSelection orderByWaterVolume() {
        orderBy(MetrictableColumns.WATER_VOLUME, false);
        return this;
    }

    public MetrictableSelection isirrigating(Boolean value) {
        addEquals(MetrictableColumns.ISIRRIGATING, toObjectArray(value));
        return this;
    }

    public MetrictableSelection orderByIsirrigating(boolean desc) {
        orderBy(MetrictableColumns.ISIRRIGATING, desc);
        return this;
    }

    public MetrictableSelection orderByIsirrigating() {
        orderBy(MetrictableColumns.ISIRRIGATING, false);
        return this;
    }

    public MetrictableSelection time(String... value) {
        addEquals(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableSelection timeNot(String... value) {
        addNotEquals(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableSelection timeLike(String... value) {
        addLike(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableSelection timeContains(String... value) {
        addContains(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableSelection timeStartsWith(String... value) {
        addStartsWith(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableSelection timeEndsWith(String... value) {
        addEndsWith(MetrictableColumns.TIME, value);
        return this;
    }

    public MetrictableSelection orderByTime(boolean desc) {
        orderBy(MetrictableColumns.TIME, desc);
        return this;
    }

    public MetrictableSelection orderByTime() {
        orderBy(MetrictableColumns.TIME, false);
        return this;
    }

    public MetrictableSelection date(String... value) {
        addEquals(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableSelection dateNot(String... value) {
        addNotEquals(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableSelection dateLike(String... value) {
        addLike(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableSelection dateContains(String... value) {
        addContains(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableSelection dateStartsWith(String... value) {
        addStartsWith(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableSelection dateEndsWith(String... value) {
        addEndsWith(MetrictableColumns.DATE, value);
        return this;
    }

    public MetrictableSelection orderByDate(boolean desc) {
        orderBy(MetrictableColumns.DATE, desc);
        return this;
    }

    public MetrictableSelection orderByDate() {
        orderBy(MetrictableColumns.DATE, false);
        return this;
    }
}
