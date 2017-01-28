package com.example.shoplocator.data.firebaseDb.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

@IgnoreExtraProperties
public class CoordinateFdbModel {

    public double x;
    public double y;

    public CoordinateFdbModel() {
    }

    public CoordinateFdbModel(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        return map;
    }

}
