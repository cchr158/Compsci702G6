package com.app.group_6.galeshapley;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import com.app.group_6.galeshapley.Data.ListData;

import java.util.ArrayList;
import java.util.LinkedList;

import Obftesting.f9b8704d3c81cb30f3831fb2bef4d6b20289d08a7e420f1ff17ead1508b194dc;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BridgeService extends IntentService {

    private ArrayList<ListData> resultList;

    public BridgeService() {
        super("BridgeService");
        resultList = new ArrayList<>();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            LinkedList<String> param1 = new LinkedList<>();
            param1.addAll(intent.getStringArrayListExtra("HOSPITAL_LIST"));
            LinkedList<String> param2 = new LinkedList<>();
            param2.addAll(intent.getStringArrayListExtra("STUDENT_LIST"));
            Log.d("MyTag2", "onHandleIntent()" + param1.toString() + param2.toString());
            String[] returnString;
            //Matching matching = new Matching(param1,param2);

            //LinkedList<String> temp = Matching.GS(param1, param2);
            //returnString = temp.toArray(new String[temp.size()]);

            returnString = f9b8704d3c81cb30f3831fb2bef4d6b20289d08a7e420f1ff17ead1508b194dc.a94ca56dc44ed8585ec115da27fcb5447dd3d78fd7cbae7cb9da8de0da64f0de3(param1, param2);


            SQLiteDatabase db = openOrCreateDatabase("group6.db", Context.MODE_PRIVATE, null);
            for (int m = 0; m < returnString.length; m += 2) {
                ContentValues cv = new ContentValues();
                cv.put("hospital_name", returnString[m]);
                cv.put("student_name", returnString[m + 1]);
                db.insert("result", null, cv);
            }

            db.close();

            Log.d("MyTag", "finished adding result");

        }
    }

}

