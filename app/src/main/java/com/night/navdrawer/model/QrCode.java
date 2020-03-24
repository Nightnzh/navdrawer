package com.night.navdrawer.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "qrCodes")
public class QrCode {

    @PrimaryKey
    @NonNull
    private String qr;

    public QrCode(String qr){
        this.qr = qr;
    }

    public String getQr(){
        return qr;
    }
}
