package com.example.myfriends;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myfriends.Model.BEFriend;
import com.example.myfriends.Utils.DateConversion;

import java.util.Date;


public class DetailActivity extends AppCompatActivity {

    final static int CAMERA_REQUEST = 1;
    String TAG = MainActivity.TAG;

    ImageView img;
    EditText etName;
    EditText etEmail;
    EditText etWebsite;
    EditText etPhone;
    DatePicker dateBirthday;
    EditText etAddress;
    EditText etBirthday;
    CheckBox cbFavorite;
    Button btnOk,btnCancel;
    int friendPosition;

    BEFriend f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        cbFavorite = findViewById(R.id.cbFavorite);
        etEmail = findViewById(R.id.etEmail);
        etWebsite = findViewById(R.id.etWebsite);
       // dateBirthday = findViewById(R.id.dateBirthday);
        etAddress = findViewById(R.id.etAddress);
        etBirthday = findViewById(R.id.etBirthday);
        img = findViewById(R.id.imgFriendDetail);

        setGUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itMakeCall:
                makeCall();
                return true;
            case R.id.itSendSms:
                sendSms();
                return true;
            case R.id.itSendEmail:
                sendEmail();
                return true;
            case R.id.itOpenWebsite:
                openWebsite();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void sendEmail()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = { etEmail.getText().toString() };
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MyAndroidEmailTest");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello from the wild west");
        startActivity(emailIntent);
    }

    private void openWebsite()
    {
        String url = etWebsite.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void makeCall()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + etPhone.getText().toString()));
        startActivity(intent);
    }

    private void sendSms() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            String[] permissions = {Manifest.permission.SEND_SMS};

            requestPermissions(permissions, 1);
            return;
        }

        SmsManager smsManager = SmsManager.getDefault();
        String text = "Top of MORNING to yall !!!!!";
        smsManager.sendTextMessage(etPhone.getText().toString(), null, text, null, null);
    }

    //Set up gui with existing friend
    private void setUpGUI(BEFriend friend)
    {
        f = friend;
        friendPosition = getIntent().getExtras().getInt("position");

        etName.setText(f.getName());
        etPhone.setText(f.getPhone());
        etEmail.setText(f.getEmail());
        etWebsite.setText(f.getUrl());
        etAddress.setText(f.getAddress());
        cbFavorite.setChecked(f.isFavorite());

    /*    Date date = f.getBirthday();
        dateBirthday.init(date.getYear(),date.getMonth(),date.getDay(),(DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            etBirthday.setText( "" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
        });*/

        etBirthday.setText(DateConversion.toString(f.getBirthday()));

        if(f.getPhoto() != null)
            img.setImageBitmap(f.getPhoto());
        else
            img.setImageResource(R.drawable.froggy);
    }

    //Set up gui for creating
    private void setUpGUI()
    {
    }

    private void setGUI()
    {
        //Just browse friend
        if(getIntent().hasExtra("friend")){
            setUpGUI((BEFriend) getIntent().getSerializableExtra("friend"));
        }
        else{ //Create new friend
            setUpGUI();
        }

        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener((View v)->{
            Intent data = new Intent();
            BEFriend newFriend = BEFriend.create(f != null ? f.getId() : 0,etName.getText().toString())
                    .birthAt(DateConversion.toDate(etBirthday.getText().toString()))
                    .livesAt(etAddress.getText().toString())
                    .withPhoneNumber(etPhone.getText().toString())
                    .withEmail(etEmail.getText().toString())
                    .withWebsite(etWebsite.getText().toString())
                    .withPhoto(((BitmapDrawable)img.getDrawable()).getBitmap())
                    .asFriend(cbFavorite.isSelected());

            data.putExtra("position",friendPosition);
            data.putExtra("friend",newFriend);
            setResult(RESULT_OK,data);
            finish();
        });

        btnCancel.setOnClickListener((View v)->{
            setResult(RESULT_CANCELED);
            finish();
        });

        //Set up camera on image click
        img.setOnClickListener((v) ->{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }
}
