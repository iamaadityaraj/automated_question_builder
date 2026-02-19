package com.example.questionbuilder.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.questionbuilder.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class UserOptionsActivity extends AppCompatActivity {
    Toolbar toolBar;
    TextView loginPageAppText,adminText,trainerText,employeeText;
    ImageView adminLogo,trainerLogo,employeeLogo;
    Button adminButton,trainerButton,EmployeeButton;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_options);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // tool bar Widgets
        toolBar=findViewById(R.id.toolBar);

        loginPageAppText=findViewById(R.id.loginPageAppText);
        adminText=findViewById(R.id.adminText);
        trainerText=findViewById(R.id.trainerText);
        employeeText=findViewById(R.id.employeeText);

        //Image View Widgets

        adminLogo=findViewById(R.id.adminLogo);
        trainerLogo=findViewById(R.id.trainerLogo);
        employeeLogo=findViewById(R.id.employeeLogo);

        // Button Widgets
        adminButton=findViewById(R.id.adminButton);
        trainerButton=findViewById(R.id.trainerButton);
        EmployeeButton=findViewById(R.id.EmployeeButton);


        intent=getIntent();
        Log.d("UserOptionsActivity", "Extras: " + intent.getExtras());
        String photoUrlString=intent.getStringExtra("photoUrl");
        String username= intent.getStringExtra("username");

        Log.d("UserOptionsActivity", "Username: " + username);
        Log.d("UserOptionsActivity", "Photo URL: " + photoUrlString);




        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(UserOptionsActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        trainerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(UserOptionsActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        EmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(UserOptionsActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}