package com.codephillip.app.automatedirrigationsystem.provider.croptable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.codephillip.app.automatedirrigationsystem.provider.base.AbstractSelection;

/**
 * Selection for the {@code croptable} table.
 */
public class CroptableSelection extends AbstractSelection<CroptableSelection> {
    @Override
    protected Uri baseUri() {
        return CroptableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CroptableCursor} object, which is positioned before the first entry, or null.
     */
    public CroptableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CroptableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public CroptableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CroptableCursor} object, which is positioned before the first entry, or null.
     */
    public CroptableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CroptableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public CroptableCursor query(Context context) {
        return query(context, null);
    }


    public CroptableSelection id(long... value) {
        addEquals("croptable." + CroptableColumns._ID, toObjectArray(value));
        return this;
    }

    public CroptableSelection idNot(long... value) {
        addNotEquals("croptable." + CroptableColumns._ID, toObjectArray(value));
        return this;
    }

    public CroptableSelection orderById(boolean desc) {
        orderBy("croptable." + CroptableColumns._ID, desc);
        return this;
    }

    public CroptableSelection orderById() {
        return orderById(false);
    }

    public CroptableSelection name(String... value) {
        addEquals(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableSelection nameNot(String... value) {
        addNotEquals(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableSelection nameLike(String... value) {
        addLike(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableSelection nameContains(String... value) {
        addContains(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableSelection nameStartsWith(String... value) {
        addStartsWith(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableSelection nameEndsWith(String... value) {
        addEndsWith(CroptableColumns.NAME, value);
        return this;
    }

    public CroptableSelection orderByName(boolean desc) {
        orderBy(CroptableColumns.NAME, desc);
        return this;
    }

    public CroptableSelection orderByName() {
        orderBy(CroptableColumns.NAME, false);
        return this;
    }

    public CroptableSelection optimalWaterLevel(Double... value) {
        addEquals(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableSelection optimalWaterLevelNot(Double... value) {
        addNotEquals(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableSelection optimalWaterLevelGt(double value) {
        addGreaterThan(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableSelection optimalWaterLevelGtEq(double value) {
        addGreaterThanOrEquals(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableSelection optimalWaterLevelLt(double value) {
        addLessThan(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableSelection optimalWaterLevelLtEq(double value) {
        addLessThanOrEquals(CroptableColumns.OPTIMAL_WATER_LEVEL, value);
        return this;
    }

    public CroptableSelection orderByOptimalWaterLevel(boolean desc) {
        orderBy(CroptableColumns.OPTIMAL_WATER_LEVEL, desc);
        return this;
    }

    public CroptableSelection orderByOptimalWaterLevel() {
        orderBy(CroptableColumns.OPTIMAL_WATER_LEVEL, false);
        return this;
    }

    public CroptableSelection cropType(String... value) {
        addEquals(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableSelection cropTypeNot(String... value) {
        addNotEquals(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableSelection cropTypeLike(String... value) {
        addLike(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableSelection cropTypeContains(String... value) {
        addContains(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableSelection cropTypeStartsWith(String... value) {
        addStartsWith(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableSelection cropTypeEndsWith(String... value) {
        addEndsWith(CroptableColumns.CROP_TYPE, value);
        return this;
    }

    public CroptableSelection orderByCropType(boolean desc) {
        orderBy(CroptableColumns.CROP_TYPE, desc);
        return this;
    }

    public CroptableSelection orderByCropType() {
        orderBy(CroptableColumns.CROP_TYPE, false);
        return this;
    }
}
