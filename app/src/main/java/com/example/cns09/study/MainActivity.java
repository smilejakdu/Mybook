package com.example.cns09.study;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.cns09.study.Navi.BookSearch.BookSearchActivity;
import com.example.cns09.study.Navi.Note.NoteActivity;
import com.example.cns09.study.Navi.StrechActivity;
import com.example.cns09.study.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.cns09.study.Navi.StopWatch.Stopwatch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private long time = 0;
    private ArrayList<KyoboItem> list = new ArrayList();
    private final String WATING_GREETINGS = "please wating ~ ^ ^ ";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new Description().execute();
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //진행다일로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(WATING_GREETINGS);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://search.kyobobook.co.kr/web/search?vPstrKeyWord=%25EB%25B2%25A0%25EC%258A%25A4%25ED%258A%25B8%25EC%2585%2580%25EB%259F%25AC&orderClick=LAG").get();
                Elements mElementDataSize = doc.select("tbody[id=search_list]").select("tr");
                for (Element elem : mElementDataSize) {
                    String myImgUrl = elem.select("td[class=image] div[class=cover] a img").attr("src");
                    String myTitle = elem.select("td[class=detail] div[class=title] a strong").text();
                    String myAuthor = elem.select("td[class=detail] div[class=author] a").text();
                    String price = elem.select("td[class=price] div[class=sell_price] strong").text();
                    Log.d("qwe", "doInBackground: " + price);
                    list.add(new KyoboItem(myImgUrl, myTitle, myAuthor, price));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            KyoboRecyclerView kyoboRecyclerView = new KyoboRecyclerView(list, context);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            binding.rcvBook.setLayoutManager(layoutManager);
            binding.rcvBook.setAdapter(kyoboRecyclerView);
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.stopwatch) {
            Intent intent = new Intent(MainActivity.this, Stopwatch.class);
            startActivity(intent);
        } else if (id == R.id.stretch) {
            Intent intent = new Intent(MainActivity.this, StrechActivity.class);
            startActivity(intent);
        } else if (id == R.id.search) {
            Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.note) {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }
}
