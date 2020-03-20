package com.night.navdrawer.ui.scanner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.night.navdrawer.R;


import static android.app.Activity.RESULT_OK;
public class ScannerFragment extends Fragment {

    private ScannerViewModel mViewModel;
    private int RE_CAMERA = 200;
    private SurfaceView surfaceView;
    private TextView textView_info;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    public static ScannerFragment newInstance() {
        return new ScannerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_scanner, container, false);
        surfaceView = root.findViewById(R.id.surfaceView_camera);
        textView_info = root.findViewById(R.id.textView_info);
        barcodeDetector = new BarcodeDetector.Builder(this.getContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this.getContext(),barcodeDetector)
                .setRequestedPreviewSize(300,300).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RE_CAMERA || resultCode == RESULT_OK){
            if (checkCameraHardware(this.getContext())){

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScannerViewModel.class);
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.CAMERA},RE_CAMERA);
        } else {

        }
    }

    private boolean checkCameraHardware(Context context){
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }
}

//https://www.ruyut.com/2018/12/5-android-studio-qr-code.html