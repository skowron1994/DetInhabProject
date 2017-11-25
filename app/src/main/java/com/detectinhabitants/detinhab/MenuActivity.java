package com.detectinhabitants.detinhab;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        Button btnInhManag = (Button) findViewById(R.id.btnInhManag);
        Button btnGstManag = (Button) findViewById(R.id.btnGstManag);
        Button btnLogout = (Button) findViewById(R.id.btnLogout);

        String welcome = AppHelper.UserContext.getUsrName();
        tvWelcome.setText("Witaj "+ welcome +"!");

        btnInhManag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inhabitant = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(inhabitant);

            }
        });

        btnGstManag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guest = new Intent(MenuActivity.this, SearchGuestActivity.class);
                startActivity(guest);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.checker = false;
                finish();
            }
        });

    }

    public class JsonTask extends AsyncTask<String, String, String> {

        String data;

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connect = null;

            try {
                URL url = new URL("http://detinhabapi.aspnet.pl/api/habitiant");
                connect = (HttpURLConnection) url.openConnection();
                connect.connect();

                InputStream stream = connect.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                StringBuffer buffer = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                data = buffer.toString();
                JSONArray habArray = new JSONArray(data);


                List habList = new ArrayList<HabitantModel>();
                for (int i = 0; i < habArray.length(); i++) {

                    HabitantModel model = new HabitantModel();

                    JSONObject arrObject = habArray.getJSONObject(i);

                    model.setHabName(arrObject.getString("Name"));
                    model.setHabSurname(arrObject.getString("Surname"));
                    model.setRoomNumber(arrObject.getInt("RoomNumber"));
                    habList.add(model);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connect != null) {
                    connect.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}

