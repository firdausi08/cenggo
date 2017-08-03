package com.example.afip.cobalist;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afip.cobalist.model.Percobaan;
import com.example.afip.cobalist.model.SingleShotLocationProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import at.markushi.ui.CircleButton;

public class InputPercobaanActivity extends AppCompatActivity implements OnMapReadyCallback{

    String ph;
    String suhu_air;
    String suhu_udara;
    String TDS;
    String DHL;
    String title;
    String description;
    String latitude;
    String longitude;

    int id_pengukuran;

    BReceiver mReceiver;
    TextView text_suhuAir;
    TextView text_PH;
    TextView text_TDS;
    TextView text_DHL;
    EditText et_title;
    EditText et_description;
    Button btn_submit;
    CircleButton btn_location;
    ProgressDialog progressDialog;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_percobaan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mReceiver = new BReceiver();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Menunggu data sensor");
        progressDialog.setMessage("Silahkan nyalakan sensor untuk mengambil data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        registerReceiver(mReceiver,new IntentFilter("data_sensor"));

        id_pengukuran = getIntent().getIntExtra("id_pengukuran",0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initView();

    }

    private void initView() {
        text_DHL = (TextView) findViewById(R.id.dhl_nilai);
        text_PH = (TextView) findViewById(R.id.ph_nilai);
        text_suhuAir = (TextView) findViewById(R.id.suhuair_nilai);
        text_TDS = (TextView) findViewById(R.id.tds_nilai);

        et_description = (EditText) findViewById(R.id.et_deskripsi);
        et_title = (EditText) findViewById(R.id.et_judul);

        btn_submit = (Button) findViewById(R.id.button_submitPercobaan);
        btn_location = (CircleButton) findViewById(R.id.location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString();
                description = et_description.getText().toString();

                latitude = String.valueOf(googleMap.getCameraPosition().target.latitude);
                longitude = String.valueOf(googleMap.getCameraPosition().target.longitude);

                sendPercobaan();
            }
        });

    }

    private void sendPercobaan() {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengirim data");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.INPUT_PERCOBAAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean(Config.PARAM_STATUS)){
                                Percobaan percobaan = new Percobaan(jsonObject.getJSONObject(Config.PARAM_DATA));
                                Toast.makeText(InputPercobaanActivity.this,"Input percobaan berhasil",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.putExtra("percoban",percobaan);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                            else Toast.makeText(InputPercobaanActivity.this,"Request gagal",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(InputPercobaanActivity.this,"Gagal memparsing data",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Cenggo","Volley error input percobaan : "+error);
                Toast.makeText(InputPercobaanActivity.this,"Koneksi gagal",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(Config.PARAM_DESKRIPSI,description);
                map.put(Config.PARAM_LATITUDE,latitude);
                map.put(Config.PARAM_LONGITUDE,longitude);
                map.put(Config.PARAM_TITLE,title);
                map.put(Config.PARAM_ID_PENGUKURAN,""+id_pengukuran);
                map.put(Config.PARAM_PH,ph);
                map.put(Config.PARAM_TDS,TDS);
                map.put(Config.PARAM_DHL,DHL);
                map.put(Config.PARAM_SUHU_AIR,suhu_air);
                map.put(Config.PARAM_SUHU_UDARA,suhu_udara);
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void getCurrentLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != getPackageManager().PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != getPackageManager().PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }

        SingleShotLocationProvider.requestSingleUpdate(this, new SingleShotLocationProvider.LocationCallback(){
            public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location){
                LatLng latLng = new LatLng(location.latitude, location.longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
//                Toast.makeText(InputPercobaanActivity.this, ""+latLng, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(InputPercobaanActivity.this,PercobaanActivity.class);
//                intent.putExtra("receivelatlong", latLng);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getCurrentLocation();
    }

    private class BReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Cenggo","Masuk broadcast");
            ph = String.valueOf(intent.getFloatExtra("ph",0f));
            suhu_air = String.valueOf(intent.getFloatExtra("suhu_air",0f));
            suhu_udara = String.valueOf(intent.getFloatExtra("suhu_udara",0f));
            DHL = String.valueOf(intent.getFloatExtra("dhl",0f));
            TDS = String.valueOf(intent.getFloatExtra("tds",0f));

            text_PH.setText(ph);
            text_suhuAir.setText(suhu_air);
            text_TDS.setText(TDS);
            text_DHL.setText(DHL);

            progressDialog.dismiss();

        }
    }
}
