package com.detectinhabitants.detinhab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddRoomActivity extends AppCompatActivity {


    public static ListView lvRooms;
    private Button btnCancel, btnAccept;
    private LinearLayout dlgAddRoom;
    private EditText etRoomNumber, etRoomLevel, etRoomBedCount;
    private ImageButton fbAddRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        lvRooms = findViewById(R.id.lvRoomList);
        fbAddRoom = findViewById(R.id.fbAddRoom);
        dlgAddRoom = findViewById(R.id.dlgAddRoom);
        dlgAddRoom.setVisibility(View.INVISIBLE);
        btnCancel = findViewById(R.id.btnCancelRoom);
        btnAccept = findViewById(R.id.btnAcceptRoom);
        etRoomNumber = findViewById(R.id.etRoomNumber);
        etRoomLevel = findViewById(R.id.etLevel);
        etRoomBedCount = findViewById(R.id.etBedCount);

        new LoadRooms(AddRoomActivity.this).execute();

        fbAddRoom.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(dlgAddRoom.getVisibility() == View.INVISIBLE){
                    dlgAddRoom.setVisibility(View.VISIBLE);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClearAllDialogControl();
                            dlgAddRoom.setVisibility(View.INVISIBLE);
                        }
                    });

                    btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            JSONObject roomData = new JSONObject();
                            try {
                                roomData.put("RoomNumber", etRoomBedCount.getText().toString());
                                roomData.put("RoomLevel", etRoomLevel.getText().toString());
                                roomData.put("RoomBedCount", etRoomBedCount.getText().toString());
                                String obj  = roomData.toString();
                                new AddRoomHandler(AddRoomActivity.this).execute(obj);
                                new LoadRooms(AddRoomActivity.this).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
                else
                {
                    ClearAllDialogControl();
                    dlgAddRoom.setVisibility(View.INVISIBLE);
                }
            }
        });



    }


    private void ClearAllDialogControl() {
        etRoomNumber.setText("");
        etRoomLevel.setText("");
        etRoomBedCount.setText("");
    }

}
