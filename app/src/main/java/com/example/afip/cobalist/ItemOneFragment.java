package com.example.afip.cobalist;

/**
 * Created by afip on 5/25/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afip.cobalist.model.Percobaan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemOneFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private KmlLayer layer;
    private List<Percobaan> listPercobaan;
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;
    private Map<Marker,Percobaan> markers;
    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Silahkan tunggu...");
        listPercobaan = new ArrayList<>();
        markers = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_item_one, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    private void loadMarkers(){
        progressDialog.show();
        listPercobaan.clear();
        markers.clear();
        StringRequest request = new StringRequest(Request.Method.GET, Config.GET_FULL_PENGUKURAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean(Config.PARAM_STATUS)){
                        JSONArray data = jsonObject.getJSONArray(Config.PARAM_DATA);
                        for(int i=0;i<data.length();i++){
                            JSONArray dataArrayPercobaan = data.getJSONObject(i).getJSONArray(Config.PARAM_DATA_PERCOBAAN);
                            for (int j=0;j<dataArrayPercobaan.length();j++){
                                final Percobaan percobaan = new Percobaan(dataArrayPercobaan.getJSONObject(j));
                                if(mMap!=null){
                                    LatLng latlng = new LatLng(Double.valueOf(percobaan.getLatitude()),Double.valueOf(percobaan.getLongitude()));
                                    Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).title(percobaan.getTitle()).snippet("Level Pencemaran : "+percobaan.getIndeks_pencemaran()+
                                            "\nKategori Pencemaran : "+percobaan.getKategori_pencemaran()+"\nProbabilitas Eceng Gondok : "+percobaan.getProbabilitas())
                                    .icon(BitmapDescriptorFactory.defaultMarker(120f-((float)percobaan.getIndeks_pencemaran()*12f))));
//
                                    markers.put(marker,percobaan);
                                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                        @Override
                                        public View getInfoWindow(Marker arg0) {
                                            return null;
                                        }

                                        @Override
                                        public View getInfoContents(Marker marker) {

                                            Context context = getActivity();
                                           // String indeks = String.valueOf(percobaan.getIndeks_pencemaran());
                                          //  Toast.makeText(getActivity(),"Error memparsing data"+indeks,Toast.LENGTH_SHORT).show();
                                            LinearLayout info = new LinearLayout(context);

                                            info.setOrientation(LinearLayout.VERTICAL);

                                            TextView title = new TextView(context);
                                            title.setTextColor(Color.BLACK);
                                            title.setGravity(Gravity.CENTER);
                                            title.setTypeface(null, Typeface.BOLD);
                                            title.setText(marker.getTitle());

                                            TextView snippet = new TextView(context);
                                            snippet.setTextColor(Color.GRAY);
                                            snippet.setText(marker.getSnippet());
//
                                            info.addView(title);
                                            info.addView(snippet);
//
                                            return info;
                                        }
                                    });

                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(getActivity(),"Gagal koneksi ke server",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Error memparsing data",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Koneksi bermasalah",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(getActivity()).add(request);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-7.264888, 112.757657);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13f));

        InputStream kmlInputStream = getResources().openRawResource(getResources().getIdentifier("sungai","raw",getActivity().getPackageName()));
        try {
            layer = new KmlLayer(mMap,kmlInputStream,getActivity());
            layer.addLayerToMap();

            loadMarkers();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}