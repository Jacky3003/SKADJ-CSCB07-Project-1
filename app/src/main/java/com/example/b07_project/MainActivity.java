package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = this.getIntent().getIntExtra("auth", 0);
        //System.out.println("Main AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(auth);
    }



    public void transitionToAddEvent(View view)
    {



        Intent addVenue = new Intent(this, ChooseVenue.class);
        addVenue.putExtra("auth", auth);


        startActivity(addVenue);

    }

    public void transitionToAdminActivity(View view){
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }
    public void transitionToAddVenue(View view){
        Intent intent = new Intent(this, AddVenue.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToUpComingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsDriver.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToActivityDenny(View view){
        Intent intent = new Intent(this, activityPageDenny.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToProfile(View view){
        Intent intent = new Intent(this, profile.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }
    public void transitionToLogin(View view)
    {
        Intent addEvent = new Intent(this, LoginActivity.class);
        addEvent.putExtra("auth", auth);
        startActivity(addEvent);
    }

    public void transitionToSignUp(View view){
        Intent addEvent = new Intent(this, SignUpActivity.class);

        addEvent.putExtra("auth", auth);
        startActivity(addEvent);
    }

    public void transitionToVenuePage(View view){
        Intent intent = new Intent(this, VenuePageDennt.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToNewEvent(View view){
        Intent intent = new Intent(this, AddEventDenny.class);
        intent.putExtra("auth", auth);
        startActivity(intent);

    }
}