package com.example.wagner.avocado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RatingBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FarmerEditProfile extends AppActivity implements DataReceived {

    String firstname, lastname, phonenumber, postalcode, country, city, address, pass, transactions;
    String deliveries, ratings;
    RatingBar rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_edit_profile);
        setDefaultView();
        rb = (RatingBar)findViewById(R.id.ratingBar);
        rb.setClickable(false);

        final Button saveChangesButton = findViewById(R.id.farmerSaveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeChanges();
                setDefaultView();
            }
        });

        final Button backButton = findViewById(R.id.farmerProfileBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnHomeIntent = new Intent(FarmerEditProfile.this, FarmerHome.class);
                returnHomeIntent.putExtra("phonenumber",
                        getIntent().getStringExtra("phonenumber"));
                startActivity(returnHomeIntent);
            }
        });
      
        DatabaseHandler db = new DatabaseHandler(this);
        db.getFarmer(getIntent().getStringExtra("phonenumber"));


    }

    private void setDefaultView(){
        Button editProfile = findViewById(R.id.farmerEditProfileButton);
        Button saveChanges = findViewById(R.id.farmerSaveChangesButton);
        //TextView short_bio = findViewById(R.id.user_profile_short_bio);
        TextView phoneNumber = findViewById(R.id.user_profile_phoneNumber);
        TextView address = findViewById(R.id.user_profile_address);
        TextView city = findViewById(R.id.user_profile_city);
        TextView postalcode = findViewById(R.id.user_profile_postalcode);
        TextView country = findViewById(R.id.user_profile_country);

        //EditText edit_short_bio = findViewById(R.id.profile_edit_short_bio);
        EditText edit_phoneNumber = findViewById(R.id.profile_edit_phoneNumber);
        EditText edit_address = findViewById(R.id.profile_edit_address);
        EditText edit_city = findViewById(R.id.profile_edit_city);
        EditText edit_postalcode = findViewById(R.id.profile_edit_postalcode);
        EditText edit_country = findViewById(R.id.profile_edit_country);


        editProfile.setVisibility(View.VISIBLE);
        saveChanges.setVisibility(View.GONE);

        //short_bio.setVisibility(View.VISIBLE);
        phoneNumber.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        city.setVisibility(View.VISIBLE);
        postalcode.setVisibility(View.VISIBLE);
        country.setVisibility(View.VISIBLE);

        //edit_short_bio.setVisibility(View.GONE);
        edit_phoneNumber.setVisibility(View.GONE);
        edit_address.setVisibility(View.GONE);
        edit_city.setVisibility(View.GONE);
        edit_postalcode.setVisibility(View.GONE);
        edit_country.setVisibility(View.GONE);
    }

    private void setEditorView() {
        Button editProfile = findViewById(R.id.farmerEditProfileButton);
        Button saveChanges = findViewById(R.id.farmerSaveChangesButton);
        //TextView short_bio = findViewById(R.id.user_profile_short_bio);
        TextView tphoneNumber = findViewById(R.id.user_profile_phoneNumber);
        TextView taddress = findViewById(R.id.user_profile_address);
        TextView tcity = findViewById(R.id.user_profile_city);
        TextView tpostalcode = findViewById(R.id.user_profile_postalcode);
        TextView tcountry = findViewById(R.id.user_profile_country);

        //EditText edit_short_bio = findViewById(R.id.profile_edit_short_bio);
        EditText edit_phoneNumber = findViewById(R.id.profile_edit_phoneNumber);
        EditText edit_address = findViewById(R.id.profile_edit_address);
        EditText edit_city = findViewById(R.id.profile_edit_city);
        EditText edit_postalcode = findViewById(R.id.profile_edit_postalcode);
        EditText edit_country = findViewById(R.id.profile_edit_country);

        edit_phoneNumber.setText(phonenumber);
        edit_address.setText(address);
        edit_city.setText(city);
        edit_postalcode.setText(postalcode);
        edit_country.setText(country);

        editProfile.setVisibility(View.GONE);
        saveChanges.setVisibility(View.VISIBLE);

        //short_bio.setVisibility(View.GONE);
        tphoneNumber.setVisibility(View.GONE);
        taddress.setVisibility(View.GONE);
        tcity.setVisibility(View.GONE);
        tpostalcode.setVisibility(View.GONE);
        tcountry.setVisibility(View.GONE);

        //edit_short_bio.setVisibility(View.VISIBLE);
        edit_phoneNumber.setVisibility(View.VISIBLE);
        edit_address.setVisibility(View.VISIBLE);
        edit_city.setVisibility(View.VISIBLE);
        edit_postalcode.setVisibility(View.VISIBLE);
        edit_country.setVisibility(View.VISIBLE);
    }

    private void writeChanges() {
        //EditText edit_short_bio = findViewById(R.id.profile_edit_short_bio);

        EditText edit_phoneNumber = findViewById(R.id.profile_edit_phoneNumber);
        EditText edit_address = findViewById(R.id.profile_edit_address);
        EditText edit_city = findViewById(R.id.profile_edit_city);
        EditText edit_postalcode = findViewById(R.id.profile_edit_postalcode);
        EditText edit_country = findViewById(R.id.profile_edit_country);

        phonenumber= (String)edit_phoneNumber.getText().toString();
        address = (String)edit_address.getText().toString();
        city = (String)edit_city.getText().toString();
        postalcode = (String)edit_postalcode.getText().toString();
        country = (String)edit_country.getText().toString();



        DatabaseHandler db1 = new DatabaseHandler();
        db1.insertFarmer(firstname, lastname, phonenumber, pass, address, country, postalcode, city,
                transactions, deliveries, ratings);
    }

    public void Success(String response){
        try {
            JSONArray avail = new JSONArray(response);
            for (int i = 0; i < avail.length(); i++) {
                JSONObject x = avail.getJSONObject(i);
                firstname = (String) x.get("firstname");
                lastname = (String) x.get("lastname");
                address = (String) x.get("address");
                phonenumber = (String) x.get("phonenumber");
                pass = (String) x.get("pass");
                city = (String) x.get("city");
                postalcode = (String) x.get("postalcode");
                country = (String) x.get("country");
                transactions = (String) x.get("transactions");
                deliveries = (String) x.get("deliveries");
                ratings = (String) x.get("ratings");


                rb.setRating(Float.parseFloat(ratings));

                TextView nameview = (TextView)findViewById(R.id.user_profile_name);
                nameview.setText(firstname+" "+lastname);
                findViewById(R.id.user_profile_photo).bringToFront();

                TextView phoneview = (TextView)findViewById(R.id.user_profile_phoneNumber);
                phoneview.setText(phonenumber);

                TextView addressview = (TextView)findViewById(R.id.user_profile_address);
                addressview.setText(address);

                TextView cityview = (TextView)findViewById(R.id.user_profile_city);
                cityview.setText(city);

                TextView pcview = (TextView)findViewById(R.id.user_profile_postalcode);
                pcview.setText(postalcode);

                TextView countryview = (TextView)findViewById(R.id.user_profile_country);
                countryview.setText(country);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Button editProfileButton = findViewById(R.id.farmerEditProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setEditorView();
            }
        });
      
        final Button historyButton = findViewById(R.id.farmerHistoryButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(FarmerEditProfile.this, FarmerHistory.class);
                myIntent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber").toString());
                startActivity(myIntent);
            }
        });

        final Button saveChangesButton = findViewById(R.id.farmerSaveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeChanges();
                Intent myIntent = new Intent(FarmerEditProfile.this, FarmerEditProfile.class);
                String phonenumber = getIntent().getStringExtra("phonenumber");
                myIntent.putExtra("phonenumber", phonenumber);
                startActivity(myIntent);
            }
        });

    }
}
