package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityThirdBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FifthActivity extends AppCompatActivity {
    DatabaseReference users;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    FirebaseAuth auth;
    ActivityThirdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance();

        users = db.getReference("Users");
    }
}