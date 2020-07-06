package edu.uic.spring20.kammardiprajwal.applicationtwo.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import edu.uic.spring20.kammardiprajwal.applicationtwo.AttrActivity;
import edu.uic.spring20.kammardiprajwal.applicationtwo.RestoActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    private static final String INTENT_NEEDED_RESTO = "edu.spring.2020.Project3.RESTO";
    private static final String INTENT_NEEDED_ATTR = "edu.spring.2020.Project3.ATTR";


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "onReceive: Action" + action);

        if(action.equals(INTENT_NEEDED_RESTO)) {
            Log.d(TAG, "onReceive: Resto caught. Starting activity");
            Intent openActivity = new Intent(context , RestoActivity.class);
            context.startActivity(openActivity);
        }
        else if(action.equals(INTENT_NEEDED_ATTR)){
            Intent openActivity = new Intent(context , AttrActivity.class);
            context.startActivity(openActivity);
        }

    }
}
