package com.app.group_6.galeshapley.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.app.group_6.galeshapley.Adapter.MyAdapter;
import com.app.group_6.galeshapley.BridgeService;
import com.app.group_6.galeshapley.Data.ListData;
import com.app.group_6.galeshapley.R;

import java.util.ArrayList;

final
/**
 * Created by Yiying Sun(Richard) on 12/04/2016.
 * Edited by Callan Christophersen on 17/04/2016.
 */
public class ResultFragment extends Fragment {

    private ArrayList<ListData> listData;
    private MyAdapter adapter;
    private TextView emptyView;
    private RecyclerView rv;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        listData = new ArrayList<ListData>();
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.result_swipe_refresh_layout);
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
        ArrayList<ListData> listData = new ArrayList<>();
        final FloatingActionButton mFabButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        mFabButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getActivity().openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
                Cursor c = db.query("hospital", null, null, null, null, null, null);
                ArrayList<String> hospitalList = new ArrayList<String>();
                while (c.moveToNext()) {
                    String hospital_name = c.getString(1);
                    String preference = c.getString(2);
                    hospitalList.add(hospital_name);
                    hospitalList.add(preference);
                }
                c.close();

                Cursor c1 = db.query("student", null, null, null, null, null, null);
                ArrayList<String> studentList = new ArrayList<String>();
                while (c1.moveToNext()) {
                    String student_name = c1.getString(1);
                    String preference = c1.getString(2);
                    studentList.add(student_name);
                    studentList.add(preference);
                }
                c1.close();

                db.close();
                if (hospitalList.size() == 0 || studentList.size() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Input hospital and student to generate perfect match:"
                            , Toast.LENGTH_LONG).show();
                } else if (hospitalList.size() == studentList.size()) {
                    Intent msgIntent = new Intent(getActivity(), BridgeService.class);
                    msgIntent.putStringArrayListExtra("HOSPITAL_LIST", hospitalList);
                    msgIntent.putStringArrayListExtra("STUDENT_LIST", studentList);
                    getActivity().startService(msgIntent);
                    Log.d("MyTag1", "onClick()" + hospitalList.toString() + studentList.toString());
                    Toast.makeText(getActivity().getApplicationContext(), "Starting Service", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(getActivity().getApplicationContext(),
                            "The number of hospital and student must to equal to generate perfect match current hospital:"
                                    + hospitalList.size() + "current studdent:" + studentList.size(), Toast.LENGTH_LONG).show();
                mFabButton.setEnabled(false);
            }
        });
        //rv.setHasFixedSize(true);
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

    @Override
    public void onResume() {
        listSetup();
        super.onResume();

    }

    public void listSetup() {
        SQLiteDatabase db = getActivity().openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
        //Cursor c = db.rawQuery("SELECT * FROM hospital WHERE dummy <> 1", null);
        Cursor c = db.query("result", null, null, null, null, null, null);
        listData.clear();
        while (c.moveToNext()) {
            String hospital_name = c.getString(1);
            String student_name = c.getString(2);
            ListData temp = new ListData(hospital_name, student_name);
            listData.add(temp);
            Log.d("ResultFragment", "listSetup");
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
        Log.d("ResultFragment", "listSetup");
    }


}