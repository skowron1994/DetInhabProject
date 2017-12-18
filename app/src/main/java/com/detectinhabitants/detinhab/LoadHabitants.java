package com.detectinhabitants.detinhab;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


//klasa wczytująca listę zapisanych w bazie mieszkańców
public class LoadHabitants extends AsyncTask<String, String, List<HabitantModel>> {

    String data;
    Activity activity;

    LoadHabitants(Activity activity) {
        this.activity = activity;
    }

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

                model.setHabID(arrObject.getString("Id"));
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
        SearchActivity.list = s;
        HabitantAdapter adapter = new HabitantAdapter(activity.getApplicationContext(), R.layout.habitants, s);
        SearchActivity.lvInhabitants.setAdapter(adapter);
    }


    public class HabitantAdapter extends ArrayAdapter {

        private List<HabitantModel> habitantList;
        private int resource;
        private LayoutInflater inflater;

        public HabitantAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<HabitantModel> objects) {
            super(context, resource, objects);

            habitantList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        public HabitantModel getItem(int id)
        {
            return habitantList.get(id);
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

            tvHabitantItem.setText(habitantList.get(position).getHabName());
            tvHabitantItem2.setText(habitantList.get(position).getHabSurname());
            tvHabitantItem3.setText(String.valueOf(habitantList.get(position).getRoomNumber()));
            return convertView;
        }
    }
}