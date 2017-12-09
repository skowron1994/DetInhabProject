package com.detectinhabitants.detinhab;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ListView lvInhabitants;
    private int length, n = 0;
    private Button btnBack;
    private EditText etSearchInhab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        etSearchInhab = (EditText) findViewById(R.id.etSearchInhab);
        btnBack = (Button) findViewById(R.id.btnBack);
        lvInhabitants = (ListView) findViewById(R.id.lvInhabitants);
        etSearchInhab.setSelected(false);
        new JsonTask().execute();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvInhabitants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                while (n <= position) {
                    if (position == n) {
                        Intent logger = new Intent(SearchActivity.this, ManageChosenActivity.class);
                        logger.putExtra("id", n+1);
                        startActivity(logger);

                    }
                    n++;
                }
                n = 0;
            }
        });
    }

    //FETCHING DATA FROM DATABASE AND PARSING IT TO LISTVIEW
    public class JsonTask extends AsyncTask<String, String, List<HabitantModel>> {

        String data;

        @Override
        protected List<HabitantModel> doInBackground(String... params) {
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

                return habList;

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
        protected void onPostExecute(List<HabitantModel> s) {
            super.onPostExecute(s);
            HabitantAdapter adapter = new HabitantAdapter(getApplicationContext(), R.layout.habitants, s);
            lvInhabitants.setAdapter(adapter);
        }
    }

    public class HabitantAdapter extends ArrayAdapter {

        private List<HabitantModel> habitantList;
        private int resource;
        private LayoutInflater inflater;

        public HabitantAdapter (@NonNull Context context, @LayoutRes int resource, @NonNull List<HabitantModel> objects) {
            super(context, resource, objects);

            habitantList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }

            TextView tvHabitantItem, tvHabitantItem2, tvHabitantItem3;
            tvHabitantItem = convertView.findViewById(R.id.tvHabitantName);
            tvHabitantItem2 = convertView.findViewById(R.id.tvHabitantSurname);
            tvHabitantItem3 = convertView.findViewById(R.id.tvHabitantRoom);

            tvHabitantItem.setText( habitantList.get(position).getHabName());
            tvHabitantItem2.setText(habitantList.get(position).getHabSurname());
            tvHabitantItem3.setText(String.valueOf(habitantList.get(position).getRoomNumber()));

            return convertView;
        }
    }
}
