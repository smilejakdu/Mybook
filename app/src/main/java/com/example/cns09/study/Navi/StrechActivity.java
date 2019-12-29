package com.example.cns09.study.Navi;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cns09.study.R;
import com.example.cns09.study.databinding.ActivityStrechBinding;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

public class StrechActivity extends AppCompatActivity {
    private ActivityStrechBinding binding;
    BigDecimal mTimeSecond = BigDecimal.ZERO;
    boolean mTimerRunning = false;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_strech);

        setSupportActionBar(binding.tbBack);
        setTitle("Mybook");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.ivPlayPause.setOnClickListener(view -> {

            String timeInsert = binding.etTimeInsert.getText().toString().trim();
            if (timeInsert == "" || timeInsert == null || timeInsert.isEmpty()) {
                Toast.makeText(StrechActivity.this, "숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mTimerRunning == false) {

                mTimerRunning = true;

                if (mTimeSecond.equals(BigDecimal.ZERO)) {
                    mTimeSecond = BigDecimal.valueOf(Double.parseDouble(binding.etTimeInsert.getText().toString().trim()));
                }
                binding.etTimeInsert.setText("");
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override

                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (mTimerRunning) {

                                    mTimeSecond = mTimeSecond.subtract(BigDecimal.valueOf(0.05));
                                    binding.tvTime.setText(String.format("%.2f 초", mTimeSecond));
                                    binding.ivPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
                                    if (mTimeSecond.compareTo(BigDecimal.ZERO) == -1) {
                                        mTimer.cancel();
                                        binding.tvTime.setText("00초");
                                        timerEndMethod();
                                    }
                                }
                            }
                        });
                    }
                }, 0, 50);
            } else {
                mTimer.cancel();
                timerEndMethod();
            }
        });

        binding.ivStop.setOnClickListener(view -> {
            mTimeSecond = BigDecimal.ZERO;
            binding.tvTime.setText("00초");
        });

    }

    private void timerEndMethod() {
        mTimerRunning = false;
        binding.ivPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        if (mTimeSecond.compareTo(BigDecimal.ZERO) == -1) {
            mTimeSecond = BigDecimal.ZERO;
        }
    }

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
