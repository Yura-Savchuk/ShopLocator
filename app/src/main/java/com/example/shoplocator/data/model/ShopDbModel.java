package com.example.shoplocator.data.model;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ShopDbModel {

    private final long id;
    private final String name;
    private final String imageUrl;
    private final float coordinateX;
    private final float coordinateY;
    private final long ownerId;

    public ShopDbModel(long id, String name, String imageUrl, float coordinateX, float coordinateY, long ownerId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.ownerId = ownerId;
    }

    public long getId() {
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

    public long getOwnerId() {
        return ownerId;
    }
}
