package com.example.testing;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    EditText editTextEmail, editTextPassword,editTextUser,editTextAge,editTextName;
    Button btnReg;
    ProgressBar progressBar;
    TextView textView;
    FirebaseAuth mAuth;
    //link Realtime
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://test-99e7b-default-rtdb.firebaseio.com/");
    public void onBackPressed() {
        // Quay về màn hình trước đó
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish(); // Kết thúc hiện tại Activity để ngăn chặn người dùng quay lại nó bằng nút back
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUser=findViewById(R.id.editTextUser);
        editTextAge=findViewById(R.id.editTextAge);
        editTextName=findViewById(R.id.editTextName);
        btnReg = findViewById(R.id.btnReg);
        textView = findViewById(R.id.loginNow);
        progressBar = findViewById(R.id.progressBar);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password,username,age,name;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                age=String.valueOf(editTextAge.getText());
                username=String.valueOf(editTextUser.getText());;
                name=String.valueOf(editTextName.getText());
                databaseReference.child("users").child(username).child("email").setValue(email);
                databaseReference.child("users").child(username).child("password").setValue(password);
                databaseReference.child("users").child(username).child("age").setValue(age);
                databaseReference.child("users").child(username).child("studentId").setValue(username);
                databaseReference.child("users").child(username).child("studentName").setValue(name);
//                databaseReference.child(email).child("password").setValue(password);
//                databaseReference.child(email).child("age").setValue(age);
//                databaseReference.child(email).child("studentId").setValue(username);
//                databaseReference.child(email).child("studentName").setValue(username);
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();


                                    Toast.makeText(Register.this, "Succes",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Register.this, "Authentication success",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }
}