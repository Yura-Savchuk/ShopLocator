package com.example.shoplocator.util.rx.schedulers;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by macbookpro on 13.11.16.
 */

public final class RxSchedulersTest extends RxSchedulersAbs {
    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.immediate();
    }
}
