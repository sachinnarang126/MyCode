package xyz.truehrms.dataholder;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import xyz.truehrms.bean.Permissions;
import xyz.truehrms.utils.Constant;

public class DataHolder {
    private static DataHolder dataHolder;
    SharedPreferences sharedPreferences;
    private List<Permissions.Result> resultList;


    private DataHolder() {
        /////singlton
    }

    public synchronized static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;
    }

    public List<Permissions.Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Permissions.Result> resultList) {
        this.resultList = resultList;
    }

    public SharedPreferences getSharedPreferences(Context context) {
        return sharedPreferences = context.getSharedPreferences(Constant.PREFERENCE, 0);
    }
}
