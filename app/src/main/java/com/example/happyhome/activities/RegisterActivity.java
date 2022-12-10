package com.example.happyhome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.happyhome.R;
import com.example.happyhome.models.User;
import com.example.happyhome.providers.AutProviders;
import com.example.happyhome.providers.UsersProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputEditTextUsername;
    TextInputEditText mTextInputEditTextEmailR;
    TextInputEditText mTextInputEditTextPasswordR;
    TextInputEditText mTextInputEditTextConfirmPassword;
    Button mButtonRegister;
    //FirebaseAuth mAut; /*Se modifica el metodo de autenticación*/
    //FirebaseFirestore mFirestore; /*Se modifica el metodo de autenticación*/
    AutProviders mAutProviders;
    UsersProvider mUsersProvider;
    AlertDialog mDialog;

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

        mAutProviders=new AutProviders();
        mUsersProvider=new UsersProvider();

        mDialog=new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento....")
                .setCancelable(false).build();

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

                if(password.equals(confirmpassword)){
                    if(password.length() >=6){
                        createUser(username,email,password);
                    }else {
                        Toast.makeText(this, "Las contraseñas debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "inserto todos los campos pero el correo no es valido", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
//maperar los datos de los usuarios en la base de datos//
    private void createUser(final String username, final String email, final String password) {
        mDialog.show();
        mAutProviders.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id=mAutProviders.getUid();
                    User user=new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);
                    //Map<String,Object> map=new HashMap<>();
                    //map.put("email",email);
                    //map.put("username",username);
                    //map.put("password",password);
                    mUsersProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"El usuario se almaceno correctamente", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(RegisterActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(RegisterActivity.this,"No se pudo almacenar en la base de datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    Toast.makeText(RegisterActivity.this,"El usuario se registro correctamente", Toast.LENGTH_LONG).show();
                }else {
                    mDialog.dismiss();
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