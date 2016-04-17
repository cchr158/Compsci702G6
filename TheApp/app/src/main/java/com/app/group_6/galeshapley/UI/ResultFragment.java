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
import android.widget.Toast;

import com.app.group_6.galeshapley.Adapter.MyAdapter;
import com.app.group_6.galeshapley.Data.ListData;
import com.app.group_6.galeshapley.Matching;
import com.app.group_6.galeshapley.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
        mFabButton.setOnClickListener(new View.OnClickListener(){
            //creates blues and puts them in a list ready for the matching algorithm.
            private Collection createAListOfBlues(SQLiteDatabase db){
                String[] colums = {"hospital_name","preferance"};
                Cursor cr = db.query("hospital",colums,null,null,null,null,null);
                cr.moveToFirst();
                List blue = new LinkedList();
                do{
                    final String hospitalName = new String(cr.getString(0));
                    blue.add(new blue() {
                        private String id = hospitalName;
                        private Collection preferanceList;
                        private pink myPink=null;

                        pink getPink(){
                            return this.myPink;
                        }
                        public void setPink(pink p){
                            this.myPink=p;
                        }
                        Collection getPreferanceList() {
                            return preferanceList;
                        }
                        private blue setPreferanceList(Object preferanceList) {
                            this.preferanceList = new ArrayList();
                            Object[] prefArray = ((String)preferanceList).split(",");
                            for(int i=0; i<prefArray.length; i++){
                                this.preferanceList.add(prefArray[i]);
                            }
                            return this;
                        }
                        public String getId() {
                            return id;
                        }
                        boolean equals(blue b){
                            return this.id.equals(b.getId());
                        }
                    }.setPreferanceList(cr.getString(1)));
                }while(cr.moveToNext());
                cr.close();
                return blue;
            }
            //creates pinks and puts them in a list ready for the matching algorithm.
            private Collection createAListOfPink(SQLiteDatabase db){
                String[] colums = {"student_name","preferance"};
                Cursor cr = db.query("student",colums,null,null,null,null,null);
                cr.moveToFirst();
                List pink = new LinkedList();
                do{
                    final String studentName = new String(cr.getString(0));
                    pink.add(new pink() {
                        private String id = studentName;
                        private Collection preferanceList;
                        private blue myBlue=null;

                        String getId() {
                            return id;
                        }
                        blue getMyBlue(){
                            return this.myBlue;
                        }
                        boolean compareToMyBlue(blue b){
                            Object[] testGroup = this.preferanceList.toArray();
                            for (int i=0; i<this.preferanceList.size(); i++){
                                if(((blue)testGroup[i]).equals(this.myBlue)){
                                    return false;
                                }else if(((blue)testGroup[i]).equals(b)){
                                    return true;
                                }
                            }
                            return false;
                        }
                        void setBlue(blue b){
                            this.myBlue=b;
                            b.setPink(this);
                        }
                        private pink setPreferanceList(Object preferanceList) {
                            this.preferanceList = new ArrayList();
                            Object[] prefArray = ((String)preferanceList).split(", ");
                            for(int i=0; i<prefArray.length; i++){
                                this.preferanceList.add(prefArray[i]);
                            }
                            return this;
                        }
                        boolean isFree(){
                            return this.myBlue == null;
                        }
                        Collection getPreferanceList() {
                            return preferanceList;
                        }
                    }.setPreferanceList(cr.getString(1)));
                }while(cr.moveToNext());
                cr.close();
                return pink;
            }
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getActivity().openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
                Collection blue = createAListOfBlues(db);
                Collection pink = createAListOfPink(db);
                db.close();
                if(((LinkedList)blue).size() == ((LinkedList)pink).size()){
                    Matching match = new Matching(blue,pink);
                    match.GaleShapley();
                    //TODO
                    //display result of galeshapley.
                }else{
                    //TODO
                    //lists need to be the same size.
                    ;
                }
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
            MyAdapter adapter = new MyAdapter(listData);
            rv.setAdapter(adapter);
        }

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }
}

interface blue{
    void setPink(pink p);
    String getId();
}
interface pink{}