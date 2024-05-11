package com.example.courseassistansapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courseassistansapp.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {


    private ActivityForgotPasswordBinding binding;

    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.email.getText().toString();
                if(!email.isEmpty()){
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this,
                                        "Şifre yenileme linki başarıyla göderildi", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            }else {
                                Toast.makeText(ForgotPassword.this,
                                        task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                //TODO: SİLİNECEK GERİ TUŞU EKLENECEK
                                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            }
                        }
                    });
                }
            }
        });

    }
}