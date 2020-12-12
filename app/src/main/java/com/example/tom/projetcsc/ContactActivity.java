package com.example.tom.projetcsc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ContactActivity extends AppCompatActivity {

    public TextView tvContactName;
    public TextView tvContactNumber;
    public String contactName;
    public String contactNumber;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        tvContactName = (TextView)findViewById(R.id.tvContactName);
        tvContactNumber = (TextView)findViewById(R.id.tvContactNumber);

        setAnotherContact();

        Button btnContactCalled = (Button)findViewById(R.id.btnContactCalled);
        Button btnContactNotCalled = (Button)findViewById(R.id.btnContactNotCalled);

        btnContactCalled.setOnClickListener(new GetActivityOnClickListener(this) {
            @Override
            public void onClick(View v) {
                Log.i("Player","win 1 point");
                this.activity.finish();
            }
        });

        btnContactNotCalled.setOnClickListener(new GetActivityOnClickListener(this) {
            @Override
            public void onClick(View v) {
                Log.i("Player","lose 1 point");
                this.activity.finish();
            }
        });

        Button btnGetAnotherContact = (Button)findViewById(R.id.btnGetAnotherContact);
        btnGetAnotherContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnotherContact();
                btnGetAnotherContact.setVisibility(View.INVISIBLE);
            }
        });




    }

    private void getcontact() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        int index = ThreadLocalRandom.current().nextInt(0, cursor.getCount());
        cursor.moveToPosition(index);

        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode ==1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getcontact();
            }
        }
    }

    public void verifyBuildVersion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            getcontact();
        }
    }

    public void setAnotherContact(){
        verifyBuildVersion();
        tvContactName.setText(contactName);
        tvContactNumber.setText(contactNumber);
    }
}