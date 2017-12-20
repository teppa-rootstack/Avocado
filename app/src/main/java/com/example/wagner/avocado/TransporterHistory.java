package com.example.wagner.avocado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class TransporterHistory extends AppActivity implements DataReceived {

    ListView lst;


    ArrayList<String> fname = new ArrayList<String>();
    ArrayList<String> fdate = new ArrayList<String>();
    ArrayList<Integer> fimgid = new ArrayList<Integer>();
    ArrayList<String> fstartcity = new ArrayList<String>();
    ArrayList<String> fendcity = new ArrayList<String>();

    String cropprice = "16 000.00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_history);

        DatabaseHandler db = new DatabaseHandler(this);
        db.getTransaction(getIntent().getStringExtra("phonenumber"),"transporter");


    }

    public void Success(String response){

        String name;
        String date;
        String phonenumber;
        String startcity;
        String endcity;

        try {
            System.out.println("THE RESPONSE: "+response);
            JSONArray hist = new JSONArray(response);
            for (int i = 0; i < hist.length(); i++) {
                //x is the transporter object
                JSONObject indhist = hist.getJSONObject(i);

                name = (String)indhist.get("firstname")+" "+(String)indhist.get("lastname");
                date = (String)indhist.get("date");
                phonenumber = (String)indhist.get("phonenumberfarmer");
                startcity = (String)indhist.get("startcity");
                endcity = (String)indhist.get("endcity");

                fname.add(name);
                fdate.add(date);
                fimgid.add(R.drawable.arka);
                fstartcity.add(startcity);
                fendcity.add(endcity);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        lst= findViewById(R.id.listview);


        TransporterHistoryListView customListview = new TransporterHistoryListView(this,
                fname.toArray(new String[fname.size()])
                ,fdate.toArray(new String[fdate.size()])
                ,fimgid.toArray(new Integer[fimgid.size()])
                ,fstartcity.toArray(new String[fstartcity.size()])
                ,fendcity.toArray(new String[fendcity.size()]));
        lst.setAdapter(customListview);

        final Button backButton = findViewById(R.id.transporterHistoryBackButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(TransporterHistory.this,
                        TransporterEditProfile.class);

                String phonenumber = getIntent().getStringExtra("phonenumber");
                myIntent.putExtra("phonenumber", phonenumber);
                startActivity(myIntent);
            }
        });
    }
}
