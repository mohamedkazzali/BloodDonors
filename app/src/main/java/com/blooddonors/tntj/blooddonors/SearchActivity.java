package com.blooddonors.tntj.blooddonors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements Serializable {

    // Private Variables
    private Spinner bloodSpinner;
    private Spinner countrySpinner;
    private Spinner stateSpinner;
    private Spinner citySpinner;
    private Spinner districtSpinner;

    HashMap<String, Object> donors = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // For Blood Group
        bloodSpinner = (Spinner) findViewById(R.id.bloodSpinner);
        ArrayAdapter<CharSequence> bloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_list, android.R.layout.simple_spinner_item);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(bloodAdapter);

        // For Country
        countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this,
                R.array.country_list, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        // For State
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_list, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        // For District
        districtSpinner = (Spinner) findViewById(R.id.citySpinner);
        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(this,
                R.array.district_list, android.R.layout.simple_spinner_item);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // JSONParser parser = new JSONParser();
                JSONArray areas = null;
                String districtString =  districtSpinner.getItemAtPosition(districtSpinner.getSelectedItemPosition()).toString();

                String jsonString = loadJSONFromAsset(SearchActivity.this);
                try{
                    Object jsonObject = new JSONObject(jsonString);
                    // Object jsonObject = loadJSONFromAsset(DonateActivity.this);
                    JSONObject stateObj = (JSONObject)jsonObject;
                    JSONObject cityObj = (JSONObject)stateObj.get("Tamilnadu");
                    areas = (JSONArray)cityObj.get(districtString);
                    ArrayList<String> areaList = new ArrayList<>();
                    for(int j=0; j<areas.length(); j++) {
                        areaList.add(areas.getString(j));
                    }
                    citySpinner.setAdapter(new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_spinner_dropdown_item, areaList));
                }
                catch (Exception e){
                    System.out.print(e.getMessage());
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // For City/Area
        citySpinner = (Spinner) findViewById(R.id.areaSpinner);

        Button clickButton = (Button) findViewById(R.id.donorSearchBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String bloodGroup =  bloodSpinner.getSelectedItem().toString();
                String country = countrySpinner.getSelectedItem().toString();
                String state = stateSpinner.getSelectedItem().toString();
                String city = districtSpinner.getSelectedItem().toString();
                String local = citySpinner.getSelectedItem().toString();


                Intent intent = new Intent(getBaseContext(), DonorListActivity.class);
                Bundle form = new Bundle();
                form.putString("BLOOD", bloodGroup);
                form.putString("COUNTRY", country);
                form.putString("STATE", state);
                form.putString("CITY", city);
                form.putString("LOCAL", local);
                intent.putExtras(form);
                startActivity(intent);

            }
        });
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("location-search.json");
            // InputStream is = context.getResources().openRawResource(R.raw.location);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
