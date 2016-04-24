package com.app.group_6.galeshapley.UI;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.group_6.galeshapley.R;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBInitial();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), MainActivity.this); //See inner class for implementation below.
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    //Creates SQL Database with 3 tables, hospital, student and, result.
    public void DBInitial() {
        db = openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS hospital");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS result");
        db.execSQL("CREATE TABLE hospital (_id INTEGER PRIMARY KEY AUTOINCREMENT, hospital_name VARCHAR, preference VARCHAR, dummy INTEGER)");
        db.execSQL("CREATE TABLE student (_id INTEGER PRIMARY KEY AUTOINCREMENT, student_name VARCHAR, preference VARCHAR, dummy INTEGER)");
        db.execSQL("CREATE TABLE result (_id INTEGER PRIMARY KEY AUTOINCREMENT, hospital_name VARCHAR, student_name VARCHAR)");
 /*       ContentValues cv = new ContentValues();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        ContentValues cv3 = new ContentValues();
        ContentValues cv4 = new ContentValues();
        ContentValues cv5 = new ContentValues();
        cv.put("hospital_name", "h1");
        cv.put("preferance", "s1,s3,s2");
        cv.put("dummy",0);
        cv1.put("hospital_name", "h2");
        cv1.put("preferance", "s1,s3,s2");
        cv1.put("dummy",0);
        cv2.put("hospital_name", "h3");
        cv2.put("preferance", "s3,s1,s2");
        cv2.put("dummy",0);

        cv3.put("student_name", "s1");
        cv3.put("preferance", "h2,h3,h1");
        cv3.put("dummy",0);
        cv4.put("student_name", "s2");
        cv4.put("preferance", "h1,h2,h3");
        cv4.put("dummy",0);
        cv5.put("student_name", "s3");
        cv5.put("preferance", "h2,h1,h3");
        cv5.put("dummy",0);
        db.insert("hospital", null, cv);
        db.insert("hospital", null, cv1);
        db.insert("hospital", null, cv2);
        db.insert("student", null, cv3);
        db.insert("student", null, cv4);
        db.insert("student", null, cv5);*/
        db.close();
        Toast.makeText(this.getApplicationContext(), "DBInitial", Toast.LENGTH_LONG);
        Log.d("MyTag1", "DBInitiald");
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.menu_main, menu);
          return true;
      }

     @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();

          if (id == R.id.action_settings) {
              return true;
          }

          return super.onOptionsItemSelected(item);
      }
  */
    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[]{"Hospital", "Med Student", "Result"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new HospitalFragment();
                case 1:
                    return new StudentFragment();
                case 2:
                    return new ResultFragment();
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }
    }
}