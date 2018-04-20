package com.blooddonors.tntj.blooddonors;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class DonorListActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mDonorList;
    private List<Donors> donorsList;
    private DonorsAdapter donorsAdapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        donorsList = new ArrayList<>();
        donorsAdapter = new DonorsAdapter(donorsList);

        mDonorList = (RecyclerView) findViewById(R.id.donor_list_main);
        mDonorList.setHasFixedSize(true);
        mDonorList.setLayoutManager(new LinearLayoutManager(this));
        mDonorList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mDonorList.setAdapter(donorsAdapter);

        Bundle form = getIntent().getExtras();
        final String city = form.getString("CITY");
        final String local = form.getString("LOCAL");
        final String blood = form.getString("BLOOD");

        if(TextUtils.isEmpty(city) || TextUtils.isEmpty(local) || TextUtils.isEmpty(blood)) {
            Toast.makeText(DonorListActivity.this, "Failed to retrive data. Please retry!",
                    Toast.LENGTH_LONG).show();
        } else if (local.equals("All")) {
            db.collection("donors")
                    .whereEqualTo("bloodGroup", blood)
                    .whereEqualTo("city", city)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            if (e != null) {
                                Toast.makeText(DonorListActivity.this, "Error while getting data from database...", Toast.LENGTH_LONG);
                            }
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if(doc.getType() == DocumentChange.Type.ADDED)
                                {
                                    Donors donors = doc.getDocument().toObject(Donors.class);
                                    donorsList.add(donors);
                                    donorsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
        else {
            db.collection("donors")
                    .whereEqualTo("bloodGroup", blood)
                    .whereEqualTo("city", city)
                    .whereEqualTo("local", local)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            if (e != null) {
                                Toast.makeText(DonorListActivity.this, "Error while getting data from database...", Toast.LENGTH_LONG);
                            }
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if(doc.getType() == DocumentChange.Type.ADDED)
                                {
                                    Donors donors = doc.getDocument().toObject(Donors.class);
                                    donorsList.add(donors);
                                    donorsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }



    }
}
