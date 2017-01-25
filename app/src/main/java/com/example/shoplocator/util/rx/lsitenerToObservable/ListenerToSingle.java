package com.example.shoplocator.util.rx.lsitenerToObservable;

import rx.Single;
import rx.SingleSubscriber;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 25.01.17.
 */

public class ListenerToSingle<Value> {

    private SingleSubscriber<? super Value> subscriber;

    public void emitNextValue(Value value) {
        if (subscriber != null &&!subscriber.isUnsubscribed()) {
            subscriber.onSuccess(value);
            subscriber.unsubscribe();
        }
    }

    public Single<Value> getSingle() {
        return Single.create(singleSubscriber -> ListenerToSingle.this.subscriber = singleSubscriber);
    }

}
