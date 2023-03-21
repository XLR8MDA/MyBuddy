package com.example.mybuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADJ on 5/14/2017.
 */

/**
 * Created by ADJ on 5/14/2017.
 */
public class UpdateData extends AppCompatActivity {

    private Button update;
    private TextView textview, dateTV, timeTV;
    String name = "";


    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        update = (Button) findViewById(R.id.update_btn1);
        textview = findViewById(R.id.textview);
        dateTV = findViewById(R.id.date);
        timeTV = findViewById(R.id.time);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_TEXT, null);
        String time = sharedPreferences.getString(KEY_TIME, null);
        String date = sharedPreferences.getString(KEY_DATE, null);
        if (name != null) {
            textview.setText("" + name);
            dateTV.setText(""+date);
            timeTV.setText(""+time);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateDataActivity().execute();
            }
        });
    }

    class UpdateDataActivity extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(UpdateData.this);
            dialog.setTitle("Hey Wait Please..." + x);
            dialog.setMessage("I am working on your request");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
          String id = textview.getText().toString();
            JSONObject jsonObject = Controller.updateData(id, name);
            Log.i(Controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result = jsonObject.getString("result");

                }
            } catch (JSONException je) {
                Log.i(Controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}