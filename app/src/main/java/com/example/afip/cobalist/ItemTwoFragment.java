package com.example.afip.cobalist;

/**
 * Created by afip on 5/25/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import at.markushi.ui.CircleButton;

import static com.google.android.gms.wearable.DataMap.TAG;

public class ItemTwoFragment extends Fragment implements Spinner.OnItemSelectedListener, OnMapReadyCallback{

    GoogleMap googleMap;
    private Map<String, String> sungai;

    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    private EditText Title;
    private EditText Deskripsi;

    private Context context;
    private ArrayList<String> nama_sungai;
    public static final String TITLE="title";
    public static final String DESKRIPSI = "deskripsi";
    public static final String ID_SUNGAI = "id_sungai";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ID_USER = "id_user";

    private int selectedSungai;

    SharedPrefManager sharedPrefManager;
    CircleButton start;
    Button btnSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_two, container, false);
        context = v.getContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Title = (EditText) v.findViewById(R.id.et_judul);
        Deskripsi = (EditText) v.findViewById(R.id.et_deskripsi);
        start = (CircleButton) v.findViewById(R.id.location);
        btnSend = (Button) v.findViewById(R.id.send);
        btnSend.setEnabled(false);


        sungai = new HashMap<>();

        sharedPrefManager = SharedPrefManager.getmInstance(getActivity());
        /*
        * Mengekseksui class RunBackground()
        * */
        new RunBackground().execute();

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                senddata();
//                Intent intent = new Intent(getActivity(), PercobaanActivity.class);
//                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senddata();
            }
        });

        return v;
    }

    private void initview(){

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }


    class RunBackground extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        /*
        * Melakukan sesuatu sebelum doInBackground()
        * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(false);
            progressDialog.setTitle("Sinkronisasi");
            progressDialog.setMessage("Tunggu sebentar...");
            progressDialog.show();

            /*
            * Inisialisasi arrayList nama_sungai
            * */
            nama_sungai = new ArrayList<>();
        }

        /*
        * Melakukan sesuatu sesudah doInBackground()
        * */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            btnSend.setEnabled(true);
            /*if (s.equals("true")) {
            } else {
                Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
            }*/
        }

        /*
        * Melakukan proses belakang layar (background)
        * */
        @Override
        protected String doInBackground(String... strings) {
            //Creating a string request
            StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for(int i = 0; i< jsonArray.length(); i++){
                                    jsonObject = jsonArray.getJSONObject(i);
                                    String id = jsonObject.getString("id_sungai");
                                    String nama = jsonObject.getString("nama_sungai");

                                    sungai.put(id,nama);


                                    Log.i("Item-Two-Fragment", "Data sungai ke-"+i+" id_sungai -> "+id+" nama_sungai -> "+nama);
                                    nama_sungai.add(nama);
                                }
                                onPostExecute("true");
                            } catch (JSONException e) {
                                onPostExecute("false");
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            onPostExecute("false");
                        }
                    });

            //Creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            //Adding request to the queue
            requestQueue.add(stringRequest);
            return null;
        }
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for(int i = 0; i< jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);
                                String id_sungai = jsonObject.getString("id_sungai");
                                String nama_sungai = jsonObject.getString("nama_sungai");
                                /*
                                * Masukkan nama sungai ke dalam array nama_sungai
                                * */

                                Log.i("Item-Two-Fragment", "Data sungai ke-"+i+" id_sungai -> "+id_sungai+" nama_sungai -> "+nama_sungai);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void senddata() {
        Log.d(TAG, "Send Data");

        final String title = Title.getText().toString().trim();
        final String deskripsi = Deskripsi.getText().toString().trim();
        LatLng latLng = googleMap.getCameraPosition().target;
        final String latitude = ""+latLng.latitude;
        final String longitude = ""+latLng.longitude;

//
//       // final String id_sungai = Id_sungai.getText().toString().trim();
//
//        // TODO: Implement your own authentication logic here.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ISIDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "respon isidata");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String isidata = jsonObject.getString("isidata");
                            // if(Util.isRequestSucces(jsonObject)) {
                           // progressDialog.dismiss();
                            if (isidata.equals("true")) {
                                Toast.makeText(getActivity(), "Your data save sucessfully", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(getActivity(), PercobaanActivity.class);
                           //     Log.d(TAG, "Login3");
                                getActivity().startActivity(in);
                            } else {
                                Toast.makeText(getActivity(), "What's Happening ??", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(getActivity(), PercobaanActivity.class);
                                //     Log.d(TAG, "Login3");
                                getActivity().startActivity(in);

                            }
                        } catch (JSONException e) {
                           // progressDialog.dismiss();
                            Toast.makeText(getContext(), "There's some issues, please try again", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(TITLE, title);
                map.put(DESKRIPSI, deskripsi);
                map.put(ID_SUNGAI, ""+selectedSungai);
                map.put(ID_USER, ""+sharedPrefManager.getUserId());
                map.put(LATITUDE, latitude);
                map.put(LONGITUDE, longitude);
                return map;
            }
        };
//        Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}
