package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySecondBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    ActivitySecondBinding binding;
    private EditText numberInput;
    private Button addButton;
    private TextView sumDisplay;
    private float sum = 0;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    DatabaseReference users;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        binding.addButton.setOnClickListener(v -> addNumber());

        binding.remButton.setOnClickListener(v -> removeNum());

        binding.nextActivity.setOnClickListener(v ->{
            Intent intent = new Intent (SecondActivity.this, FourthActivity.class);
            startActivity(intent);
        });

        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance();

        users = db.getReference("Users");

//        binding.AnalisActivity.setOnClickListener(v ->{
//            Intent intent = new Intent(SecondActivity.this, FifthActivity.class);
//            Log.d("ActivityTransition", "Navigating to SecondActivity");
//            startActivity(intent);
//        });

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("admin", false);

        if (isAdmin) {
            binding.remButton.setVisibility(View.VISIBLE);
        } else {
            binding.remButton.setVisibility(View.GONE);
        }

    }

    private void addNumber() {
        String numberStr = binding.numberInput.getText().toString();
        if (!numberStr.isEmpty()){
            float number = Float.parseFloat(numberStr);
            sum += number;

            binding.sumDisplay.setText("Cумма " + sum);
            binding.numberInput.setText("");
        }

        HashMap hashMap = new HashMap();
        hashMap.put("sum", sum);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        usersRef.child(user.getUid()).updateChildren(hashMap);

        Toast.makeText(SecondActivity.this, "Данные сохранены", Toast.LENGTH_LONG).show();
    }

    private void loadNum(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usersRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.sumDisplay.setText("Сумма:" + snapshot.child("sum").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeNum(){
        HashMap hashMap = new HashMap();
        hashMap.put("sum", 0);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        usersRef.child(user.getUid()).updateChildren(hashMap);

        Toast.makeText(SecondActivity.this, "Данные удалены", Toast.LENGTH_LONG).show();

        sum = 0;
        binding.sumDisplay.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNum();
    }
}
