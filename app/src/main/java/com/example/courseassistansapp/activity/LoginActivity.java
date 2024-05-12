package com.example.courseassistansapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courseassistansapp.databinding.ActivityLoginBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String email;
    private String password;
    private DatabaseReference myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance("https://course-assistants-app-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference();

        if (auth.getCurrentUser() != null) {
            binding.textEmail.setText(auth.getCurrentUser().getEmail());
        }

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.textEmail.getText().toString();
                password =binding.textPassword.getText().toString();
                auth.signInWithEmailAndPassword(email , password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (email.endsWith("@yildiz.edu.tr")){
                                startActivity(new Intent(LoginActivity.this, InstructorMainMenu.class));
                                finish();
                            }else if (email.endsWith("@std.yildiz.edu.tr")){
                                startActivity(new Intent(LoginActivity.this, StudentMainMenu.class));
                                finish();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Giriş hatası: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (auth.getCurrentUser() == null) {
                    goToSignInActivity();
                    finish();
                }
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    AuthUI.getInstance().signOut(LoginActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                reload();
                            }
                        }
                    });
                }

            }
        });

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( LoginActivity.this, ForgotPassword.class));
            }
        });
    }

    private void reload() {
        Intent intent = getIntent();
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    private void goToSignInActivity(){
        Intent signIn = new Intent(this, SignInActivity.class);
        startActivity(signIn);
        finish();
    }


}