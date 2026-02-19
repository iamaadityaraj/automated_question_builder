package com.example.questionbuilder.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.questionbuilder.R;
import com.example.questionbuilder.viewmodel.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignupActivity extends AppCompatActivity {
    private AuthViewModel viewModel;
    private Intent intent;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;

    private TextView signupPageAppText,signupText,accountCreationText,orText,alreadyHaveAnAccount;
    private EditText username,email_id,password,confirmPassword;
    private Button signupPageSignupButton,siginWithGoogle,signup_page_login_btn;

    private final ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                Log.d("SignInActivity", "Intent Data: " + data);
                Task<GoogleSignInAccount> accountTask=GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try{
                    GoogleSignInAccount signInAccount= accountTask.getResult(ApiException.class);
                    AuthCredential authCredential= GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                auth=FirebaseAuth.getInstance();
                                FirebaseUser user= auth.getCurrentUser();
                                intent= new Intent(SignupActivity.this, UserOptionsActivity.class);
                                if(user !=null){
                                    Log.d("SignUpActivity", "User signed in: " + user.getDisplayName());
                                    Log.d("SignUpActivity", "User email: " + user.getEmail());
                                    String username=user.getDisplayName();
                                    Uri photoUrl = user.getPhotoUrl();
                                    intent.putExtra("username", username);
                                    intent.putExtra("USER_PHOTO_URL", photoUrl != null ? photoUrl.toString() : null);

                                }
                                Toast.makeText(SignupActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else{
                                Log.e("SignUpActivity", "User is null after sign-in.");
                                Toast.makeText(SignupActivity.this, "Failed to Sign In: "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TextView Widgets
        signupPageAppText=findViewById(R.id.signupPageAppText);
        accountCreationText=findViewById(R.id.accountCreationText);
        orText=findViewById(R.id.orText);
        alreadyHaveAnAccount=findViewById(R.id.alreadyHaveAnAccount);

        // EditText Widgets
        username=findViewById(R.id.username);
        email_id=findViewById(R.id.email_id);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.loginPagePassword);

        // Button Widgets
        signupPageSignupButton=findViewById(R.id.signupPageSignupButton);
        siginWithGoogle=findViewById(R.id.siginWithGoogle);
        signup_page_login_btn=findViewById(R.id.signup_page_login_btn);

        GoogleSignInOptions options= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientId))
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(SignupActivity.this,options);

        auth=FirebaseAuth.getInstance();

        siginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });


        signup_page_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signupPageSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email_id.getText().toString();
                String Pass= password.getText().toString();
                if(!Email.isEmpty() && !Pass.isEmpty()){
                    viewModel.signUp(Email,Pass);
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    intent= new Intent(SignupActivity.this, GenerativeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Please Enter a Valid Details", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        viewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AuthViewModel.class);
    }
}