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
import android.util.Log;
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
public class HospitalFragment extends Fragment {

    private ArrayList<ListData> listData;
    private MyAdapter adapter;
    private TextView emptyView;
    private RecyclerView rv;

    public HospitalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = new ArrayList<ListData>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hospital, container, false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        listSetup();
        FloatingActionButton mFabButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddHospitalActivity.class);
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
            Log.d("MyTag1", "emptyView");
        } else {

            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            adapter = new MyAdapter(listData);
            rv.setAdapter(adapter);
            Log.d("MyTag1", "setAdapter");
        }


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return rootView;
    }

    public void listSetup() {
        SQLiteDatabase db = getActivity().openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
        //Cursor c = db.rawQuery("SELECT * FROM hospital WHERE dummy <> 1", null);
        Cursor c = db.query("hospital", null, null, null, null, null, null);
        while (c.moveToNext()) {
            String hospital_name = c.getString(1);
            String preference = c.getString(2);
            ListData temp = new ListData(hospital_name, preference);
            listData.add(temp);

        }
        c.close();
//        Log.d("MyTag1", "listSetup"+  listData.get(1).getString1());
    }
}
