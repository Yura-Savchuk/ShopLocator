package com.example.shoplocator.util.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class MapperUtil {

    public static <E, N> N transform(@Nullable E existObject, @NonNull Mapper<E, N> mapper) {
        N newObject = null;
        if (existObject != null) {
            newObject = mapper.createNewFromExist(existObject);
        }
        return newObject;
    }

    public static <E, N> ArrayList<N> transformList(@Nullable Collection<E> existObjects, @NonNull Mapper<E, N> mapper) {
        ArrayList<N> newObjects = new ArrayList<>();
        if (existObjects != null) {
            for (E existObject : existObjects) {
                N newObject = transform(existObject, mapper);
                if (newObject != null) {
                    newObjects.add(newObject);
                }
            }
        }
        return newObjects;
    }

}
