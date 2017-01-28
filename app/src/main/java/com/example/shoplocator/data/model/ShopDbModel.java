package com.example.shoplocator.data.model;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopDbModel {

    private final String id;
    private final String name;
    private final String imageUrl;
    private final float coordinateX;
    private final float coordinateY;
    private final String ownerId;

    public ShopDbModel(String id, String name, String imageUrl, float coordinateX, float coordinateY, String ownerId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
