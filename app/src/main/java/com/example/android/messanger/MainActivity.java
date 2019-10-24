package com.example.android.messanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txt_message, txt_pNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_message = (EditText) findViewById(R.id.txt_message);
        txt_pNumber = (EditText) findViewById(R.id.txt_phone_number);

    }


    public void btn_send(View view) {

        //Permission which we are using
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        //checking the permission
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            //If user grants perm. then what to do
            MyMessage();
        } else {
            //if user denies, then again request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);

        }
    }

    private void MyMessage() {

        //to get pNum from editTxt, get the txt, convert in string, then trim all the spaces
        String phoneNumber = txt_pNumber.getText().toString().trim();
        //get msg from editTxt
        String Message = txt_message.getText().toString().trim();

        //check if user hasn't entered null strings in edit txt
        if (!txt_pNumber.getText().toString().equals("") && !txt_message.getText().toString().equals(""))//this cond will work he hasn't entered "" means null

        {
            //To send msg , use msg Manager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, Message, null, null);

            //displays a toast msg
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        }

        //if user enters nothing, then he gets a toast
        else {
            Toast.makeText(this, "Please enter Number & Message", Toast.LENGTH_SHORT).show();

        }
    }

    //mtd for handling permission, for request code generated will be matched by using a switch condition
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:

                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    MyMessage();
                } else {
                    Toast.makeText(this, "You don't have Required Permissions to execute", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
