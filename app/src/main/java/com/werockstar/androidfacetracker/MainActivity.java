package com.werockstar.androidfacetracker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.cameraview.CameraView;
import com.werockstar.androidfacetracker.presenter.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainPresenterImpl.View {

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private CameraView mCameraView;
    private Button btnTake;
    public static final String EXTRA_PHOTO = "EXTRA_PHOTO";
    private ProgressDialog progress;
    private MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this);
        progress = new ProgressDialog(MainActivity.this);
        mCameraView = (CameraView) findViewById(R.id.cameraview);
        btnTake = (Button) findViewById(R.id.btnTake);

        btnTake.setOnClickListener(v -> mCameraView.takePicture());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasGrantCameraPermission()) {
            try {
                mCameraView.start();
                mCameraView.addCallback(callback);
            } catch (Exception e) {
                btnTake.setEnabled(false);
                Log.d(MainActivity.class.getSimpleName(), e.getMessage());
            }
        } else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mCameraView.stop();
    }

    private boolean hasGrantCameraPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraView.start();
                }
                btnTake.setEnabled(hasGrantCameraPermission());
                break;
        }
    }

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
        public void onPictureTaken(final CameraView cameraView, final byte[] data) {
            presenter.convertBitmapToStream(data);
        }
    };


    private void goOnResult(byte[] bytes) {
        Intent intentToResultActivity = new Intent(MainActivity.this, ResultActivity.class);
        intentToResultActivity.putExtra(EXTRA_PHOTO, bytes);
        startActivity(intentToResultActivity);
    }


    @Override
    public void takePhotoSuccess(byte[] data) {
        goOnResult(data);
    }

    @Override
    public void takePhotoError() {

    }

    @Override
    public void displayLoading() {
        progress.setMessage("Please wait...");
        progress.setIndeterminate(true);
        progress.show();
    }

    @Override
    public void dismissLoading() {
        progress.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.onStop();
    }
}
