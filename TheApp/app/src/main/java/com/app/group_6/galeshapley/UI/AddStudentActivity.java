package com.app.group_6.galeshapley.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.group_6.galeshapley.R;

public class AddStudentActivity extends AppCompatActivity {


    private EditText studentName;
    private EditText preferenceString;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_studnet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        studentName = (EditText) findViewById(R.id.editName);
        saveBtn = (Button) findViewById(R.id.saveStudent_button);
        preferenceString = (EditText) findViewById(R.id.editHostalPreference);
        saveBtn.setOnClickListener(new MyOnClickListener());
    }


    public class MyOnClickListener implements View.OnClickListener {
        boolean isAdd = true;

        @Override
        public void onClick(View v) {


            ContentValues cv = new ContentValues();
            SQLiteDatabase db = openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
            cv.put("student_name", studentName.getText().toString());
            cv.put("preference", preferenceString.getText().toString());
            db.insert("student", null, cv);
            db.close();
            Log.d("MyTag", "finish adding Hospital");
            finish();
        }
    }
}
