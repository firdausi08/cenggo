package com.example.afip.cobalist;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.attr.country;
import static android.R.attr.path;
import static com.example.afip.cobalist.R.id.action_share;
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
    Context context;
    File imagePath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        receivelatlong = getIntent().getStringExtra("receivelatlong");
//        Toast.makeText(PercobaanActivity.this, ""+receivelatlong, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_pengukuran);

      //  FloatingActionButton dummy_share = (FloatingActionButton) findViewById(R.id.share);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPrefManager = SharedPrefManager.getmInstance(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Silahkan tunggu.....");

        percobaan = getIntent().getParcelableExtra("percobaan");
        nama_sungai = getIntent().getStringExtra("nama_sungai");
        latitude = percobaan.getLatitude();
        longitude = percobaan.getLongitude();
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);


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

//        action_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListPercobaanActivity.this,InputPercobaanActivity.class);
//                intent.putExtra("id_pengukuran",id_pengukuran);
//                startActivityForResult(intent,45);
//            }
//        });

//        dummy_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id== action_share){


//            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//            Uri screenshotUri = Uri.parse(String.valueOf(path));
//
//            sharingIntent.setType("image/png");
//            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//            startActivity(Intent.createChooser(sharingIntent, "Share image using"));



//            Bitmap bitmap = takeScreenshot();
//            saveBitmap(bitmap);
//            shareIt();
  //          takeScreenshot();




           return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    private void takeScreenshot() {
//        Date now = new Date();
//        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//
//        try {
//            // image naming and path  to include sd card  appending name you choose for file
//            String mPath = Environment.getExternalStorageDirectory().toString() + "/PICTURES/Screenshots/" + now + ".jpg";
//
//            // create bitmap screen capture
//            View v1 = getWindow().getDecorView().getRootView();
//            v1.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            v1.setDrawingCacheEnabled(false);
//            File imageFile = new File(mPath);
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//            outputStream.flush();
//            outputStream.close();
//
//
//            MediaScannerConnection.scanFile(this,
//                    new String[]{imageFile.toString()}, null,
//                    new MediaScannerConnection.OnScanCompletedListener() {
//                        public void onScanCompleted(String path, Uri uri) {
//                            Log.i("ExternalStorage", "Scanned " + path + ":");
//                            Log.i("ExternalStorage", "-> uri=" + uri);
//                        }
//                    });
//
//            openScreenshot(imageFile);
//        } catch (Throwable e) {
//            // Several error may come out with file handling or OOM
//            e.printStackTrace();
//        }
//    }
//
//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//    }



//    public Bitmap takeScreenshot() {
//        View rootView = findViewById(android.R.id.content).getRootView();
//        rootView.setDrawingCacheEnabled(true);
//        return rootView.getDrawingCache();
//    }
//
//    public void saveBitmap(Bitmap bitmap) {
//        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream(imagePath);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            Log.e("GREC", e.getMessage(), e);
//        } catch (IOException e) {
//            Log.e("GREC", e.getMessage(), e);
//        }
//    }
//
//    private void shareIt() {
//        Uri uri = Uri.fromFile(imagePath);
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("image/*");
//        String shareBody = "In Tweecher, My highest score with screen shot";
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Tweecher score");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//    }
//
//    public static Bitmap getScreenShot(View view) {
//        View screenView = view.getRootView();
//        screenView.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//        screenView.setDrawingCacheEnabled(false);
//        return bitmap;
//    }
//
//    public static void store(Bitmap bm, String fileName){
//        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
//        File dir = new File(dirPath);
//        if(!dir.exists())
//            dir.mkdirs();
//        File file = new File(dirPath, fileName);
//        try {
//            FileOutputStream fOut = new FileOutputStream(file);
//            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
//            fOut.flush();
//            fOut.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



}
