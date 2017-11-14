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
    private int length, n=0;
    private Button btnBack;
    private EditText etSearchInhab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        etSearchInhab = (EditText) findViewById(R.id.etSearchInhab);
        btnBack = (Button) findViewById(R.id.btnBack);
        lvInhabitants = (ListView)findViewById(R.id.lvInhabitants);

        //search from list of habitants while writing
        /*etSearchInhab.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){

                }
                else{
                    searchPhrase(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() < length){

                    for(String item: inhabitants){
                        if(!item.toLowerCase().contains(s.toString().toLowerCase())){
                            ihList.remove(item);
                        }
                    }
                }
            }
        });*/

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataList().execute();
                //finish();
            }
        });

        lvInhabitants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                while(n<=position){
                    if(position==n){
                        Intent logger = new Intent(SearchActivity.this,ManageChosenActivity.class);
                        startActivity(logger);

                    }
                    n++;
                }
            }
        });
    }

    /*private void searchPhrase(String s) {
        for(String item:){
            if(!item.toLowerCase().contains(s.toString().toLowerCase())){
            }
        }
    }*/

    //FETCHING DATA FROM DATABASE AND PARSING IT TO LISTVIEW
    public class DataList extends AsyncTask<String, String, List<HabitantModel>> {

        private String data;

        @Override
        protected List<HabitantModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                //connecting the api
                URL url = new URL("http://detinhabwebapi.azurewebsites.net/api/habitant/");
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                //buffer to save the json data
                StringBuffer buffer = new StringBuffer();
                String line = "";

                //filling the buffer with data
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                data = buffer.toString();

                //making a json object from loaded data + parsing it to a HabitantModel
                JSONObject dataObject = new JSONObject(data);

                JSONArray habList = dataObject.getJSONArray("ArrayOfHabitiant");

                List<HabitantModel> habitantsList = new ArrayList<>();

                for(int i=0; i<habList.length(); i++){
                    JSONObject hab = habList.getJSONObject(i);
                    HabitantModel habitant = new HabitantModel();
                    habitant.setHabName(hab.getString("Name"));
                    habitant.setHabSurname(hab.getString("Surname"));
                    habitant.setRoomNumber(hab.getInt("RoomNumber"));
                    habitantsList.add(habitant);
                }

                return habitantsList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<HabitantModel> result) {
            super.onPostExecute(result);
            HabitantAdapter adapter = new HabitantAdapter(getApplicationContext(), R.layout.habitants, result);
            lvInhabitants.setAdapter(adapter);
        }

        public class HabitantAdapter extends ArrayAdapter {

            private int resource;
            private List<HabitantModel> habitantList;
            private LayoutInflater inflater;

            public HabitantAdapter(Context context, int resource, List<HabitantModel> objects) {
                super(context, resource, objects);
                habitantList = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            }

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null){
                    convertView = inflater.inflate(resource, null);
                }

                TextView habNameSurname;
                habNameSurname = (TextView)findViewById(R.id.tvHabitants);
                habNameSurname.setText(habitantList.get(position).getHabName()+" "+habitantList.get(position).getHabSurname());
                return convertView;
            }
        }
    }
}


