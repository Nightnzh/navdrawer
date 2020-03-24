package com.night.navdrawer.ui.scanner;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.night.navdrawer.QrCodeRepository;
import com.night.navdrawer.db.QrCodeDao;
import com.night.navdrawer.model.QrCode;

import java.util.List;

public class ScannerViewModel extends AndroidViewModel {
    private QrCodeRepository qrCodeRepository;
    private LiveData<List<QrCode>> allQrCodes;

    public ScannerViewModel(Application application){
        super(application);
        qrCodeRepository = new QrCodeRepository(application);
        allQrCodes = qrCodeRepository.getAllQrCodes();
    }

    LiveData<List<QrCode>> getAllQrCodes(){
        return allQrCodes;
    }
    public void insert(QrCode qrCode){
        qrCodeRepository.insert(qrCode);
    }
}
