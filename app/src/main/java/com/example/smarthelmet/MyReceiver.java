package com.example.smarthelmet;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    SharedPreferences pref;
    @Override
    public void onReceive(Context context, Intent intent) {
        pref = context.getSharedPreferences("numpref", Context.MODE_PRIVATE);
        if(intent.getAction().equals(SMS_RECEIVED)){ //filter the intent

            Bundle bundle = intent.getExtras();  //get system call data in bundle

            SmsMessage[] msgs = null;
            String str = "";
            if(bundle != null){

                Object [] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for(int i = 0; i< msgs.length; i++){
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    str+= msgs[i].getDisplayMessageBody();
                }

                Log.i("Msg>>>>>>>>>>>>>>>>>>>>",msgs[0].getOriginatingAddress());
                if(msgs[0].getOriginatingAddress().equals( pref.getString("number",""))){
                showNotification(context);
                }

            }

            else{
                Log.i("Error>>>>>>>>>>>>>>>>>>","Bundle is emety");
            }
        }
    }

    private void showNotification(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder n = new Notification.Builder(context, "ch1").setSmallIcon(R.drawable.ic_launcher_foreground).setContentText("Accident Alert");
            NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            Intent i = new Intent(context, MapsActivity.class);
            PendingIntent ac = PendingIntent.getActivity(context, 0, i, 0);
            n.setContentIntent(ac);
            nm.notify(12222, n.build());
        }
        else {
            Notification.Builder n = new Notification.Builder(context).setSmallIcon(R.drawable.ic_launcher_foreground).setContentText("Accident Alert");
            NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            Intent i = new Intent(context, MapsActivity.class);
            PendingIntent ac = PendingIntent.getActivity(context, 0, i, 0);
            n.setContentIntent(ac);
            nm.notify(12222, n.build());
        }
    }

}
