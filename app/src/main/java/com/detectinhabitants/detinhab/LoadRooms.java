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
import android.widget.Adapter;
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

public class LoadRooms extends AsyncTask<String, String, List<RoomModel>> {

    String data;
    Activity activity;

    LoadRooms(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<RoomModel> doInBackground(String... params) {
        BufferedReader reader = null;
        HttpURLConnection connect = null;

        try {
            URL url = new URL("http://detinhabapi.aspnet.pl/api/GetAllRooms");
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


            List roomList = new ArrayList<RoomModel>();
            for (int i = 0; i < habArray.length(); i++) {

                RoomModel model = new RoomModel();

                JSONObject arrObject = habArray.getJSONObject(i);

                model.setID(arrObject.getInt("Id"));
                model.setRoomNumber(arrObject.getInt("RoomNumber"));
                model.setLevel(arrObject.getInt("Level"));
                model.setBedCount(arrObject.getInt("BedCount"));
                roomList.add(model);
            }

            return roomList;

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
    protected void onPostExecute(List<RoomModel> s) {
        super.onPostExecute(s);
        if(s==null){
            String[] emptyList = new String[]{"brak informacji do wyświetlenia..."};
            ArrayAdapter<String> resultEmpty = new ArrayAdapter<String>(activity.getApplicationContext(), R.layout.empty, R.id.tvEmpty, emptyList);
            AddRoomActivity.lvRooms.setAdapter(resultEmpty);
        }
        else{
            RoomAdapter adapter = new RoomAdapter(activity.getApplicationContext(), R.layout.rooms, s);
            AddRoomActivity.lvRooms.setAdapter(adapter);
        }

    }


    public class RoomAdapter extends ArrayAdapter {

        private List<RoomModel> roomsList;
        private int resource;
        private LayoutInflater inflater;

        public RoomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<RoomModel> objects) {
            super(context, resource, objects);

            roomsList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }

            TextView tvRoomItem1, tvRoomItem2, tvRoomItem3, tvRoomItem4;
            tvRoomItem1= convertView.findViewById(R.id.tvRoomId);
            tvRoomItem2 = convertView.findViewById(R.id.tvRoomNumber);
            tvRoomItem3 = convertView.findViewById(R.id.tvLevel);
            tvRoomItem4 = convertView.findViewById(R.id.tvBedCount);

            tvRoomItem1.setText(roomsList.get(position).getID());
            tvRoomItem2.setText(roomsList.get(position).getRoomNumber());
            tvRoomItem3.setText(roomsList.get(position).getLevel());
            tvRoomItem4.setText(roomsList.get(position).getBedCount());

            return convertView;
        }
    }
}