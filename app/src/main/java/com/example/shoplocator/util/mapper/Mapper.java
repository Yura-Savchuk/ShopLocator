package com.example.shoplocator.util.mapper;

import android.support.annotation.NonNull;

public interface Mapper<E, N> {

    N createNewFromExist(@NonNull E exist);

}
