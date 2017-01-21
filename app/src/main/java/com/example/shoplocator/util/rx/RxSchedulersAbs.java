package com.example.shoplocator.util.rx;

import rx.Observable;
import rx.Scheduler;
import rx.Single;

/**
 * Created by macbookpro on 13.11.16.
 */

public abstract class RxSchedulersAbs {

    abstract public Scheduler getMainThreadScheduler();
    abstract public Scheduler getIOScheduler();
    abstract public Scheduler getComputationScheduler();

    public <T> Observable.Transformer<T, T> getIOToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Single.Transformer<T, T> getIOToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Observable.Transformer<T, T> getComputationToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Single.Transformer<T, T> getComputationToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> Single.Transformer<T, T> getIOToIOTransformerSingle() {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getIOScheduler());
    }

    public  <T> Observable.Transformer<T, T> getIOObservable() {
        return tObservable -> tObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getIOScheduler());
    }

    public  <T> Single.Transformer<T, T> getIOSingle() {
        return tSingle -> tSingle
                .subscribeOn(getIOScheduler())
                .observeOn(getIOScheduler());
    }
}
