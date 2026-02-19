package com.example.questionbuilder.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbuilder.Adapter.ResponsesAdapter;
import com.example.questionbuilder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewExistingBankActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_existing_bank);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Responses");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewResponses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager

        // Fetch and display the saved responses
        fetchResponses();
    }

    // Fetch responses from Firebase and display in RecyclerView
    private void fetchResponses() {
        // Attach a listener to read the data at the "Responses" reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> responsesList = new ArrayList<>();
                // Loop through all the children of the Responses node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get each response as a String
                    String response = snapshot.getValue(String.class);
                    responsesList.add(response); // Add to list
                }

                // Check if responsesList is populated
                if (responsesList.isEmpty()) {
                    Toast.makeText(ViewExistingBankActivity.this, "No responses available", Toast.LENGTH_SHORT).show();
                } else {
                    // Display the fetched responses in RecyclerView
                    displayResponses(responsesList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewExistingBankActivity.this, "Failed to load responses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Display responses in RecyclerView
    private void displayResponses(List<String> responsesList) {
        // Create an adapter for RecyclerView and set it
        ResponsesAdapter adapter = new ResponsesAdapter(responsesList);
        recyclerView.setAdapter(adapter);
    }
}
