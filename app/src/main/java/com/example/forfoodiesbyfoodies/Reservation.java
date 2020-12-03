package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class Reservation extends AppCompatActivity {
//Declaration
    android.webkit.WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
 //Receiving the date, chosen from the calendar by the user on the previous activity
        String day = getIntent().getStringExtra("DAY");
        String month = getIntent().getStringExtra("MONTH");
        String year = getIntent().getStringExtra("YEAR");

        wv = findViewById(R.id.web_view);
        wv.setWebViewClient(new WebViewClient());
 //link to be opened into the activity from external web reservation page with the specific date
        wv.loadUrl("https://www.opentable.com/s/?covers=2&dateTime="+year+"-"+month+"-"+day+
                "%2019%3A00&metroId=72&regionIds=5316&pinnedRids%5B0%5D=87967&enableSimpleCuisines=true&includeTicketedAvailability=true&pageType=0 ");
        WebSettings settings = wv.getSettings();
 //Enables java script
        settings.setJavaScriptEnabled(true);
    }
    @Override
 //When the back button is clicked, the app won't stop and a previous activity will be displayed
    public void onBackPressed() {
        if (wv.canGoBack())
        {
            wv.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}