package com.example.shoplocator.util.rx.validation;

import java.util.Collection;

import rx.Single;
import rx.functions.Func1;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 31.01.17.
 */

public class RxValidation {

    public <T, C extends Collection<T>> Single.Transformer<C, C> collectionNotEmpty(Throwable errorIfEmpty) {
        return listSingle -> listSingle.flatMap(new Func1<C, Single<C>>() {
            @Override
            public Single<C> call(C shopDbModels) {
                if (shopDbModels == null || shopDbModels.isEmpty()) {
                    return Single.error(errorIfEmpty);
                }
                return Single.just(shopDbModels);
            }
        });
    }

}
