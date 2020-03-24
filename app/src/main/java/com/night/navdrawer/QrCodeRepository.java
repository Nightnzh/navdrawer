package com.night.navdrawer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.night.navdrawer.db.QrCodeDao;
import com.night.navdrawer.db.QrCodeDataBase;
import com.night.navdrawer.model.QrCode;

import java.util.List;

public class QrCodeRepository {
    private QrCodeDao qrCodeDao;
    private LiveData<List<QrCode>> allQrCodes;

    public QrCodeRepository(Application application){
        QrCodeDataBase db = QrCodeDataBase.getDatabase(application);
        qrCodeDao = db.qrCodeDao();
        allQrCodes = qrCodeDao.getAll();
    }

    public LiveData<List<QrCode>> getAllQrCodes(){
        return allQrCodes;
    }

    public void insert(QrCode qrCode){
        qrCodeDao.insert(qrCode);
    }
}
