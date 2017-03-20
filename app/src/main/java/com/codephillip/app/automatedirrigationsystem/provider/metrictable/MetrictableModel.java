package com.codephillip.app.automatedirrigationsystem.provider.metrictable;

import com.codephillip.app.automatedirrigationsystem.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code metrictable} table.
 */
public interface MetrictableModel extends BaseModel {

    /**
     * Get the {@code water_volume} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getWaterVolume();

    /**
     * Get the {@code isirrigating} value.
     * Can be {@code null}.
     */
    @Nullable
    Boolean getIsirrigating();

    /**
     * Get the {@code time} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTime();

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDate();
}
