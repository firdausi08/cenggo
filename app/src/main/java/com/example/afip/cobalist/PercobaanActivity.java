package com.example.afip.cobalist;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afip.cobalist.model.Percobaan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.attr.country;
import static com.example.afip.cobalist.R.raw.sungai;

public class PercobaanActivity extends AppCompatActivity {

    TextView textSuhuAir, textTds, textPH, textDhl, textPencemaran, textWaktu, textkategori, textSungai, textProbabilitas,
            textLatitude, textLongitude, textAddress;
    private SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;
    private Percobaan percobaan;
    private String nama_sungai;
    String latitude="";
    String longitude="";
    String lokasi = "";
    String city = "";
    String address = "";
    String state = "";
    String postalCode ="";
    String address_sungai = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        receivelatlong = getIntent().getStringExtra("receivelatlong");
//        Toast.makeText(PercobaanActivity.this, ""+receivelatlong, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_pengukuran);
        sharedPrefManager = SharedPrefManager.getmInstance(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Silahkan tunggu.....");

        percobaan = getIntent().getParcelableExtra("percobaan");
        nama_sungai = getIntent().getStringExtra("nama_sungai");
        latitude = percobaan.getLatitude();
        longitude = percobaan.getLongitude();

        //   Geocoder geocoder;

        List<Address> addresses;
        Geocoder geocoder = new Geocoder(PercobaanActivity.this, Locale.getDefault());



        try {

            addresses = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            lokasi =  addresses.get(0).getAddressLine(0) + " ";

        } catch (IOException e) {
            e.printStackTrace();
        }

        textSuhuAir = (TextView) findViewById(R.id.suhuair_nilai);
        textPH = (TextView) findViewById(R.id.ph_nilai);
        textTds = (TextView) findViewById(R.id.tds_nilai);
        textDhl = (TextView) findViewById(R.id.dhl_nilai);
        textPencemaran = (TextView) findViewById(R.id.pencemaran_nilai);
        textWaktu = (TextView) findViewById(R.id.date_nilai);
        textkategori = (TextView) findViewById(R.id.kategori_pencemaran);
        textSungai = (TextView) findViewById(R.id.sungai_nilai);
        textProbabilitas = (TextView) findViewById(R.id.probabilitas_nilai);
        textAddress = (TextView) findViewById(R.id.lokasi_nilai);

        textSuhuAir.setText(""+percobaan.getSuhu_air());
        textTds.setText(""+percobaan.getTds());
        textDhl.setText(""+percobaan.getDhl());
        textkategori.setText(""+percobaan.getKategori_pencemaran());
        textWaktu.setText(""+percobaan.getWaktu());
        textPencemaran.setText(""+percobaan.getIndeks_pencemaran());
        textPH.setText(""+percobaan.getPh());
        textSungai.setText(""+nama_sungai);
        textProbabilitas.setText(""+percobaan.getProbabilitas());

        textAddress.setText(""+lokasi);
    }



}
