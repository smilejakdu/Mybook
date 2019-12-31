package com.example.cns09.study.Navi.StopWatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cns09.study.Navi.StopWatch.StopInfo;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SharedStop {

    String shared = "stopshared";    // String 하고 이름을 shared 로 지어주게 된ㄷ ㅏ.

    public String stopInfoString(StopInfo stopInfo) {
        return stopInfo.time;
    }

    public void saveData(Context context, String result) {

        String plus = result;
        SharedPreferences sharedPreferences = context.getSharedPreferences(shared, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("result", plus);
        editor.commit();
    }

    public String loadData(Context scontext) {    // 불러오는기능
        SharedPreferences sharedPreferences = scontext.getSharedPreferences(shared, MODE_PRIVATE);
        String d = sharedPreferences.getString("result", "");
        return d;

    }

    public ArrayList<StopInfo> Stopedit(Context scontext) {
        SharedPreferences sharedPreferences = scontext.getSharedPreferences(shared, MODE_PRIVATE);
        String base = sharedPreferences.getString("result", "");
        String splitedStopInfo[] = base.split("@");
        ArrayList<StopInfo> stopInfoArrayList = new ArrayList<>();

        for (int i = 0; i < splitedStopInfo.length; i++) {
            if (!splitedStopInfo[i].equals("")) {
                StopInfo stopInfo = new StopInfo(splitedStopInfo[i]);
                stopInfoArrayList.add(stopInfo);
            }
        }

        return stopInfoArrayList;
    }

    public void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shared, MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
