package com.codephillip.app.automatedirrigationsystem.provider.croptable;

import com.codephillip.app.automatedirrigationsystem.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code croptable} table.
 */
public interface CroptableModel extends BaseModel {

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code key} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getKey();

    /**
     * Get the {@code optimal_water_level} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getOptimalWaterLevel();

    /**
     * Get the {@code crop_type} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCropType();
}
