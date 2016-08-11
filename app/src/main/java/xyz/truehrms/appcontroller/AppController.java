package xyz.truehrms.appcontroller;

import android.app.Application;

import xyz.truehrms.dataholder.DataHolder;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataHolder.getInstance();
    }
}
