package com.werockstar.androidfacetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.face.FaceDetector;

public class ResultActivity extends AppCompatActivity {

    ImageView ivResult;
    private FaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ivResult = (ImageView) findViewById(R.id.ivResult);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getByteArray(MainActivity.EXTRA_PHOTO) != null) {
            displayResult(bundle);
            createFaceDetection();
        }
    }

    private void displayResult(Bundle bundle) {
        Glide.with(this)
                .load(bundle.getByteArray(MainActivity.EXTRA_PHOTO))
                .into(ivResult);
    }

    private void createFaceDetection() {
        detector = new FaceDetector.Builder(this)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Glide.clear(ivResult);
    }
}
