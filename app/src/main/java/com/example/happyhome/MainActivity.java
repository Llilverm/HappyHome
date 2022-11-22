package com.example.happyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewRegister;
    TextInputEditText mTextInputEditTextEmail;
    TextInputEditText mTextInputEditTextPassword;
    Button mButtonInicioLogin;
    FirebaseAuth mAut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewRegister=findViewById(R.id.TextViewRegister);
        mTextInputEditTextEmail=findViewById(R.id.textInputEditTextEmail);
        mTextInputEditTextPassword=findViewById(R.id.textInputEditTextPassword);
        mButtonInicioLogin=findViewById(R.id.ButtonInicioLogin);

        mAut=FirebaseAuth.getInstance();

        mButtonInicioLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String email=mTextInputEditTextEmail.getText().toString();
        String password=mTextInputEditTextPassword.getText().toString();

        mAut.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ((task.isSuccessful())){
                    Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "El email y contraseña no son correctos", Toast.LENGTH_LONG).show();
                }
            }
        });



        Log.d("campo","email "+email);
        Log.d("campo","password "+password);
    }
}