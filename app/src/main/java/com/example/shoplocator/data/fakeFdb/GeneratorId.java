package com.example.shoplocator.data.fakeFdb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class GeneratorId {

    @Nullable
    private String generatedId;

    @NonNull
    public String getGeneratedId() {
        if (generatedId == null) {
            generatedId = generateId();
        }
        return generatedId;
    }

    @NonNull
    private String generateId() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis())
                + String.valueOf(new Random().nextLong());
    }
}
