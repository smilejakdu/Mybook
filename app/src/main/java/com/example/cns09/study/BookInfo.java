package com.example.cns09.study;

import android.net.Uri;

public class BookInfo {

    public String title;    //제목
    public String content;  //내용
    public Uri uri;

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public Uri getUri(){
        return uri;
    }

    public BookInfo(String title , String content, Uri uri){

        this.title = title;
        this.content = content;
        this.uri=uri;
    }
}

