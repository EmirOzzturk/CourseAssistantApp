package com.example.courseassistansapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courseassistansapp.Model.User;
import com.example.courseassistansapp.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private DatabaseReference myDatabase;

    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Emaile göre student ID çıakrtır
        updateStudentID_UI();

        FirebaseAuth Auth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance("https://course-assistants-app-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference();
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.createUserWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //Kayıt olan kullanıcı fUser
                                    FirebaseUser fUser = Auth.getCurrentUser();
                                    Toast.makeText(SignInActivity.this,"Kayıt Başarılı", Toast.LENGTH_LONG).show();
                                    sendEmailVerification();
                                    myUser = createUser();

                                    myDatabase.child("users").child(Objects.requireNonNull(Auth.getUid())).setValue(myUser);
                                    goToMainActivity();
                                }else {
                                    Toast.makeText(SignInActivity.this,"Kayıt Başarısız: "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    private User createUser() {

        if (binding.editStudentId.getText().toString().isEmpty()){
            User user = new User(
                    binding.editTextNameSurname.getText().toString(),
                    binding.editTextEmail.getText().toString(),
                    binding.editTextPassword.getText().toString());
            return user;
        }else{

            String str = binding.editStudentId.getText().toString();
            int intValue = 0;
            try {
                intValue = Integer.parseInt(str);
                // You can now use the intValue variable which holds the integer value
            } catch (NumberFormatException e) {
                // Handle the exception here
                //  e.g., show an error message to the user indicating invalid input
                Toast.makeText(this, "Öğrenci numarasını doğru giriniz", Toast.LENGTH_SHORT).show();
            }

            User user = new User(
                    binding.editTextNameSurname.getText().toString(),
                    binding.editTextEmail.getText().toString(),
                    binding.editTextPassword.getText().toString(),
                    intValue);
            return user;
        }


    }

    private void updateStudentID_UI() {
        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.editTextEmail.getText().toString().endsWith("@std.yildiz.edu.tr")){
                    binding.editStudentId.setVisibility(View.VISIBLE);
                }else{
                    binding.editStudentId.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void sendEmailVerification(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.w("signInActivity", "email sent");
                }
            }
        });
    }
    private void goToMainActivity(){
        Intent mainActivity = new Intent(this, LoginActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
