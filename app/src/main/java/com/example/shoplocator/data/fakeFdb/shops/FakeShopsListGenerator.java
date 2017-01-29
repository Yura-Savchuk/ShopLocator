package com.example.shoplocator.data.fakeFdb.shops;

import com.example.shoplocator.data.fakeFdb.GeneratorId;
import com.example.shoplocator.data.model.ShopDbModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 29.01.17.
 */

public class FakeShopsListGenerator {

    private static final int COUNT_FOR_ONE_GENERATION = 20;
    private static final String FAKE_SHOP_NAME = "Fake Shop Name ";
    private static final String FAKE_SHOP_URL = "http://news.images.itv.com/image/file/812875/stream_img.jpg";
    private static final float MIN_COORD_X = 50.f;
    private static final float MIN_COORD_Y = 50.f;

    private static final String [] OWNER_IDS = {"1", "2", "3", "4", "5"};

    private List<ShopDbModel> shopDbModels = new LinkedList<>();
    private final Random random = new Random();

    public List<ShopDbModel> getNewList() {
        for (int i=0; i<COUNT_FOR_ONE_GENERATION; i++) {
            ShopDbModel shopDbModel = generateNewShop(i);
            shopDbModels.add(shopDbModel);
        }
        return shopDbModels;
    }

    private ShopDbModel generateNewShop(int index) {
        return new ShopDbModel(
                getNewId(),
                FAKE_SHOP_NAME + index,
                FAKE_SHOP_URL,
                getNewCoordX(),
                getNewCoordY(),
                getNewOwnerId()
        );
    }

    private String getNewId() {
        while (true) {
            String id = new GeneratorId().getGeneratedId();
            boolean idIsUnical = true;
            for (ShopDbModel item : shopDbModels) {
                if (item.getId().equals(id)) {
                    idIsUnical = false;
                }
            }
            if (idIsUnical) return id;
        }
    }

    private float getNewCoordX() {
        return MIN_COORD_X + (float) random.nextInt(100) / 100;
    }

    private float getNewCoordY() {
        return MIN_COORD_Y + (float) random.nextInt(100) / 100;
    }

    private String getNewOwnerId() {
        return OWNER_IDS[random.nextInt(OWNER_IDS.length)];
    }

}
