package com.example.questionbuilder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.questionbuilder.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    private AuthRepository repository;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository= new AuthRepository(application);
        currentUser= repository.getCurrentUser();
        firebaseUserMutableLiveData= repository.getFirebaseUserMutableLiveData();
    }
    public void signUp(String Email, String Pass){
        repository.signUp(Email, Pass);
    }
    public void signIn(String Email, String Pass){
        repository.signIn(Email, Pass);
    }
    public void signOut(){
        repository.SignOut();
    }
}
