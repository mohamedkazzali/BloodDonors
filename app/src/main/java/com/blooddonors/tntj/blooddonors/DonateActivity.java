package com.blooddonors.tntj.blooddonors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonateActivity extends AppCompatActivity {

    // Private Variables
    private  Spinner bloodSpinner;
    private Spinner countrySpinner;
    private Spinner stateSpinner;
    private Spinner citySpinner;
    private Spinner districtSpinner;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        // For Blood Group
        bloodSpinner = (Spinner) findViewById(R.id.opTxtBloodGroup);
        ArrayAdapter<CharSequence> bloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_list, android.R.layout.simple_spinner_item);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(bloodAdapter);

        // For Country
        countrySpinner = (Spinner) findViewById(R.id.txtCountry);
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this,
                R.array.country_list, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        // For State
        stateSpinner = (Spinner) findViewById(R.id.txtstate);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_list, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        // For District
        districtSpinner = (Spinner) findViewById(R.id.txtDistrict);
        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(this,
                R.array.district_list, android.R.layout.simple_spinner_item);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // JSONParser parser = new JSONParser();
                JSONArray areas = null;
                String districtString =  districtSpinner.getItemAtPosition(districtSpinner.getSelectedItemPosition()).toString();

                String jsonString = loadJSONFromAsset(DonateActivity.this);
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
                    citySpinner.setAdapter(new ArrayAdapter<String>(DonateActivity.this, android.R.layout.simple_spinner_dropdown_item, areaList));
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
        citySpinner = (Spinner) findViewById(R.id.txtCity);

        Button clickButton = (Button) findViewById(R.id.submit_button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SaveDonor(v);
            }
        });
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("location.json");
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

    public void SaveDonor(View view) {

        EditText fullNameTxt = (EditText) findViewById(R.id.opTxtFullName);
        String fullName = fullNameTxt.getText().toString();
        String bloodGroup =  bloodSpinner.getSelectedItem().toString();
        EditText mobileNumberTxt = (EditText) findViewById(R.id.txtMoibleNumber);
        String mobileNumber =mobileNumberTxt.getText().toString();
        String country = countrySpinner.getSelectedItem().toString();
        String state = stateSpinner.getSelectedItem().toString();
        String city = districtSpinner.getSelectedItem().toString();
        String local = citySpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(bloodGroup) || TextUtils.isEmpty(local) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(mobileNumber)) {
            Toast.makeText(DonateActivity.this, "Please fill all mandatory fields...",
                    Toast.LENGTH_LONG).show();
        } else  {
            Map<String, Object> docData = new HashMap<>();
            // Map<String, Object> nestedData = new HashMap<>();
            docData.put("fullName", fullName);
            docData.put("bloodGroup", bloodGroup);
            docData.put("mobileNumber", mobileNumber);
            docData.put("country", country);
            docData.put("state", state);
            docData.put("city", city);
            docData.put("local", local);
            // docData.put("info", nestedData);

            db.collection("donors").document()
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DonateActivity.this, "Saved Successfully... Thank you for joining as donor!",
                                    Toast.LENGTH_LONG).show();
                            Intent mainActivity = new Intent(DonateActivity.this, MainActivity.class);
                            startActivity(mainActivity);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DonateActivity.this, "Failed to save. Please retry!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }



    }


}
