package com.example.cns09.study.Navi.MovieWebService;

import com.example.cns09.study.Navi.BookModel.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"X-Naver-Client-Id: UHsRE0R9LGspRWna4F2I", "X-Naver-Client-Secret: _x06AWGdFZ"})
    @GET("book.json")
    Call<Book> getBooks(@Query("query") String title,
                        @Query("display") int displaySize,
                        @Query("start") int startPosition);

}
