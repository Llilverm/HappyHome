package com.example.happyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputEditTextUsername;
    TextInputEditText mTextInputEditTextEmailR;
    TextInputEditText mTextInputEditTextPasswordR;
    TextInputEditText mTextInputEditTextConfirmPassword;
    Button mButtonRegister;
    FirebaseAuth mAut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*instancias*/
        mCircleImageViewBack=findViewById(R.id.circleimagback);
        mTextInputEditTextUsername=findViewById(R.id.textInputEditTextUsername);
        mTextInputEditTextEmailR=findViewById(R.id.textInputEditTextEmailR);
        mTextInputEditTextPasswordR=findViewById(R.id.textInputEditTextPasswordR);
        mTextInputEditTextConfirmPassword=findViewById(R.id.textInputEditTextConfirmPassword);
        mButtonRegister=findViewById(R.id.ButtonRegister);

        mAut=FirebaseAuth.getInstance();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); /* este paso limpia el formulario de registro, finaliza el ingreso */
            }
        });
    }

    private void register() {
        String username=mTextInputEditTextUsername.getText().toString();
        String email=mTextInputEditTextEmailR.getText().toString();
        String password=mTextInputEditTextPasswordR.getText().toString();
        String confirmpassword=mTextInputEditTextConfirmPassword.getText().toString();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()){
            if (isEmailValid(email)){

                if (password.equals(confirmpassword)){
                    if (password.length()>=6){
                        CreateUser(email,password);
                    }else {
                        Toast.makeText(this, "Las contraseñas deben tener 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(this,"insertó todos los campos, el correo no es valido", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this,"Para continuar inserta todos los campos", Toast.LENGTH_LONG).show();
        }

    }

    private void CreateUser(String email, String password) {
        mAut.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"El usuario se registro correctamente", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RegisterActivity.this,"No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}