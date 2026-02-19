package com.example.questionbuilder.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.questionbuilder.R;
import com.example.questionbuilder.viewmodel.AuthViewModel;

public class SplashActivity extends AppCompatActivity {
    ImageView splashImage;
    TextView splashText2;
    private AuthViewModel viewModel;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        splashImage=findViewById(R.id.splashImage);
        splashText2=findViewById(R.id.splashText2);
        viewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AuthViewModel.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(viewModel.getCurrentUser()!= null){
                    intent=new Intent(SplashActivity.this, GenerativeActivity.class);
                    startActivity(intent);
                }
                else{
                    intent=new Intent(SplashActivity.this, UserOptionsActivity.class);
                    startActivity(intent);
                }

                startActivity(intent);
            }
        },2000);


    }
}