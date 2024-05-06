package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";

    ActivityMainBinding binding;

    private FirebaseAuth auth;
    private TextInputEditText emailField;
    private TextInputEditText passwordField;
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        emailField = (TextInputEditText) binding.email;
        passwordField = (TextInputEditText) binding.password;

        Log.d(TAG, "Activity created");
        binding.loginbtn.setOnClickListener(v -> onLoginClick());

        binding.regbtn.setOnClickListener(v -> {
            Intent intent = new Intent (MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        });
    }

    private void onLoginClick() {
        String email = emailField.getText().toString();
        String pass = passwordField.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Почта/Пароль не могут быть пустыми", Toast.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        usersRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    boolean isAdmin = dataSnapshot.child("admin").getValue(Boolean.class);

                                    // Сохранение значения isAdmin в SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putBoolean("isAdmin", isAdmin);
                                    myEdit.apply();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Обработка ошибок
                            }
                        });

                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure (@NonNull Exception e){
                        Snackbar.make(binding.textView,
                                "Authorisation Error: " + e.getMessage(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}