package com.example.happyhome.providers;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AutProviders {

    private FirebaseAuth mAut;

    public AutProviders() {
        mAut=FirebaseAuth.getInstance();
    }

    public Task<AuthResult> register(String email, String password) {
        return mAut.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> login(String email, String password) {
        return mAut.signInWithEmailAndPassword(email, password);
    }

    /*Establecer el metodo de Sing Google*/
    public Task<AuthResult> googleLogin(GoogleSignInAccount googleSignInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        return mAut.signInWithCredential(credential);
    }

    public String getEmail(){
        if (mAut.getCurrentUser() != null) {
            return mAut.getCurrentUser().getEmail();
        }
        else {
            return null;
        }
    }

    /*En caso de estar registrado no genere error buscando el id*/
    public String getUid() {
        if (mAut.getCurrentUser() != null) {
            return mAut.getCurrentUser().getUid();
        }
        else {
            return null;
        }
    }


}
