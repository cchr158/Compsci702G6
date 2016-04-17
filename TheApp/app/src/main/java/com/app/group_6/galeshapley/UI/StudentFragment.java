package com.app.group_6.galeshapley.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.group_6.galeshapley.Adapter.MyAdapter;
import com.app.group_6.galeshapley.Data.ListData;
import com.app.group_6.galeshapley.R;

import java.util.ArrayList;

/**
 * Created by Yiying Sun(Richard) on 12/04/2016.
 * Edited by Callan Christophersen on 17/04/2016.
 */
public class StudentFragment extends Fragment {

    private ArrayList<ListData> listData;
    private MyAdapter adapter;
    private TextView emptyView;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = new ArrayList<>();
        listSetup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student, container, false);
        TextView emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);

        FloatingActionButton mFabButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        mFabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudentActivity.class);
                Bundle mBundle = new Bundle();
                intent.putExtras(mBundle);
                startActivity(intent);
                onPause();
            }
        });
        //rv.setHasFixedSize(true);
        if (listData.isEmpty()) {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {

            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            MyAdapter adapter = new MyAdapter(listData);
            rv.setAdapter(adapter);
        }


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    public void listSetup() {
        SQLiteDatabase db = getActivity().openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM student WHERE dummy <> 1", null);
        while (c.moveToNext()) {
            String student_name = c.getString(c.getColumnIndex("student_name"));
            String preferance = c.getString(c.getColumnIndex("preferance"));
            ListData temp = new ListData(student_name, preferance);
            listData.add(temp);
        }
        c.close();
    }
}
