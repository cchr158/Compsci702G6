package com.app.group_6.galeshapley.UI;

import android.content.BroadcastReceiver;
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
import android.widget.Toast;

import com.app.group_6.galeshapley.Adapter.MyAdapter;
import com.app.group_6.galeshapley.BridgeService;
import com.app.group_6.galeshapley.Data.ListData;
import com.app.group_6.galeshapley.R;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        TextView emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        ArrayList<ListData> listData = new ArrayList<>();
        FloatingActionButton mFabButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
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
                    hospitalList.add(student_name);
                    hospitalList.add(preference);
                }
                c1.close();

                db.close();
                Intent msgIntent = new Intent(getActivity(), BridgeService.class);
                msgIntent.putStringArrayListExtra("HOSPITAL_LIST", hospitalList);
                msgIntent.putExtra("STUDENT_LIST", studentList);
                getActivity().startService(msgIntent);
                Log.d("MyTag1", "onClick()");
                Toast.makeText(getActivity().getApplicationContext(), "Starting Service", Toast.LENGTH_LONG);
            }
        });
        //rv.setHasFixedSize(true);
        if (listData.isEmpty()) {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            adapter = new MyAdapter(listData);
            rv.setAdapter(adapter);
        }

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }


    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> returnString = intent.getStringArrayListExtra("Result");
            for (int i = 0; i < returnString.size(); i = i+2) {
                ListData resultItem = new ListData(returnString.get(i), returnString.get(i+1));
                listData.add(resultItem);
            }
            adapter.notifyDataSetChanged();
        }
    }
}