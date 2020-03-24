package com.night.navdrawer.db;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.night.navdrawer.model.QrCode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {QrCode.class},version = 1,exportSchema = false)
public abstract class QrCodeDataBase extends RoomDatabase {
    public abstract QrCodeDao qrCodeDao();
    private static volatile QrCodeDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static QrCodeDataBase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (QrCodeDataBase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context,QrCodeDataBase.class,"qrCodeDatabase")
                            .addCallback(databaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    QrCodeDao dao = INSTANCE.qrCodeDao();
                    dao.deleteAll();
                    QrCode qrCode = new QrCode("124124");
                    dao.insert(qrCode);
                    qrCode = new QrCode("2414");
                    dao.insert(qrCode);
                }
            });
        }
    };
}
