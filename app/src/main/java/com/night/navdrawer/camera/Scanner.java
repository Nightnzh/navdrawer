package com.night.navdrawer.camera;
import android.content.Context;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Scanner {
//    private String TAG =  Camera.class.getSimpleName();

    private Context context;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView surfaceView;

    public Scanner(Context context){
        this.context = context;
    }
    public void init(){
        barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(context,barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(800,600)
                .build();
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size()!=0){

                }
            }
        });
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    cameraSource.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
    }

//    public static android.hardware.Camera getCameraInstance(){
//        android.hardware.Camera c = null;
//        try {
//            c = android.hardware.Camera.open();
//        }catch (Exception e){
//            Log.d("Camera",e.toString());
//        }
//        return c;
//    }
}
