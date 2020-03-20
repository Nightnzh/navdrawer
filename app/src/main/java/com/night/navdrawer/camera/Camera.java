package com.night.navdrawer.camera;

import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

public class Camera {
//    private String TAG =  Camera.class.getSimpleName();

    public static android.hardware.Camera getCameraInstance(){
        android.hardware.Camera c = null;
        try {
            c = android.hardware.Camera.open();
        }catch (Exception e){
            Log.d("Camera",e.toString());
        }
        return c;
    }
}
