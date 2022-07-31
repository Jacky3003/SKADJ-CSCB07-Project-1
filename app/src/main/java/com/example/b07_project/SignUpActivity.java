package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

    }

    public void updateUserProfile(String name){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println(user.getDisplayName());
                        }
                    }
                });
    }


    public void signUp(View view){
        EditText last_name = (EditText) findViewById(R.id.LastName_EditText);
        EditText name = (EditText) findViewById(R.id.Name_EditText);
        EditText email = (EditText) findViewById(R.id.Email_SignUp_EditText);
        EditText password = (EditText) findViewById(R.id.Password_SignUp_EditText);


            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("SIGN IN WAS SUCCESSFUL  :((((");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUserProfile(name.getText().toString() + " " + last_name.getText().toString());
                                //user.updateProfile(new UserProfileChangeRequest.Builder);

                                User newUser = new User();
                                newUser.id = user.getUid();
                                newUser.auth = 0;
                                newUser.firstName = name.getText().toString();
                                newUser.lastName = last_name.getText().toString();
                                newUser.email = email.getText().toString();

                                // User data should be complete here, now validating
                                if(!(validateData(view, newUser))){return;}

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users");

                                myRef.child(newUser.id).setValue(newUser);


                                Intent main = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(main);
                                finish();
                            } else {
                                //fail
                                System.out.println("SIGN IN FAILED   :((((");
                                TextView loginFail = (TextView) findViewById(R.id.Sign_Up_Error_Message);
                                loginFail.setText(task.getException().getMessage());
                            }
                        }
                    });
    }

    private boolean validateData(View view, User user){
        //Check if user has names
        if(user.firstName.trim().isEmpty() || user.lastName.trim().isEmpty()){return false;}
        //Check if user id is empty (if for any reason)
        if(user.id.trim().isEmpty()){return false;}
        //Check if user has inputted an email
        if(user.email.trim().isEmpty()){return false;}
        //Check if Auth values are valid
        if(user.auth != 0){return false;}
        else if(user.auth != 1){return false;}
        return true;
    }
}