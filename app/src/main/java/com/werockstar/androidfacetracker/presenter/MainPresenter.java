package com.werockstar.androidfacetracker.presenter;

public interface MainPresenter {
    void convertBitmapToStream(byte[] data);

    void onStop();

    interface View {
        void takePhotoSuccess(byte[] data);

        void takePhotoError();

        void displayLoading();

        void dismissLoading();
    }
}
