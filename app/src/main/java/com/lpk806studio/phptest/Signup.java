package com.lpk806studio.phptest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Signup extends AppCompatActivity {
    EditText username,password;
    Button btn_signup;
    private SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        session = new SessionHandler(getApplicationContext());


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });

    }

    private void postData() {
        String postParams =
                "Username=" + username.getText().toString() + "&" +
                        "Password=" + password.getText().toString();

        String urlString = "http://192.168.1.191/user/signup.php";

        MyAsynTask newTask = new MyAsynTask();
        newTask.execute(urlString,postParams);


    }
    private class MyAsynTask extends AsyncTask<String,Void,String > {

        @Override
        protected String doInBackground(String... strings) {
            String retStr = postHttpConnection(strings[0] , strings[1]);
            return retStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("===============");
            System.out.println(s);

            //go to next page
            session.loginUser(username.getText().toString());
            Intent signup = new Intent(Signup.this,Dashboard.class);
            startActivity(signup);

        }
    }

    private String postHttpConnection(String urlStr, String postParams) {
        String response = "";

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(postParams);

            dataOutputStream.flush();
            dataOutputStream.close();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = bufferedReader.readLine()) != null){
                    response += line;
                }
            }else{
                response = "";
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }
}
