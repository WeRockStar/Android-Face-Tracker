package com.werockstar.androidfacetracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.cameraview.CameraView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private CameraView mCameraView;
    private Button btnTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraView = (CameraView) findViewById(R.id.cameraview);

        btnTake = (Button) findViewById(R.id.btnTake);

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraView.takePicture();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasGrantCameraPermission()) {
            mCameraView.start();
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraView.start();
                }
                btnTake.setEnabled(hasGrantCameraPermission());
                break;
        }
    }
}
