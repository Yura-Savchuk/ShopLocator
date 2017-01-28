package com.example.shoplocator.data.firebaseDb.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 28.01.17.
 */

@IgnoreExtraProperties
public class ShopFdbModel {

    public String id;
    public String name;
    public String image_url;
    public String owner_id;
    public CoordinateFdbModel coordinate;

    public ShopFdbModel() {
    }

    public ShopFdbModel(String id, String name, String imageUrl, String ownerId, CoordinateFdbModel coordinate) {
        this.id = id;
        this.name = name;
        this.image_url = imageUrl;
        this.owner_id = ownerId;
        this.coordinate = coordinate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("image_url", image_url);
        map.put("owner_id", owner_id);
        map.put("coordinate", coordinate.toMap());
        return map;
    }

}
