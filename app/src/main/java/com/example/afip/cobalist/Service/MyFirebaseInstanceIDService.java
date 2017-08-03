package com.example.afip.cobalist.Service;

import android.util.Log;
import android.widget.Toast;

import com.example.afip.cobalist.SharedPrefManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzd;

/**
 * Created by afip on 7/26/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    SharedPrefManager sharedPrefManager;
    @Override
    public void onTokenRefresh() {
        sharedPrefManager = SharedPrefManager.getmInstance(this);
        String token = FirebaseInstanceId.getInstance().getToken();
        sharedPrefManager.setToken(token);
        Log.d("Cenggo","masuk OnTokenRefresh()" + token);
    }
}
