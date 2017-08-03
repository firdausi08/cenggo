package com.example.afip.cobalist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.afip.cobalist.model.Percobaan;

import java.util.ArrayList;
import java.util.List;

public class ListPercobaanActivity extends AppCompatActivity {
    private Button buttonMulaiPengukuran;
    private ArrayList<Percobaan> listPercobaan;
    private ListPercobaanActivity.Adapter adapter;
    private SharedPrefManager sharedPrefManager;
    RecyclerView recyclerView;
    private String nama_sungai;
    private Button button;
    private FloatingActionButton fab;
    private int id_pengukuran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_percobaan_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id_pengukuran = getIntent().getIntExtra("id_pengukuran",0);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        sharedPrefManager = SharedPrefManager.getmInstance(this);
        listPercobaan = getIntent().getParcelableArrayListExtra("percobaan");
        nama_sungai = getIntent().getStringExtra("nama_sungai");
        initView();

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.item_percobaan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   button = (Button) findViewById(R.id.fab);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPercobaanActivity.this,InputPercobaanActivity.class);
                intent.putExtra("id_pengukuran",id_pengukuran);
                startActivityForResult(intent,45);
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListPercobaanActivity.this,InputPercobaanActivity.class);
//                startActivity(intent);
//            }
//        });
        adapter = new Adapter(listPercobaan,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 45 && resultCode == Activity.RESULT_OK && adapter!=null){
            Percobaan percobaan = data.getParcelableExtra("percobaan");
            adapter.addItem(percobaan);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Adapter extends RecyclerView.Adapter<ListPercobaanActivity.Adapter.Holder>{

        private List<Percobaan> listPercobaan;
        private Context context;

        public Adapter(ArrayList<Percobaan> listPengukuran, Context context){
            this.listPercobaan = listPengukuran;
            this.context = context;
        }

        public void addItem(Percobaan percobaan){
            listPercobaan.add(percobaan);
            notifyDataSetChanged();
        }

        @Override
        public ListPercobaanActivity.Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_list_percobaan,null,false);
            return new ListPercobaanActivity.Adapter.Holder(view);
        }

        @Override
        public void onBindViewHolder(ListPercobaanActivity.Adapter.Holder holder, final int position) {
            holder.deskripsi.setText(listPercobaan.get(position).getDeskripsi());
            holder.title.setText(listPercobaan.get(position).getTitle());
            holder.waktu.setText(listPercobaan.get(position).getWaktu());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListPercobaanActivity.this,PercobaanActivity.class);
                    intent.putExtra("percobaan", listPercobaan.get(position));
                    intent.putExtra("nama_sungai",nama_sungai);
                 //   intent.putExtra("lokasi",lokasi);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listPercobaan.size();
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
