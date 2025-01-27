package com.example.cns09.study.Navi.Note;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cns09.study.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditNoteActivity extends AppCompatActivity {
//    com.example.cns09.study
    public static final String EXTRA_ID =
            "com.example.cns09.study.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.cns09.study.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.cns09.study.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE =
            "com.example.cns09.study.EXTRA_DATE";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView tvToday;
    private Toolbar tbBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        tbBack = findViewById(R.id.tb_back);

        setSupportActionBar(tbBack);
        setTitle("Mybook");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        tvToday = findViewById(R.id.tv_today);


        SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd");
        Date dateTime = new Date();
        String time1 = format1.format(dateTime);

        Log.d("time1", "onCreate: " + time1);
        tvToday.setText(time1);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            tvToday.setText(intent.getStringExtra(EXTRA_DATE));
        } else {
            setTitle("Add Note");
        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = tvToday.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DATE, date);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;

            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
