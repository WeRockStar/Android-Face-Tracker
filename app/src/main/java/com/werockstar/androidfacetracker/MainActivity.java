package com.werockstar.androidfacetracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.cameraview.CameraView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private CameraView mCameraView;
    private Button btnTake;
    private FaceDetector detector;
    private ImageView ivResult;
    public static final String EXTRA_BYTE = "EXTRA_BYTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraView = (CameraView) findViewById(R.id.cameraview);
        btnTake = (Button) findViewById(R.id.btnTake);
        ivResult = (ImageView) findViewById(R.id.ivResult);

        mCameraView.addCallback(callback);
        createFaceDetection();

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraView.takePicture();
            }
        });
    }

    private void createFaceDetection() {
        detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .build();
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
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            new Handler().post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };
}
