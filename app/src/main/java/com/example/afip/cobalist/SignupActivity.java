package com.example.afip.cobalist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    String Name, Password, Email;

    //_nameText, _emailText, _passwordText
    @InjectView(R.id.input_name) EditText nama_lengkap;
    @InjectView(R.id.input_username) EditText username;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nama_lengkap.getText().toString();
        String email = username.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        if(email.equals("username@gmail.com") && password.equals("pass123")){
            Toast.makeText(getBaseContext(), "sukses", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(SignupActivity.this, LoginActivity.class);
            SignupActivity.this.startActivity(in);
        }
        else if(email.equals("") || password.equals("")){
            Toast.makeText(getBaseContext(), "kosong", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getBaseContext(), "wrong", Toast.LENGTH_SHORT).show();
        }


//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }
    
    
    public void register_register(View v){
        Name = nama_lengkap.getText().toString();
        Password = _passwordText.getText().toString();
        Email = username.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name,Password,Email);
    }
    class BackGround extends AsyncTask<String, String, String>{
        protected String doInBackground(String... params){
            String nama_lengkap = params[0];
            String _passwordText = params[1];
            String username = params[2];
            String data ="";
            int tmp;

            //192.168.8.101
            try{
                URL url = new URL("http://localhost/cenggoserver/register");
                String urlParams = "name "+nama_lengkap+" password="+_passwordText+" email="+username;



                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while ((tmp=is.read())!=1){
                    data+= (char)tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;
            }
            catch (MalformedURLException e){
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
            catch (IOException e){
                e.printStackTrace();
                return "Exception : "+e.getMessage();
            }
        }


    }

//    protected void onPostExecute(String a){
//        if(s.equals("")){
//            s="Data save succesfully";
//        }
//        Toast.makeText(ctx, a, Toast.LENGTH_LONG.show);
//    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nama_lengkap.getText().toString();
        String email = username.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nama_lengkap.setError("at least 3 characters");
            valid = false;
        } else {
            nama_lengkap.setError(null);
        }

//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            username.setError("enter a valid email address");
//            valid = false;
        if (email.isEmpty() || name.length() < 3) {
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