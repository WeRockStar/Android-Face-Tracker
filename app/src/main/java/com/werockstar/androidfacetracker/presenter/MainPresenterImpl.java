package com.werockstar.androidfacetracker.presenter;

import android.util.Log;

import com.werockstar.androidfacetracker.util.BitmapUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainPresenterImpl implements MainPresenter {

    private CompositeSubscription subscription = new CompositeSubscription();
    private MainPresenter.View view;

    public MainPresenterImpl(View view) {
        this.view = view;
    }

    @Override
    public void convertBitmapToStream(byte[] data) {
        view.displayLoading();
        subscription.add(Observable.just(BitmapUtil.handleBitmap(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> view.dismissLoading())
                .subscribe(photoByte -> view.takePhotoSuccess(photoByte),
                        throwable -> {
                            view.takePhotoError();
                        }));
    }

    @Override
    public void onStop() {
        subscription.clear();
    }
}
