package com.example.castro.httptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_INTERNET = 1;
    TextView tvShow;
    String result;
    String myURL = "YOUR_URL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = (TextView) findViewById(R.id.tvshow);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            try {
                tvShow.setText(httpRequestResponse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    PERMISSION_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_INTERNET:
                try {
                    tvShow.setText(httpRequestResponse());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private String httpRequestResponse() throws IOException {
        //Instantiate new instance of our class
        HTTPRequest getRequest = new HTTPRequest();
        //Perform the doInBackground method, passing in our url
        try {
            return result = getRequest.execute(myURL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
