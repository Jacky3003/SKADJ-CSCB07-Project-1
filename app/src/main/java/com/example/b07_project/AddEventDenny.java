package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class AddEventDenny extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int selectedVenueId;
    String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_denny);

        selectedVenueId = -1;

        firstName = getIntent().getStringExtra("firstName");
        if(firstName != null){
            TextView textView = findViewById(R.id.profileUserName);
            textView.setText(firstName);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Venues");
        getdata();
    }

    private void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> list = new ArrayList<String>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Venue venue = snap.getValue(Venue.class);
                    list.add(venue.getName());
                }

                Spinner sports = (Spinner) findViewById(R.id.eventVenueSpinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, list);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sports.setAdapter(adapter);
                initializeSports();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }

    private void setSportDropdown(ArrayList<String> sportList) {


        Spinner sports = (Spinner) findViewById(R.id.eventSportSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, sportList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sports.setAdapter(adapter);

    }

    public void initializeSports() {
        ArrayList<String> list = new ArrayList<String>();
        databaseReference = firebaseDatabase.getReference("Venues");


        Spinner sports = (Spinner) findViewById(R.id.eventVenueSpinner);
        sports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> list = new ArrayList<String>();
                        System.out.println(snapshot.getChildrenCount());
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Venue venue = snap.getValue(Venue.class);
                            System.out.println(venue.getSports());
                            if (venue.getName() == parent.getItemAtPosition(position).toString()) {
                                setSportDropdown(venue.getSports());
                                selectedVenueId = venue.getId();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error);
                    }
                });
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }






















    public void createDennyEvent(View view)
    {
        System.out.println("someone clicked the button");
        final int[] curMaxId = {-1}; //java made me use a final length 1 array instead of an integer idk y



        EditText editText = (EditText) findViewById(R.id.eventNameDenny);
        String eventName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.eventCapacityDenny);
        Event event = new Event(eventName);

        if (editText.getText().toString() != null)event.capacity = Integer.parseInt(editText.getText().toString());
        editText= (EditText) findViewById(R.id.eventDescriptionDenny);
        event.eventDescription= editText.getText().toString();
        TimePicker timePicker = (TimePicker) findViewById(R.id.eventStartTimeDenny);

        DatePicker datePicker = (DatePicker) findViewById(R.id.eventDateDenny);

        event.setStartDate(new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
        timePicker = (TimePicker) findViewById(R.id.eventEndTimeDenny);
        event.setEndDate(new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));


        Spinner sports = (Spinner) findViewById(R.id.eventSportSpinner);

        event.sport = sports.getSelectedItem().toString();
        event.venueId = selectedVenueId;
        event.ownerId = getIntent().getStringExtra("userId");
        if (event.ownerId == null) event.ownerId = "default";


        if (!validateEvent(view, event)) return;



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");
        Log.i("demo", "honestly idk");




        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            int tempId;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("why do we exist? just to suffer?");
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        tempId = childSnapshot.getValue(Event.class).id;
                        Log.i("demo", "current max id: " + curMaxId[0]);
                        if (tempId >= curMaxId[0]) curMaxId[0] = tempId;
                        Log.i("demo", "id = " + tempId);
                        Log.i("demo", "new current max id: " + curMaxId[0]);
//
//
//                    tempId = task.getResult().getValue();
//                    Log.i("demo", "id = " + tempId);
//                    if (tempId >= curMaxId[0]) curMaxId[0] = tempId;

                    }
                    //Log.i("demo", task.getResult().getValue().toString());

                    event.id=curMaxId[0]+1;
                    //event.ownerId = 0; //temporary until we make user class


                    myRef.child(event.id + "").setValue(event);

                    DatabaseReference venueRef = database.getReference("Venues");
                    venueRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            for (DataSnapshot childSnapShot : task.getResult().getChildren()){
                                Venue venue = childSnapShot.getValue(Venue.class);
                                if (venue.scheduledEvents == null) venue.scheduledEvents = new ArrayList<Integer>();
                                if (venue.getId() == event.getVenueId()) {
                                    venue.scheduledEvents.add(event.getId());
                                    //adds event id to venue's list of scheduled event id's
                                    venueRef.child(venue.getId() + "").setValue(venue);
                                    //updates said venue in database
                                }

                            }
                        }
                    });



                }
            }
        });



        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    private void makePopUp(View view, String message) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            popupWindow.setElevation(20);
//        }

        TextView textView = (TextView) popupView.findViewById(R.id.popup_text);


        textView.setText(message);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private boolean validateEvent(View view, Event event) {
        Log.i("status", "validating event");
        if (event.getStartTimeStamp() < Instant.now().toEpochMilli()) {
            makePopUp(view, "Invalid start date");
            return false;
        }



        if (event.getEndTimeStamp() < event.getStartTimeStamp()) {
            makePopUp(view, "Invalid end date");
            return false;
        }

        if (event.getCapacity() < 10){
            makePopUp(view, "Capacity too low");
            Log.i("status", "capacity low, should give popup");
            return false;
        }

        if (event.getName().trim().isEmpty() || event.getName().trim() == "Name"){
            makePopUp(view, "Please give your event a name");
            return false;
        }
        if (event.getEventDescription().length() < 15){
            makePopUp(view, "Please give your event a good description.");
            return false;
        }

        return true;


    }







}
