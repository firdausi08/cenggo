package com.example.afip.cobalist.Service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afip on 7/26/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("Cenggo","Message Received : "+remoteMessage.getData());

        try {
            JSONObject data = new JSONObject(remoteMessage.getData().get("message"));
            Log.d("Token","Message content : "+data);
            float ph = (float) data.getDouble("ph");
            float tds = (float) data.getDouble("ec") * 0.65f;
            float dhl = (float) data.getDouble("ec");
            float suhu_air = (float) data.getDouble("suhu_air");
            float suhu_udara = (float) data.getDouble("suhu_udara");

            Intent intent = new Intent("data_sensor");
            intent.putExtra("ph",ph);
            intent.putExtra("tds",tds);
            intent.putExtra("dhl",dhl);
            intent.putExtra("suhu_air",suhu_air);
            intent.putExtra("suhu_udara",suhu_udara);
            sendBroadcast(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
