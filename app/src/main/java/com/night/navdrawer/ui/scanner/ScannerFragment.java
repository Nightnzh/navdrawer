package com.night.navdrawer.ui.scanner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.night.navdrawer.R;
import com.night.navdrawer.model.QrCode;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ScannerFragment extends Fragment {

    private ScannerViewModel mViewModel;
    private int RE_CAMERA = 200;
    private SurfaceView surfaceView;
    private TextView textView_info;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private String TAG = Scanner.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private com.night.navdrawer.camera.Scanner scanner;
    private RecyclerView recyclerView;
    private Radapter adapter;

    public static ScannerFragment newInstance() {
        return new ScannerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity()).get(ScannerViewModel.class);
        fragmentManager = getFragmentManager();
        view = inflater.inflate(R.layout.fragment_scanner, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));
        adapter = new Radapter(requireContext());
        recyclerView.setAdapter(adapter);
        mViewModel.getAllQrCodes().observe(getViewLifecycleOwner(), new Observer<List<QrCode>>() {
            @Override
            public void onChanged(List<QrCode> qrCodes) {
                adapter.setQrCodes(qrCodes);
            }
        });

        
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"onRequestPermissionsResult");
        if(requestCode == RE_CAMERA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fragmentManager.beginTransaction()
                        .detach(this)
                        .attach(this)
                        .commit();
            }
        }
    }



    private void initCamera(View root) {
        surfaceView = root.findViewById(R.id.surfaceView_camera);
        //textView_info = root.findViewById(R.id.textView_info);
        barcodeDetector = new BarcodeDetector.Builder(requireContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(requireContext(),barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(800,800)
                .build();
        surfaceView.draw(new Canvas());
        surfaceView.getHolder()
                .addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                    try {
                        cameraSource.start(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG,"cameraSource.stop()");
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView_info.post(new Runnable(){
                        @Override
                        public void run() {
                            textView_info.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
        Log.d(TAG,"initCamera");
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