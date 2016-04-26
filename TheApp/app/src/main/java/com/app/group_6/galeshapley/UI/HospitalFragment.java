package com.app.group_6.galeshapley.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

    public ArrayList<ListData> listData;
    private MyAdapter adapter;
    private TextView emptyView;
    private RecyclerView rv;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public HospitalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = new ArrayList<ListData>();
    }

    @Override
    public void onResume() {
        listSetup();
        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hospital, container, false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.hospital_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listSetup();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
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
        MyAdapter adapter = new MyAdapter(listData);
        rv.setAdapter(adapter);
        //rv.setHasFixedSize(true);
        if (listData.isEmpty()) {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        } else {
            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return rootView;
    }

    public void listSetup() {
        SQLiteDatabase db = getActivity().openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
        //Cursor c = db.rawQuery("SELECT * FROM hospital WHERE dummy <> 1", null);
        Cursor c = db.query("hospital", null, null, null, null, null, null);
        listData.clear();
        while (c.moveToNext()) {
            String hospital_name = c.getString(1);
            String preference = c.getString(2);
            ListData temp = new ListData(hospital_name, preference);
            listData.add(temp);

        }
        c.close();
        db.close();
        if (listData.isEmpty()) {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        } else {
            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        MyAdapter adapter = new MyAdapter(listData);
        rv.setAdapter(adapter);
        Log.d("HospitalFragment", "listSetup");
    }
}
