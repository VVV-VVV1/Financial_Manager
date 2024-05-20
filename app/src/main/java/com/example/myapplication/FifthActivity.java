package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityFifthBinding;

public class FifthActivity extends AppCompatActivity {
    private ActivityFifthBinding binding;
    private Activity ActivityCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFifthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}