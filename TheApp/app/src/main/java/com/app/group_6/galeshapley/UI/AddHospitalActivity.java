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

public class AddHospitalActivity extends AppCompatActivity {

    private EditText hospitalName;
    private EditText preferenceString;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        hospitalName = (EditText) findViewById(R.id.editName);
        saveBtn = (Button) findViewById(R.id.saveHospital_button);
        preferenceString = (EditText) findViewById(R.id.editStudentPreference);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
                cv.put("hospital_name", hospitalName.getText().toString());
                cv.put("preferance", preferenceString.getText().toString());
                db.insert("HOSPITAL", null, cv);
                db.close();
                finish();
                Log.d("MyTag1", "finish");
            }
        });
    }

}
