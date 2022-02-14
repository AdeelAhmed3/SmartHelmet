package com.example.smarthelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetupActivity extends AppCompatActivity {

    EditText numberTxt;
    Button saveBtn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView showNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        pref = getSharedPreferences("numpref", Context.MODE_PRIVATE);
        editor = pref.edit();

        numberTxt = findViewById(R.id.number_txt);
        saveBtn = findViewById(R.id.setup_btn);
        showNumber = findViewById(R.id.show_number);

        showNumber.setText(pref.getString("number",""));


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("number",numberTxt.getText().toString().trim());
                editor.apply();
                showNumber.setText(numberTxt.getText().toString());
                numberTxt.setText(null);
                sendSms(numberTxt.getText().toString(),"hi");
                showAlert();
            }
        });

    }


    void showAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Number saved....!");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    void sendSms(String number , String txt){

        SmsManager manager =SmsManager.getDefault();

        manager.sendTextMessage(number,null,txt,null,null);


    }

}