package com.example.kijjapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the NewBookingsActivity Class
 * @authors: Team KIJJ
 */

public class NewBookingsActivity extends AppCompatActivity {

    private TextView tv;
    private SeekBar seekBar;
    private String breed;
    private String wantDog;
    private String wantCat;
    private String wantOther;
    private String wantAll;
    private double maxLong;
    private double minLong;
    private double maxLat;
    private double minLat;
    private double rating;

    //public static final String URL_bookingInfo = "http://kijj.cs.loyola.edu/model/getValidBookings.php";
    public static final String URL_bookingInfo = "http://klmatrangola.cs.loyola.edu/kijjTesting/getValidBookings.php";
    //public static final String URL_doApply = "http://kijj.cs.loyola.edu/model/doApply.php";
    public static final String URL_doApply = "http://klmatrangola.cs.loyola.edu/kijjTesting/doApply.php";
    private ArrayList<Booking> validBookingsList = new ArrayList<>();
    String[] ownerInfo;
    String[] ownerAddress;
    String[] ownerZCS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbooking);
        seekBar = findViewById(R.id.distance_bar);
        tv = findViewById(R.id.distanceView);

        //the seek bar = the distance bar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv.setText(String.valueOf(seekBar.getProgress()) + " mi");
            }
        });
    }

    /**
     * Method to go to profile view
     */
    public void goToProfile(View view) {
        Intent intent = new Intent(NewBookingsActivity.this, LearnMoreActivity.class);
        intent.putExtra("key", Arrays.toString(ownerInfo));
        intent.putExtra("key2", Arrays.toString(ownerAddress));
        intent.putExtra("key3", Arrays.toString(ownerZCS));
        startActivity(intent);
    }

    /**
     * Method to go to new back to previous view (home)
     */
    public void goBack(View view) {
        finish();
    }

    /**
     * method to search for filtered bookings
     */
    public void search(View view) {
        //get info
        CheckBox dogBox = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox catBox = (CheckBox) findViewById(R.id.checkBox2);
        EditText OtherEt = (EditText) findViewById(R.id.checkBox3);
        CheckBox allBox = (CheckBox) findViewById(R.id.checkBoxAll);
        EditText breedET = (EditText) findViewById(R.id.checkBox4);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        SeekBar distanceBar = (SeekBar) findViewById(R.id.distance_bar);

        float[] r = new float[]{ ratingBar.getRating() };

        //handle rating
        rating = r[0];
        Log.w("MA","rating=" + rating);

        //handle type input
        if (dogBox.isChecked()) {
            wantDog = "Dog";
        } else {
            wantDog = "noDog";
        }
        if (catBox.isChecked()) {
            wantCat = "Cat";
        } else {
            wantCat = "NoCat";
        }
        if (allBox.isChecked()) {
            wantAll = "All";
        } else {
            wantAll = "NotAll";
        }
        wantOther = OtherEt.getText().toString();
        Log.w("MA", "wantOther: " + wantOther);


        //handle breed input
        if (breedET.getText().toString().trim().length() > 0) {
            breed = breedET.getText().toString();
        } else {
            breed = "blank";
        }
        Log.w("MA", "Breed=" + breed);

        int dist = distanceBar.getProgress();
        double distance = dist;
        Log.w("MA", "Dist" + distance);

        maxLong = MainActivity.sitter.getLongi() + distance;
        Log.w("MA", "max Long" + maxLong);
        minLong = MainActivity.sitter.getLongi() - distance;
        Log.w("MA", "min Long" + minLong);
        maxLat = MainActivity.sitter.getLat() + distance;
        Log.w("MA", "max Lat" + maxLat);
        minLat = MainActivity.sitter.getLat() - distance;
        Log.w("MA", "min Lat" + minLat);

        ThreadTaskValidBookings taskAllBookings = new ThreadTaskValidBookings(this);
        taskAllBookings.start();
        Log.w("MA", "searching...");
    }

    /**
     * Method to update all the bookings
     * @param s
     */
    public void updateAllBookings(String s) {
        final ListView searchListView;
        searchListView = (ListView) findViewById(R.id.SearchListView);
        validBookingsList.clear();

        String endDate = null;
        String startDate = null;
        String name = null;
        String type = null;
        try {

            Log.w("MA", "All of them: " + s);
            JSONArray jsonArray = new JSONArray(s);
            int numBookings = jsonArray.length();
            Log.w("MA", "num Bookings: " + numBookings);
            for (int i = 0; i < numBookings; i++) {

                // JSONObject jsonObjecti = jsonArray.getJSONArray(i);
                JSONObject jsonObji = new JSONObject(jsonArray.getString(i));
                String bookingi_str = jsonObji.getString("booking");

                JSONObject bookingi = new JSONObject(bookingi_str);

                JSONObject owneri = jsonObji.getJSONObject("owner");

                PetOwner tempOwner = new PetOwner(bookingi.getString("ownerEmail"), owneri.getString("first"), owneri.getString("last"),
                        owneri.getString("address"), owneri.getString("city"), owneri.getString("state"), owneri.getInt("zip"),
                        owneri.getDouble("lat"), owneri.getDouble("long"), owneri.getString("desc"), owneri.getDouble("rating"), owneri.getString("type"),
                        owneri.getString("breed"), owneri.getString("petName"));

                Booking tempBooking = new Booking(bookingi.getInt("id"), MainActivity.sitter, tempOwner,
                        bookingi.getString("start"), bookingi.getString("end"), bookingi.getString(("status")));


                validBookingsList.add(tempBooking);
            }

        } catch (
                JSONException jsone) {
            Log.w("MA", "JSON exception: " + jsone.getMessage());
        }

        Log.w("MA", "now Have: " + validBookingsList.size());
        ownerInfo = new String[validBookingsList.size()];
        ownerAddress = new String[validBookingsList.size()];
        ownerZCS = new String[validBookingsList.size()];
        final String[] test = new String[validBookingsList.size()];
        for (int j = 0; j < validBookingsList.size(); j++) {
            startDate = validBookingsList.get(j).getStartDate();
            endDate = validBookingsList.get(j).getStartDate();
            name = validBookingsList.get(j).getPetOwner().getPetName();
            type = validBookingsList.get(j).getPetOwner().getType();
            String stat = validBookingsList.get(j).getStatus();

            //test = new String[]{String.valueOf(bookingsList.size()), "Name: " + name + "\nType: " + type + "\nStart Date: " + startDate + "\nEnd Date: " + endDate, "test"};
            test[j] = "Name: " + name + "\nType: " + type + "\nStart Date: " + startDate + "\nEnd Date: " + endDate + "\nStatus: " + stat;
            //error here
            ownerInfo[j] = validBookingsList.get(j).getPetOwner().getFirst() + " " + validBookingsList.get(j).getPetOwner().getLast();
            ownerAddress[j] = validBookingsList.get(j).getPetOwner().getAddress();
            ownerZCS[j] = validBookingsList.get(j).getPetOwner().getCity() + ", " + validBookingsList.get(j).getPetOwner().getState() + ", "
                    + validBookingsList.get(j).getPetOwner().getZip();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SearchCustomAdapter whatever = new SearchCustomAdapter((Activity) searchListView.getContext(), test);
                searchListView.setAdapter(whatever);
            }
        });
    }


    /**
     * Getters and setters
     */
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getWantDog() {
        return wantDog;
    }

    public void setWantDog(String wantDog) {
        this.wantDog = wantDog;
    }

    public String getWantCat() {
        return wantCat;
    }

    public void setWantCat(String wantCat) {
        this.wantCat = wantCat;
    }

    public String getWantOther() {
        return wantOther;
    }

    public void setWantOther(String wantOther) {
        this.wantOther = wantOther;
    }

    public String getWantAll() {
        return wantAll;
    }

    public void setWantAll(String wantAll) {
        this.wantAll = wantAll;
    }

    public double getMaxLong() {
        return maxLong;
    }

    public void setMaxLong(double maxLong) {
        this.maxLong = maxLong;
    }

    public double getMinLong() {
        return minLong;
    }

    public void setMinLong(double minLong) {
        this.minLong = minLong;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Method to go to the owner's profile
     * @param view
     */
    public void goToOwnerProfile(View view) {
        final ListView listView;
        //listView = (ListView) findViewById(R.id.listView);
        //find out which one was clicked on (index)
        View parentRow = (View) view.getParent();
        listView = (ListView) parentRow.getParent();
        int position = listView.getPositionForView(parentRow);
        Log.w("MA", "Position: " + position);
        //find info from that index in the booking list
        String ownerName = validBookingsList.get(position).getPetOwner().getFirst() +  " " + validBookingsList.get(position).getPetOwner().getLast();
        // ownerInfo[position] = bookingsList.get(position).getPetOwner().getFirst() + " " + bookingsList.get(position).getPetOwner().getLast();
        String ownerAddress = validBookingsList.get(position).getPetOwner().getAddress();
        String ownerZCS = validBookingsList.get(position).getPetOwner().getCity() + ", " + validBookingsList.get(position).getPetOwner().getState() + ", "
                + validBookingsList.get(position).getPetOwner().getZip();
        Intent intent = new Intent(NewBookingsActivity.this,OwnerProfileActivity.class);
        intent.putExtra("key", ownerName);
        intent.putExtra("key2", ownerAddress);
        intent.putExtra("key3", ownerZCS);
        //intent.putExtra("owner", )
        startActivity(intent);
    }

    /**
     * Method to apply for the open booking
     * @param view
     */
    public void doApply(View view) {
        final ListView listView;
        //listView = (ListView) findViewById(R.id.listView);
        //find out which one was clicked on (index)
        View parentRow = (View) view.getParent();
        listView = (ListView) parentRow.getParent();
        int position = listView.getPositionForView(parentRow);
        Log.w("MA", "Position: " + position);
        //find info from that index in the booking list
        int bookingId = validBookingsList.get(position).getId();
        ThreadTaskDoApply taskApply = new ThreadTaskDoApply(this, bookingId);
        taskApply.start();
        //Button applyB = (Button) listView.getItemAtPosition(position);
       // Button applyB = (Button) findViewById(R.id.apply);
      //  applyB.setText("Applied");


        //validBookingsList.remove(position);
        //listView.add(position, "followed");

    }
}

