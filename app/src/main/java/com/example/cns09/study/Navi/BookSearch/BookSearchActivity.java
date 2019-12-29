package com.example.cns09.study.Navi.BookSearch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cns09.study.Navi.BookAdapter.BookAdapter;
import com.example.cns09.study.Navi.BookModel.Book;
import com.example.cns09.study.Navi.BookModel.Item;
import com.example.cns09.study.Navi.MovieWebService.ApiInterface;
import com.example.cns09.study.Navi.MovieWebService.ServiceGenerator;
import com.example.cns09.study.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSearchActivity extends AppCompatActivity {

    private RecyclerView rcvBooks;
    private RecyclerView.LayoutManager mLayoutManager;
    private BookAdapter mBookAdapter;
    private EditText mEtKeyword;
    private ImageView mBtnSearch;
    InputMethodManager mInputMethodManager;
    Context context;
    private Toolbar tbBack;
    ArrayList<Item> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        context = this;
        tbBack = findViewById(R.id.tb_back);
        mEtKeyword = findViewById(R.id.et_keyword);
        mBtnSearch = findViewById(R.id.iv_search);
        rcvBooks = findViewById(R.id.rcv_book);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                startSearch(mEtKeyword.getText().toString());
            }
        });

        setSupportActionBar(tbBack);
        setTitle("Movie");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//       rcv
        rcvBooks.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        rcvBooks.setLayoutManager(mLayoutManager);
        mBookAdapter = new BookAdapter(context, books);
        rcvBooks.setAdapter(mBookAdapter);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, new LinearLayoutManager(context).getOrientation());
        rcvBooks.addItemDecoration(dividerItemDecoration);

        mEtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    startSearch(mEtKeyword.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    public void hideKeyboard() {
//        mInputMethodManager.hideSoftInputFromWindow(rcvBooks.getWindowToken(), 0);
    }

    public void showEmptyFieldMessage() {
        Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
    }

    public void showNotFoundMessage(String keyword) {
        Toast.makeText(context, "\'" + keyword + "\' 를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
    }

    // 검색어가 입력되었는지 확인 후 책정보 가져오기
    public void startSearch(String title) {
        if (title.isEmpty()) {
            showEmptyFieldMessage();
        } else {
            mLayoutManager.scrollToPosition(0);
            getBooks(title);
        }
    }

    // 책 가져오기
    public void getBooks(final String title) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Book> call = apiInterface.getBooks(title, 100, 1);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    ArrayList<Item> books = new ArrayList(response.body().getItems());
                    if (books.size() == 0) {
                        mBookAdapter.clearItems();
                        showNotFoundMessage(title);
                    } else {
                        mBookAdapter.clearAndAddItems(books);
                    }
                } else {
                    Log.d("qwe", response.message());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("qwe", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
