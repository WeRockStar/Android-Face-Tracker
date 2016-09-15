package com.werockstar.androidfacetracker;


import android.widget.Toast;

import com.google.android.cameraview.CameraView;

public class CameraManager {

    public CameraView.Callback callback = new CameraView.Callback() {
        @Override
        public void onCameraOpened(CameraView cameraView) {
            super.onCameraOpened(cameraView);
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            super.onCameraClosed(cameraView);
        }

        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            Toast.makeText(cameraView.getContext(), R.string.take_photo, Toast.LENGTH_LONG).show();
        }
    };
}
