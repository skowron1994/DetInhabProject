package com.detectinhabitants.detinhab;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ManageChosenActivity extends AppCompatActivity {

    public static TextView habName, habSurname, habRoom, habCounsuelor, habContact, habReturnTime, habLastGuest, habSex, habAge, currentStatus;
    private ImageButton statusChange;
    private ListView lvStatusChange;
    public static HabitantModel habModel;
    String idChosen;
    private int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_click_hbt);
        getSupportActionBar().hide();
        habName = (TextView) findViewById(R.id.tvHabName);
        habSurname = (TextView) findViewById(R.id.tvHabSurname);
        habAge = (TextView) findViewById(R.id.tvAge);
        habRoom = (TextView) findViewById(R.id.tvRoomNumber);
        habCounsuelor = (TextView) findViewById(R.id.tvCounsuelor);
        habContact = (TextView) findViewById(R.id.tvCounContact);
        habReturnTime = (TextView) findViewById(R.id.tvMaxReturnTime);
        habLastGuest = (TextView) findViewById(R.id.tvLastGuest);
        habSex = (TextView) findViewById(R.id.tvAdnotations);
        currentStatus = (TextView)findViewById(R.id.tvCurrentStatus);
        statusChange = (ImageButton)findViewById(R.id.fbChangeStatus);
        lvStatusChange = (ListView)findViewById(R.id.lvStatusList);
        Bundle intent = getIntent().getExtras();
        idChosen = String.valueOf(intent.getInt("id"));
        new HabitantHandler(ManageChosenActivity.this).execute(idChosen);
        lvStatusChange.setVisibility(View.INVISIBLE);

        statusChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvStatusChange.setVisibility(View.VISIBLE);
            }
        });


        //statusy
        String[] statusList = {"Wybierz status:", "W pokoju", "Opuścił placówkę", "Wyjechał na weekend", "Na zajęciach pozalekcyjnych", "Spóźnia się"};
        ArrayAdapter<String> status = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusList);
        lvStatusChange.setAdapter(status);
        lvStatusChange.setSelection(0);

        lvStatusChange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                while (n <= position) {
                    if (position == n) {

                        switch((int)position){

                            case 1:
                                new HabitantHandler(ManageChosenActivity.this).execute(idChosen);
                                new StatusChangeHandler(ManageChosenActivity.this).execute(position, AppHelper.HabitantContext.getHabStatus(), Integer.valueOf(idChosen));
                                new HabitantHandler(ManageChosenActivity.this).execute(idChosen);
                                break;
                            case 2:
                                habModel.setHabStatus(2);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 3:
                                habModel.setHabStatus(3);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 4:
                                habModel.setHabStatus(4);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 5:
                                habModel.setHabStatus(5);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                        }
                        lvStatusChange.setVisibility(View.INVISIBLE);
                    }
                    n++;
                }
                n = 0;

            }
        });


    }

    private void changeStatus(){
        if(habModel.getHabStatus()==1){

            currentStatus.setText("W pokoju");
        }
        else if(habModel.getHabStatus()==2){

            currentStatus.setText("Poza placówką");
        }
        else if(habModel.getHabStatus()==3){

            currentStatus.setText("Wyjechał na weekend");
        }
        else if(habModel.getHabStatus()==4){

            currentStatus.setText("Na zajęciach pozalekcyjnych");
        }
        else if(habModel.getHabStatus()==5){

            currentStatus.setText("Spóźnia się");
        }

    }
}
