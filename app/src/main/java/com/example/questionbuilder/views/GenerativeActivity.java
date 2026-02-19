package com.example.questionbuilder.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.questionbuilder.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class GenerativeActivity extends AppCompatActivity {
    private Intent intent;
    ImageView generativePageAppLogo;
    TextView generativePageTitle,generativePageTitle2;
    Button generateQuestionButton,viewExistingBanksButton,SignOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generative);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Image View Widgets
        generativePageAppLogo=findViewById(R.id.generativePageAppLogo);

        // Text View Widgets

        // Button Widgets
        generateQuestionButton=findViewById(R.id.generateQuestionButton);
        viewExistingBanksButton=findViewById(R.id.viewExistingBanksButton);
        SignOut=findViewById(R.id.SignOut);



        generateQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(GenerativeActivity.this, QuestionGenerationActivity.class);
                startActivity(intent);
            }
        });
        viewExistingBanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(GenerativeActivity.this, ViewExistingBankActivity.class);
                startActivity(intent);
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {     @Override     public void onClick(View view) {     FirebaseAuth.getInstance().signOut();     intent=new Intent(GenerativeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        });

    }
}