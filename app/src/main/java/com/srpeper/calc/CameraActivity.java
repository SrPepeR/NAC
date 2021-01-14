package com.srpeper.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Detector.Processor {

    private Bundle bundle;

    private SurfaceView cameraView;
    private EditText operationTxt;
    private CameraSource cameraSource;
    private SwitchMaterial switchMaterial;

    private TextRecognizer textRecognizer;

    private final int CAMERA = 1414;

    private boolean isPaused = false;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bundle = savedInstanceState;

        setContentView(R.layout.activity_camera);
        this.cameraView = findViewById(R.id.surface_view);
        this.operationTxt = findViewById(R.id.txtview);
        this.switchMaterial = findViewById(R.id.camSwitch);

        this.switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                this.isPaused = true;
            } else {
                this.isPaused = false;
                this.operationTxt.requestFocus();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        createCamera();
    }

    private void createCamera() {
        this.textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!this.textRecognizer.isOperational()) {
            Log.e("NAC", "Detector dependencies are not yet available");
        } else {
            this.textRecognizer.setProcessor(this);
            cameraSource = new CameraSource.Builder(getApplicationContext(), this.textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(this);
        }
    }

    public void calculate (View button) {
        Intent result = new Intent();
        result.putExtra("OPERATION", this.operationTxt.getText().toString());
        setResult(CAMERA, result);
        finish();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }

    @Override
    public void release() {
    }

    @Override
    public void receiveDetections(@NonNull Detector.Detections detections) {
        if (!isPaused) {
            SparseArray<TextBlock> items = detections.getDetectedItems();
            Frame.Metadata meta = detections.getFrameMetadata();

            if (items.size() > 0) {

                int[] goalHeigth = {
                        (meta.getHeight() / 2) - 200,
                        (meta.getHeight() / 2) + 200
                };

                for (int i = 0; i < items.size(); i++) {
                    int tempId = items.keyAt(i);
                    TextBlock textBlock = items.get(tempId);

                    int[] blockHeigth = {
                            textBlock.getBoundingBox().top,
                            textBlock.getBoundingBox().bottom
                    };

                    if (blockHeigth[0] >= goalHeigth[0] && blockHeigth[1] <= goalHeigth[1]) {
                        this.operationTxt.post(() -> {
                            operationTxt.setText(textBlock.getValue());
                        });
                    }
                }
            }
        }
    }
}