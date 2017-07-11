package com.example.afip.cobalist;

/**
 * Created by afip on 5/25/2017.
 */

import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import com.example.afip.cobalist.model.Percobaan;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ItemTwoFragment extends Fragment implements Spinner.OnItemSelectedListener{

    @InjectView(R.id.send) Button startMeasuring;

    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    private Spinner spinner;
    private EditText et_judul;
    private EditText et_deskripsi;

    private Context context;
    private ArrayList<String> nama_sungai;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_two, container, false);
        context = v.getContext();
        spinner = (Spinner) v.findViewById(R.id.spinner);
        et_judul = (EditText) v.findViewById(R.id.et_judul);
        et_deskripsi = (EditText) v.findViewById(R.id.et_deskripsi);
        Button start = (Button)v.findViewById(R.id.send);
        /*
        * Mengekseksui class RunBackground()
        * */
        new RunBackground().execute();

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PengukuranActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void initview(){

    }

    public void setSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nama_sungai);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String nama = nama_sungai.get(position);
                Toast.makeText(context, nama+" dipilih.", Toast.LENGTH_SHORT).show();
                /*String title = et_judul.getText().toString();
                String deskripsi = et_deskripsi.getText().toString();

                Percobaan percobaan = new Percobaan();
                percobaan.setTitle(title);
                percobaan.setDeskripsi(deskripsi);
                percobaan.setId_user(1);
                percobaan.setId_sungai(position+1);*/

                /*Toast.makeText(view.getContext(), "Selected "+ adapter.getItem(i), Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), " Title : "+ et_judul.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), "Deskripsi : "+ et_deskripsi.getText().toString(), Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            setSpinner();
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
}
