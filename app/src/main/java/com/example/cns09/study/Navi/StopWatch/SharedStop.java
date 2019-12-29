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

        Log.d("qwerasdf", "stopInfo" + stopInfo.time);  // Log 를 찍어서 stopInfo.time 이 들어오는지 확인한다 .
        // 여기로 오지도 않음 . = > Stopadapter 를 봐야함 .
        return stopInfo.time.toString();    // return 으로 stopInfo.time.toString () ; 으로 String 으로 바꿔줘서 return 으로 보내주게 된다 .

    }

    public void saveData(Context scontext, String result) {

        String shared = "stopshared";
//        String base = loadData(scontext);
        // 위에를 넣지않으니깐 clear 후에 , 다시 데이터를 넣을때 덮어서 넣어진다 .
        // 다시 위에 String base = loadData(scontext); 를 주석처리하니깐. 삭제가 정상적으로 안됨 .
        String plus = result ;
        Log.d("qwerasdf", "plus : " + plus);
        SharedPreferences sharedPreferences = scontext.getSharedPreferences(shared, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("result", plus);
        editor.commit();

    }

    public String loadData(Context scontext) {    // 불러오는기능
        SharedPreferences sharedPreferences = scontext.getSharedPreferences(shared, MODE_PRIVATE);
        String d = sharedPreferences.getString("result", "");
        Log.d("qwerasdf", "d : " + d); // d 도 아무것도 없음
        return d;

    }
    ///////
    public ArrayList<StopInfo> Stopedit(Context scontext) {
        SharedPreferences sharedPreferences = scontext.getSharedPreferences(shared, MODE_PRIVATE);
        String base = sharedPreferences.getString("result", "");
        String splitedStopInfo[] = base.split("@");

        ArrayList<StopInfo> stopInfoArrayList = new ArrayList<>();

        for (int i = 0; i < splitedStopInfo.length; i++) {
            if(!splitedStopInfo[i].equals("")){
                StopInfo stopInfo = new StopInfo(splitedStopInfo[i]);
                stopInfoArrayList.add(stopInfo);
            }
        }

        Log.d("abc", "list :" + stopInfoArrayList);
        return stopInfoArrayList;
    }

    public void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shared, MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

    }
}
