package com.example.b07_project;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Navigation extends AppCompatActivity implements View.OnClickListener{
    String userId;
    @Override
    public void onClick(View view)
    {
        UserServices userServices = new UserServices();
        System.out.println(view.getId());
        switch (view.getId())
        {
            case R.id.homeButton:
                Intent intent1 = new Intent(view.getContext(), VenuePageDennt.class);
                //intent1.putExtra("auth", intent1.g;
                userId = getIntent().getStringExtra("userId");
                if (userId != null)intent1.putExtra("userId", userId);
                System.out.println("BRUH");
                view.getContext().startActivity(intent1);
                finish();

                break;

            case R.id.profileButton:
                Intent intent2 = new Intent(view.getContext(), profile.class);
               //intent2.putExtra("auth", auth);
                System.out.println("lmao");
                view.getContext().startActivity(intent2);
                finish();
                break;
            case R.id.logOutButton:
            case R.id.signOutButton:
                userServices.logOutCurrentUser();
                finish();
                break;


        }
    }
}