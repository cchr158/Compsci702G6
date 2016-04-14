package com.app.group_6.galeshapley.UI;

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
import com.app.group_6.galeshapley.Data.ListData;
import com.app.group_6.galeshapley.R;

import java.util.ArrayList;

/**
 * Created by Yiying Sun(Richard) on 12/04/2016.
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
        mFabButton.setOnClickListener(new MyOnClickListener());
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

    public class MyOnClickListener implements View.OnClickListener {
        boolean isAdd = true;

        @Override
        public void onClick(View v) {
            //TODO
            //call service to do the matching;
            Log.d("MyTag1", "onClick()");
            Toast.makeText(getActivity().getApplicationContext(), "Starting Service", Toast.LENGTH_LONG);

        }
    }
}
