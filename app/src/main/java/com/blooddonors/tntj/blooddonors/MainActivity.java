package com.blooddonors.tntj.blooddonors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView mainGrid = (GridView) findViewById(R.id.main_menu);
        // Instance of ImageAdapter class
        mainGrid.setAdapter(new ImageAdapter(this));

        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(searchActivity);
                        break;
                    case 1:
                        Intent donateActivity = new Intent(getApplicationContext(), DonateActivity.class);
                        startActivity(donateActivity);
                        break;
                    // case 2:
                        // Intent loginActivity = new Intent(getApplicationContext(), SearchActivity.class);
                        // startActivity(loginActivity);
                        // break;
                    case 2:
                        Intent contactActivity = new Intent(getApplicationContext(), ContactActivity.class);
                        startActivity(contactActivity);
                        break;
                    case 3:
                        Intent aboutActivity = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(aboutActivity);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
