package com.example.afip.cobalist.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.afip.cobalist.R;

import butterknife.InjectView;

public class UserGuide extends AppCompatActivity {

    @InjectView(R.id.cara_aplikasi) Button cara_aplikasi;
    @InjectView(R.id.cara_sensor) Button cara_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_user_guide, container, false);
        LinearLayout aplikasi = (LinearLayout) v.findViewById(R.id.cara_aplikasi);
        LinearLayout sensor = (LinearLayout) v.findViewById(R.id.cara_sensor);

        aplikasi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserGuide.this, Cara_aplikasi.class);
                startActivity(intent);
            }
        });
        sensor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(UserGuide.this, Cara_sensor.class);
                startActivity(intent2);

            }
        });

        return v;
    }
}
