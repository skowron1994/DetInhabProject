package com.detectinhabitants.detinhab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddHabitantActivity extends AppCompatActivity {

    private Button btnAddHabitant;
    private EditText etNewHabName, etNewHabSurname, etNewHabAge, etNewHabAddress, etNewHabHomeNubmer, etNewHabFlatNumber, etNewHabRoomNumber,
    etnewHabConsuelor, etNewHabConsuelorContact, etZipCode, etMaxTime, etNewHabCountry;
    private String name, surname, age, street, country, roomNumber, consuelor, consContact, zipCode, returnTime, town, homeNumber;
    private int genderId;
    private RadioButton maleChk, femaleChk;
    private RadioGroup rGroup;
    public JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habitant);
        btnAddHabitant = findViewById(R.id.btnAddHabitant);
        etNewHabName = findViewById(R.id.etNewHabitantName);
        etNewHabSurname = findViewById(R.id.etNewHabitantSurname);
        etNewHabAge = findViewById(R.id.etNewHabitantAge);
        etNewHabAddress = findViewById(R.id.etNewHabitantAddress);
        etNewHabHomeNubmer = findViewById(R.id.etNewHabitantHomeNumber);
        etNewHabFlatNumber = findViewById(R.id.etNewHabitantFlatNumber);
        etNewHabRoomNumber = findViewById(R.id.etNewHabitantRoomNumber);
        etnewHabConsuelor = findViewById(R.id.etNewHabitantConsuelor);
        etNewHabConsuelorContact = findViewById(R.id.etNewHabitantConsuelorContact);
        etZipCode = findViewById(R.id.etNewHabitantZipcode);
        etMaxTime = findViewById(R.id.etNewHabitantMaxTime);
        etNewHabCountry = findViewById(R.id.etNewHabitantCountry);
        maleChk = findViewById(R.id.checkboxMale);
        femaleChk = findViewById(R.id.checkboxFemale);
        rGroup = findViewById(R.id.genderChoose);
        jsonData = new JSONObject();

        btnAddHabitant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEmpty(etNewHabName)){
                    Toast.makeText(getApplicationContext(),"Nie uzupełniono pola Imię.",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(etNewHabName.getText().toString().trim().length() < 3){
                        Toast.makeText(getApplicationContext(),"Pole Imię musi posiadać przynajmniej 3 znaki.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        name = etNewHabName.getText().toString();

                        if(isEmpty(etNewHabSurname)){
                            Toast.makeText(getApplicationContext(),"Nie uzupełniono pola Nazwisko.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(etNewHabSurname.getText().toString().trim().length() < 3){
                                Toast.makeText(getApplicationContext(),"Pole nazwisko musi posiadać przynajmniej 3 znaki.",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                surname = etNewHabSurname.getText().toString();

                                if(!maleChk.isChecked() && !femaleChk.isChecked()){
                                    Toast.makeText(getApplicationContext(),"Płeć musi zostać wybrana.",Toast.LENGTH_SHORT).show();
                                }
                                else if(maleChk.isChecked() && femaleChk.isChecked()){
                                    Toast.makeText(getApplicationContext(),"Należy wybrać tylko jedną płeć.",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(maleChk.isChecked() && !femaleChk.isChecked()){
                                        genderId = 1;
                                    }
                                    else if(!maleChk.isChecked() && femaleChk.isChecked()){
                                        genderId = 2;
                                    }

                                    if(isEmpty(etNewHabAge)){
                                        Toast.makeText(getApplicationContext(),"Nie uzupełniono pola Data urodzenia.",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        if(!isValid(etNewHabAge.getText().toString())){
                                            Toast.makeText(getApplicationContext(),"Niepoprawna data urodzenia. Format to dzień.miesiąc.rok",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            age = etNewHabAge.getText().toString();

                                            if(isEmpty(etNewHabCountry)){
                                                Toast.makeText(getApplicationContext(),"Nie wypełniono pola Państwo.",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                if(etNewHabCountry.getText().toString().trim().length()<3){
                                                    Toast.makeText(getApplicationContext(),"Pole Państwo musi zawierać przynajmniej 3 znaki.",Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    country = etNewHabCountry.getText().toString();

                                                    if(isEmpty(etNewHabAddress)){
                                                        Toast.makeText(getApplicationContext(),"Nie podano ulicy.",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        if(etNewHabAddress.getText().toString().trim().length()<3){
                                                            Toast.makeText(getApplicationContext(),"Pole Ulica musi się składać z przynajmniej 3 znaków.",Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            street = etNewHabAddress.getText().toString();

                                                            if(isEmpty(etNewHabHomeNubmer)){
                                                                Toast.makeText(getApplicationContext(),"Nie podano numeru domu.",Toast.LENGTH_SHORT).show();
                                                            }
                                                            else{
                                                                homeNumber = etNewHabHomeNubmer.getText().toString();

                                                                if(isEmpty(etNewHabFlatNumber)){
                                                                    Toast.makeText(getApplicationContext(),"Nie wypełniono pola miasto.",Toast.LENGTH_SHORT).show();
                                                                }
                                                                else{
                                                                    if(etNewHabFlatNumber.getText().toString().trim().length()<3){
                                                                        Toast.makeText(getApplicationContext(),"Miasto musi się składać z przynajmniej 3 liter.",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else{
                                                                        town = etNewHabFlatNumber.getText().toString();

                                                                        if(isEmpty(etZipCode)){
                                                                            Toast.makeText(getApplicationContext(),"Podaj kod pocztowy",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        else{
                                                                            if(etZipCode.getText().toString().trim().length()<6 || etZipCode.getText().toString().trim().length()>6){
                                                                                Toast.makeText(getApplicationContext(),"Kod pocztowy musi składać się z 5 cyfr",Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            else{
                                                                                zipCode = etZipCode.getText().toString();

                                                                                if(isEmpty(etnewHabConsuelor)){
                                                                                    Toast.makeText(getApplicationContext(),"Należy wypełnić pole Prawny opiekun.",Toast.LENGTH_SHORT).show();
                                                                                }
                                                                                else{
                                                                                    if(etnewHabConsuelor.getText().toString().trim().length()<3){
                                                                                        Toast.makeText(getApplicationContext(),"Pole Prawny opiekun musi posiadać przynajmniej 3 znaki.",Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                    else{
                                                                                        consuelor = etnewHabConsuelor.getText().toString();

                                                                                        if(isEmpty(etNewHabConsuelorContact)){
                                                                                            Toast.makeText(getApplicationContext(),"Wypełnij pole Kontakt do opiekuna.",Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        else{
                                                                                            if(etNewHabConsuelorContact.getText().toString().trim().length()!=9){
                                                                                                Toast.makeText(getApplicationContext(),"Telefon musi składać się z 9 cyfr.",Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                            else{
                                                                                                consContact = etNewHabConsuelorContact.getText().toString();

                                                                                                if(isEmpty(etNewHabRoomNumber)){
                                                                                                    Toast.makeText(getApplicationContext(),"Należy wypełnić pole Numer pokoju.",Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                                else{
                                                                                                    roomNumber = etNewHabRoomNumber.getText().toString();

                                                                                                    if(isEmpty(etMaxTime)){
                                                                                                        Toast.makeText(getApplicationContext(),"Należy wypełnić pole Maksymalna godzina powrotu.",Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                    else{
                                                                                                        returnTime = etMaxTime.getText().toString();
                                                                                                        try {
                                                                                                            jsonData.put("Name", name);
                                                                                                            jsonData.put("Surname", surname);
                                                                                                            jsonData.put("Sex", genderId);
                                                                                                            jsonData.put("Birthday", age);
                                                                                                            jsonData.put("Country", country);
                                                                                                            jsonData.put("Street", street);
                                                                                                            jsonData.put("HouseNumber", homeNumber);
                                                                                                            jsonData.put("Town", town);
                                                                                                            jsonData.put("ZipCode", zipCode);
                                                                                                            jsonData.put("RoomId", roomNumber);
                                                                                                            jsonData.put("ConsuelorName", consuelor);
                                                                                                            jsonData.put("ConsuelorContact", consContact);
                                                                                                            jsonData.put("MaxTime", Integer.parseInt(returnTime));
                                                                                                            jsonData.put("BinaryData", null);
                                                                                                            jsonData.put("LastGuest", null);
                                                                                                            jsonData.put("Status", 1);
                                                                                                            String obj = jsonData.toString();
                                                                                                            new AddHabitantHandler(AddHabitantActivity.this).execute(obj);
                                                                                                        } catch (JSONException e) {
                                                                                                            e.printStackTrace();
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });


    }





    /*Funkcje do walidacji formularza*/


    private void validateForm(){






    }


    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }



    private boolean isValid(String date){

        Date testDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            testDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (!sdf.format(testDate).equals(date))
        {
            return false;
        }


        return true;
    }


}


