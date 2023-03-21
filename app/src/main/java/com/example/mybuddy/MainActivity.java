package com.example.mybuddy;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, roll,purpose;
    Button button;
    TextView textview;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        roll = findViewById(R.id.roll);
        button = findViewById(R.id.button);
        textview = findViewById(R.id.textview);
        purpose=findViewById(R.id.purpose);

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading.....");

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudentData();
                pd.show();
            }
        });
    }


    public void addStudentData() {
        String sname = name.getText().toString();
        String sphone = phone.getText().toString();
        String sroll = roll.getText().toString();
        String spur= purpose.getText().toString();
        String stxt = (getRandomString(6));
        textview.setText("" + stxt);

        Calendar ForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String sdate = currentDate.format(ForDate.getTime());

        Calendar ForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss");
        String stime = currentTime.format(ForTime.getTime());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TEXT, stxt);
        editor.putString(KEY_TIME, stime);
        editor.putString(KEY_DATE, sdate);
        editor.apply();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwufEEQsNJX3AdtGRwFIKeuzqTVowgqcwVTYLDUei-Zr9t_lclJ-LidMpn12xaAyeU_/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(MainActivity.this, UpdateData.class);
                intent.putExtra("stxt", stxt);
                startActivity(intent);
                pd.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "addStudent");
                params.put("vDate", sdate);
                params.put("vRoll", sroll);
                params.put("vName", sname);
                params.put("vPhone", sphone);
                params.put("vPur",spur);
                params.put("vTxt", stxt);

                return params;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    // Fetch the stored data in onResume()
    // Because this is what will be called
    // when the app opens again

    // Store the data in the SharedPreference
    // in the onPause() method
    // When the user closes the application
    // onPause() will be called
    // and data will be stored
    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        Calendar ForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String sdate = currentDate.format(ForDate.getTime());

        Calendar ForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss");
        String stime = currentTime.format(ForTime.getTime());


        // write all the data entered by the user in SharedPreference and apply
        String name = textview.getText().toString();
        myEdit.putString("name", name);
        myEdit.putString("sdate", sdate);
        myEdit.putString("stime", stime);
        myEdit.apply();
    }


    public static String getRandomString(int i) {
        final String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i--;
        }
        return result.toString();
    }
}