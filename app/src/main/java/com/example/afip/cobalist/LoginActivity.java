package com.example.afip.cobalist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;

import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.example.afip.cobalist.R.layout.login;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_username) EditText username;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    public static final String LOGIN_URL = "http://192.168.8.101/cenggoserver/login";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
      //  _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String Username = username.getText().toString().trim();
        final String password = _passwordText.getText().toString().trim();

        // TODO: Implement your own authentication logic here.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String login = jsonObject.getString("login");
                           // if(Util.isRequestSucces(jsonObject)) {
                            progressDialog.dismiss();
                            if (login.equals("true")){
                                /*boolean isSuccess = jsonObject.getBoolean("login");
                                Log.d("SMD", "Masuk onResponse");
                                if (isSuccess) {
                                    JSONObject data = jsonObject.getJSONObject("login");
                                    int id = jsonObject.getInt("login");
                                    //String password = data.getString(login.API_PARAM_PASSWORD);
                                    String password = jsonObject.getString("login");
                                    String username = jsonObject.getString("login");
//                                    manager.userLogin(id,username,password);
//                                    if(manager.getToken()!=null &&  !manager.getToken().equals(""))
//                                        reRegisterDevice();
                                } */
                                Toast.makeText(LoginActivity.this, "Sukses login", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                Log.d(TAG, "Login3");
                                LoginActivity.this.startActivity(in);
                            } else {
                                Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Terjadi kesalahan, silahkan coba lagi",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

//                        if(response.trim().equals("true")){
//                          //  Toast.makeText(getBaseContext(), "sukses", Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "Login1");
//                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
//                            Log.d(TAG, "Login2");
//                            in.putExtra(KEY_USERNAME, Username);
//                            Log.d(TAG, "Login3");
//                           // LoginActivity.this.startActivity(in);
//                            startActivity(in);
//                            Log.d(TAG, "Login4");
//                        }else{
//                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
//                            Log.d(TAG, "Login5");
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,Username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

//        if(Username.equals("username") && password.equals("12345")){
//            Toast.makeText(getBaseContext(), "sukses", Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(LoginActivity.this, MainActivity.class);
//            LoginActivity.this.startActivity(in);
//        }
//        else if(Username.equals("") || password.equals("")){
//            Toast.makeText(getBaseContext(), "kosong", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(getBaseContext(), "wrong", Toast.LENGTH_SHORT).show();
//        }
//
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String,String> map = new HashMap<String,String>();
//            map.put(KEY_USERNAME,Username);
//            map.put(KEY_PASSWORD,password);
//            return map;
//        }

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }
    public void onClick(View v) {
        login();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String Username = username.getText().toString();
        String password = _passwordText.getText().toString();

//        if (Username.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Username).matches()) {
//            username.setError("enter a valid Username address");
//            valid = false;
        if (Username.isEmpty() || username.length() < 3) {
            username.setError("at least 3 characters");
            valid = false;
        } else {
            username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
