package com.example.afip.cobalist;

/**
 * Created by afip on 5/25/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afip.cobalist.model.Pengkuran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemThreeFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button buttonMulaiPengukuran;
    ProgressDialog progressDialog;
    private List<Pengkuran> listPengukuran;
    private List<String> nama_sungai;
    private Adapter adapter;
    private SharedPrefManager sharedPrefManager;
    private AlertDialog alertDialog;
    int selectedSungai = 0;
    public static ItemThreeFragment newInstance() {
        ItemThreeFragment fragment = new ItemThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading data....");
        progressDialog.setMessage("Silahkan tunggu.....");
        sharedPrefManager = SharedPrefManager.getmInstance(getActivity());
        listPengukuran = new ArrayList<>();
        nama_sungai = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_item_three, container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPengukuran);
        buttonMulaiPengukuran = (Button) view.findViewById(R.id.button_pengkuran);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(listPengukuran,getActivity());

        buttonMulaiPengukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama_sungai.clear();
//                startActivity(new Intent(getActivity(),InputPercobaanActivity.class));
                loadSungai();
//                spinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,nama_sungai));
            }
        });


        loadPengukuran();
        return view;
    }

    private void loadSungai(){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Config.GET_SUNGAI,
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
                            View alertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pengukuran_baru,null);
                            final EditText editTitle = (EditText) alertView.findViewById(R.id.edit_title);
                            final EditText editDeskripsi = (EditText) alertView.findViewById(R.id.edit_deskripsi);
                            final Spinner spinnerSungai = (Spinner) alertView.findViewById(R.id.spinner_sungai);
                            spinnerSungai.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,nama_sungai));
                            spinnerSungai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    String nama = nama_sungai.get(i);
                                    selectedSungai = (i+1);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            new AlertDialog.Builder(getActivity())
                                    .setView(alertView)
                                    .setTitle("Pengukuran baru")
                                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String title = editTitle.getText().toString();
                                            String deskripsi = editDeskripsi.getText().toString();
                                            int id_sungai = (spinnerSungai.getSelectedItemPosition() + 1);
                                            kirimPengukuran(title,deskripsi,id_sungai);
                                        }
                                    })
                                    .show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void kirimPengukuran(final String title, final String deskripsi, final int id_sungai){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.INPUT_PENGUKURAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean(Config.PARAM_STATUS)){
                                progressDialog.dismiss();
                                loadPengukuran();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getActivity(),"Parsing data gagal",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(Config.PARAM_TITLE,title);
                map.put(Config.PARAM_ID_SUNGAI,""+id_sungai);
                map.put(Config.PARAM_DESKRIPSI,deskripsi);
                map.put(Config.PARAM_ID_USER,""+sharedPrefManager.getUserId());
                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void loadPengukuran(){
        listPengukuran.clear();
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.GET_PENGUKURAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean(Config.PARAM_STATUS)){
                                JSONArray jsonArray = jsonObject.getJSONArray(Config.PARAM_DATA);
                                for(int i=0;i<jsonArray.length();i++){
                                    listPengukuran.add(new Pengkuran(jsonArray.getJSONObject(i)));
                                }
                                recyclerView.setAdapter(adapter);
                            }
                            else  Toast.makeText(getActivity(),"Request failed",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),"Parsing data error",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error obtaining data...",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(Config.KEY_USER_ID,""+sharedPrefManager.getUserId());
                return map;
            }
        };

        Volley.newRequestQueue(getActivity()).add(request);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.Holder>{

        private List<Pengkuran> listPengukuran;
        private Context context;

        public Adapter(List<Pengkuran> listPengukuran, Context context){
            this.listPengukuran = listPengukuran;
            this.context = context;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_pengkuran,null,false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            holder.deskripsi.setText(listPengukuran.get(position).getDeskripsi());
            holder.title.setText(listPengukuran.get(position).getTitle());
            holder.waktu.setText(listPengukuran.get(position).getDate());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ListPercobaanActivity.class);
                    intent.putParcelableArrayListExtra("percobaan",listPengukuran.get(position).getListPercobaan());
                    intent.putExtra("nama_sungai",listPengukuran.get(position).getNama_sungai());
                    intent.putExtra("id_pengukuran",listPengukuran.get(position).getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listPengukuran.size();
        }

        class Holder extends RecyclerView.ViewHolder{
            TextView waktu;
            TextView title;
            TextView deskripsi;
            View view;

            public Holder(View itemView) {
                super(itemView);
                view = itemView;
                waktu = (TextView) itemView.findViewById(R.id.waktu);
                title = (TextView) itemView.findViewById(R.id.title);
                deskripsi = (TextView) itemView.findViewById(R.id.deskripsi);
            }
        }
    }

}