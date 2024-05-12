package com.example.courseassistansapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.courseassistansapp.Model.User;
import com.example.courseassistansapp.databinding.ActivityAccountBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private User user;

    private String password;
    private Uri imageUri = null;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static String currentPhotoPath;
    StorageReference storageRef = FirebaseStorage.getInstance("gs://course-assistants-app.appspot.com").getReference();
    FirebaseAuth Auth = FirebaseAuth.getInstance();
    DatabaseReference myDatabase = FirebaseDatabase.getInstance("https://course-assistants-app-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //Event Listener oluştur ve Databaseden userı al
        ValueEventListener userInfoListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //database'den user classını al
                user = snapshot.child("users").child(Auth.getUid()).getValue(User.class);

                if (user != null){
                    password = user.getPassword();
                    writeToScreen(user);
                }
                if (user.getTitle().equals("Akademisyen")){
                    binding.editStudentId.setVisibility(View.GONE);
                    binding.studentId.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "veri okunamadı", error.toException());

            }
        };
        //Listener'ı Database'e ekle
        myDatabase.addValueEventListener(userInfoListener);



        //Tuşa basınca bilgileri güncelle
        binding.updateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //database'e user classının örneğini yaz
                user = readUserInfoFromScreen();
                user.setPassword(password);
                myDatabase.child("users").child(Auth.getUid()).setValue(user);
            }
        });

        binding.backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Önceki activity ye döner
                finish();
            }
        });

        //Fotoğraf koy
        binding.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermissions();
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        binding.whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = user.getContactEmailAddress();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setPackage("com.whatsapp");  // Optional to suggest WhatsApp

                try {
                    startActivity(Intent.createChooser(intent, "Share with"));
                } catch (android.content.ActivityNotFoundException ex) {
                    // Handle the case where WhatsApp is not installed
                    Toast.makeText(AccountActivity.this, "WhatsApp yüklü değil", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = user.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Phone Number of "+ user.getName());

                try {
                    startActivity(Intent.createChooser(intent, "Send Email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AccountActivity.this, "E-posta uygulaması yüklü değil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "05306417433"; // Replace with recipient phone number
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));

                String message = user.getName();
                intent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AccountActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private User readUserInfoFromScreen() {
        User tmp = new User();

        tmp.setStudentID(Integer.valueOf(binding.editStudentId.getText().toString()));
        tmp.setEmail(binding.editEmail.getText().toString());
        tmp.setName(binding.editNameSurname.getText().toString());
        tmp.setOngoingEducation(binding.editOngoingEducation.getText().toString());
        tmp.setPhoneNumber(binding.editPhoneNumber.getText().toString());
        tmp.setContactEmailAddress(binding.editEmailContact.getText().toString());
        tmp.setTitle(binding.title.getText().toString());
        if(imageUri != null){
            tmp.setImageUrl(imageUri.toString());
        }

        return tmp;
    }


    private void writeToScreen(User user){

        binding.title.setText(user.getTitle());


        if (user.getName() != null) {
            binding.editNameSurname.setText(user.getName());
        }

        if (user.getEmail() != null) {
            binding.editEmail.setText(user.getEmail());
        }

        if (user.getStudentID() != null) {
            binding.editStudentId.setText(String.valueOf(user.getStudentID()));
        }

        if (user.getTitle() != null) {
            binding.title.setText(user.getTitle());
        }

        if (user.getOngoingEducation() != null) {
            binding.editOngoingEducation.setText(user.getOngoingEducation());
        }

        if (user.getContactEmailAddress() != null) {
            binding.editEmailContact.setText(user.getContactEmailAddress());
        }

        if (user.getPhoneNumber() != null) {
            binding.editPhoneNumber.setText(user.getPhoneNumber());
        }
    }

    private void verifyPermissions(){
        String[] permissions =
                {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){

            dispatchTakePictureIntent();
        }else{
            ActivityCompat.requestPermissions(this, permissions, CAMERA_PERM_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions, grantResults);
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                Log.d("tag", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = null ;
                try {
                    photoURI = FileProvider.getUriForFile(this, "com.example.android.provider", photoFile);
                }catch (IllegalArgumentException exception){
                    Log.d("tag", exception.getMessage());
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d("tag", storageDir.toString());
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){

                File f = new File(currentPhotoPath);
                binding.profilePhoto.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

                uploadToFirebase(f);

            }
        }
    }

    private void uploadToFirebase(File file){
        Uri uri = Uri.fromFile(file);

        StorageReference imageRef = storageRef.child(Auth.getUid() + "." + "jpeg");
        imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        myDatabase.child("users").child(Auth.getUid()).child("imageUrl").setValue(uri.toString());
                        imageUri = uri;
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountActivity.this, "Fotoğraf Yüklenemedi", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void goToMainActivity(){
        Intent signIn = new Intent(this, LoginActivity.class);
        startActivity(signIn);
        finish();
    }
}