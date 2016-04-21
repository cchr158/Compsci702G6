package com.app.group_6.galeshapley;

import android.app.IntentService;
import android.content.Intent;

import com.app.group_6.galeshapley.Data.ListData;

import java.util.ArrayList;
import java.util.LinkedList;

import GaleShapleV1_1.Matching;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BridgeService extends IntentService {

    public BridgeService() {
        super("BridgeService");
        resultList = new ArrayList<>();
    }

    private ArrayList<ListData> resultList;

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
            Matching matching = new Matching(param1, param2);
            ArrayList<String> returnString = new ArrayList<>();
            returnString.addAll(matching.GS(param1, param2));
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("Result");
            broadcastIntent.putStringArrayListExtra("result", returnString);
        }
    }

}

