package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;


public class LoginActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public int auth;
    private UserServices userServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        userServices = new UserServices();

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //if(currentUser != null){
        //    System.out.println("CREATE NEW INTENT OF MAIN ACTIVE");
        //}
    }


    public void login(View view){
        EditText email = (EditText) findViewById(R.id.Email_EditText);
        EditText password = (EditText) findViewById(R.id.Password_EditText);
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        TextView loginFail = findViewById(R.id.Login_Failed_TextView);

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        // validate that there are emails and passwords to check
        if(!(validateData(view, email, password))){return;}

        userServices.logInUser(emailString, passwordString, view, this);




//
    }

    private boolean validateData(View view, EditText email, EditText password){
        if(email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()){
            //makePopUp(view, "Provide credentials to login.");
            Snackbar mySnackbar = Snackbar.make(view, "Provide credentials to login", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        return true;
    }

public void trasitionToSignUp(View view)
{
    Intent intent = new Intent(this, SignUpActivity.class);
    startActivity(intent);
    finish();
}

}