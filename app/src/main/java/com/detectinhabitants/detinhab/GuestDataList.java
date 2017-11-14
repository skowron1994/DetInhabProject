package com.detectinhabitants.detinhab;


import android.os.AsyncTask;

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

public class GuestDataList extends AsyncTask<String, String, List<GuestModel>>{




    private String data;

    @Override
    protected List<GuestModel> doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            //connecting the api
            URL url = new URL("http://detinhabapi.aspnet.pl/api/guest/");
            connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            //buffer to save the json data
            StringBuffer buffer = new StringBuffer();
            String line = null;

            //filling the buffer with data
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            data = buffer.toString();

            //making a json object from loaded data + parsing it to a GuestModel
            JSONObject dataObject = new JSONObject(data);

            JSONArray gstList = dataObject.getJSONArray("ArrayOfHabitiant");

            List<GuestModel> guestList = new ArrayList<>();

            for(int i=0; i<gstList.length(); i++){
                JSONObject gst = gstList.getJSONObject(i);
                GuestModel guest = new GuestModel();
                guest.setGstName(gst.getString("Name"));
                guest.setGstSurname(gst.getString("Surname"));
                guestList.add(guest);
            }

            return guestList;


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
    protected void onPostExecute(List<GuestModel> result) {
        super.onPostExecute(result);

    }
}
