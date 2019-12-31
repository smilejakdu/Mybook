package com.example.cns09.study.Navi.StopWatch;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.MenuItem;

import com.example.cns09.study.R;
import com.example.cns09.study.databinding.ActivityStopwatchBinding;

import java.util.ArrayList;

public class Stopwatch extends AppCompatActivity {
    private ActivityStopwatchBinding binding;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;
    Context context;
    Stopadapter stopadapter;
    RecyclerView.LayoutManager sLayoutManager;
    ArrayList<StopInfo> stopInfoArrayList = new ArrayList<>();
    final SharedStop sharedStop = new SharedStop();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stopwatch);

        setSupportActionBar(binding.tbBack);
        setTitle("Mybook");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;
        handler = new Handler();

//       항상 stopInfoArrayList 이 init_rcv () 함수 위에 있어야 한다.
        stopInfoArrayList = sharedStop.Stopedit(this);
        init_rcv();
        startButton();
        pauseButton();
        resetButton();
        saveButton();

    }



    private void saveButton() {
        //저장버튼
        binding.ivSave.setOnClickListener(view -> {
            binding.tvWatch.getText();
            getStopInfoArrayListFromSp(binding.tvWatch.getText().toString());  // 숫자로 받은것을 String 으로 바꿔서 받게된다
            stopadapter.sList = sharedStop.Stopedit(context);
            stopadapter.notifyDataSetChanged();
        });
    }

    private void resetButton() {
        // 리셋 버튼
        binding.ivRest.setOnClickListener(view -> {
            MillisecondTime = 0L;
            StartTime = 0L;
            TimeBuff = 0L;
            UpdateTime = 0L;
            Seconds = 0;
            Minutes = 0;
            MilliSeconds = 0;
            binding.tvWatch.setText("00:00:00");
        });
    }

    private void startButton() {
        //시작 버튼
        binding.ivStart.setOnClickListener(view -> {
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            binding.ivRest.setEnabled(false);

        });
    }

    private void pauseButton() {
        // 정지 버튼
        binding.ivPause.setOnClickListener(view -> {
            TimeBuff += MillisecondTime;
            handler.removeCallbacks(runnable);
            binding.ivRest.setEnabled(true);

        });
    }


    private void init_rcv() {
        binding.rcvWatch.setHasFixedSize(true);
        sLayoutManager = new LinearLayoutManager(this);
        binding.rcvWatch.setLayoutManager(sLayoutManager);
//        어댑터 설정
        stopadapter = new Stopadapter(this, stopInfoArrayList);
        binding.rcvWatch.setAdapter(stopadapter);
    }


    public String getloadData() {
        SharedStop sharedStop = new SharedStop();   // 공유 인스턴스화 불러온다 .
        String get = sharedStop.loadData(this);     //
        return get;

    }

    public void getStopInfoArrayListFromSp(String timeStr) {

        String base = getloadData();
        String split = "@";
        String base2 = base + timeStr + split;
        SharedStop sharedStop = new SharedStop();
        sharedStop.saveData(context, base2);

    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            binding.tvWatch.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}