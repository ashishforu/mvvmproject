package com.example.demospeedtest.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Speed.class}, version  = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="DB_NAME";

    public abstract SpeedDao speedDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null)
        {
            synchronized (AppDatabase.class){
                if(INSTANCE == null)
                {
                    INSTANCE= Room.databaseBuilder(context,AppDatabase.class,
                                    DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsynTask(INSTANCE);
        }
    };
    static class PopulateAsynTask extends AsyncTask<Void,Void,Void>
    {
        private SpeedDao userDao;
        PopulateAsynTask(AppDatabase appDatabase)
        {
            userDao=appDatabase.speedDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }
}