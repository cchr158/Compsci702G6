package com.app.group_6.galeshapley.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.group_6.galeshapley.R;

public class MainActivity extends AppCompatActivity {
    Button exitB;
    Button nextB;
    Button prevB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exitB = (Button) findViewById(R.id.Exit);
        nextB = (Button) findViewById(R.id.Next);
        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to Hospotialactivity
                Intent intent = new Intent(MainActivity.this, HospotialActivity.class);
                Bundle mBundle = new Bundle();
                //mBundle.putParcelable();
                intent.putExtras(mBundle);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }
}

