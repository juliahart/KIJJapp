package com.example.kijjapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.DateValueSanitizer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * This is the UpcomingActivity Class
 * @authors: Team KIJJ
 */

public class UpcomingActivity extends AppCompatActivity {
    // public static final String URL_upcomingInfo = "http://kijj.cs.loyola.edu/model/bookingInfo.php";
    public static final String URL_upcomingInfo = "http://klmatrangola.cs.loyola.edu/kijjTesting/bookingInfo.php";

    // public static final String URL_points = "http://kijj.cs.loyola.edu/model/changePoints.php";
    public static final String URL_points= "http://klmatrangola.cs.loyola.edu/kijjTesting/changePoints.php";

    // public static final String URL_removeBooking = "http://kijj.cs.loyola.edu/model/removeBooking.php";
    public static final String URL_removeBooking = "http://klmatrangola.cs.loyola.edu/kijjTesting/removeBooking.php";

    String[] sd;
    String[] ed;
    int length;
    private ArrayList<Booking> bookingsList = new ArrayList<>();
    String[] ownerInfo;
    String[] ownerAddress;
    String[] ownerZCS;
    private String ownerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        ThreadTaskUpcomingBookings taskUpcomingBookings = new ThreadTaskUpcomingBookings(this);
        taskUpcomingBookings.start();
    }


    /**
     * Method to go to profile view
     */
    public void goToProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        // go to home screen
        startActivity(intent);
    }


    /**
     * Method to go back to previous view
     */
    public void goBack(View view) {
        finish();
    }


    @SuppressLint("SetTextI18n")
    public void updateBookingsArray(String s) {

        final ListView listView;
        listView = (ListView) findViewById(R.id.listView);


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
                        bookingi.getString("start"), bookingi.getString("end"), bookingi.getString("status"));


                bookingsList.add(tempBooking);

            }
            final String[] test = new String[bookingsList.size()];

            ownerInfo = new String[bookingsList.size()];
            ownerAddress = new String[bookingsList.size()];
            ownerZCS = new String[bookingsList.size()];
            for (int j = 0; j < bookingsList.size(); j++) {
                Log.w("MA", "booking id: " + bookingsList.get(j).getId());
                startDate = bookingsList.get(j).getStartDate();
                endDate = bookingsList.get(j).getEndDate();
                name = bookingsList.get(j).getPetOwner().getPetName();
                type = bookingsList.get(j).getPetOwner().getType();


                //test = new String[]{String.valueOf(bookingsList.size()), "Name: " + name + "\nType: " + type + "\nStart Date: " + startDate + "\nEnd Date: " + endDate, "test"};
                test[j] = "Name: " + name + "\nType: " + type + "\nStart Date: " + startDate + "\nEnd Date: " + endDate;
                Log.w("MA", "booking String: " + "Name: " + name + "\nType: " + type + "\nStart Date: " + startDate + "\nEnd Date: " + endDate + "test");

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CustomListAdapter whatever = new CustomListAdapter((Activity) listView.getContext(), test);
                    listView.setAdapter(whatever);
                }
            });


        } catch (
                JSONException jsone) {
            Log.w("MA", "JSON exception: " + jsone.getMessage());
        }
    }


    /**
     * Method to get the bookingsList
     * @return
     */
    public String getBookingsList() {
        return bookingsList.toString();
    }

    /**
     * Method to set bookingsList
     * @param bookingsList
     */
    public void setBookingsList(ArrayList<Booking> bookingsList) {
        this.bookingsList = bookingsList;
    }

    /**
     * Method to go to the owner's profile if the button is clicked
     * @param view
     */
    public void goToOwnerProfile(View view) {
        final ListView listView;
        View parentRow = (View) view.getParent();
        listView = (ListView) parentRow.getParent();
        int position = listView.getPositionForView(parentRow);
        Log.w("MA", "Position: " + position);
        //find info from that index in the booking list
        String ownerName = bookingsList.get(position).getPetOwner().getFirst() +  " " + bookingsList.get(position).getPetOwner().getLast();
        String ownerAddress = bookingsList.get(position).getPetOwner().getAddress();
        String ownerZCS = bookingsList.get(position).getPetOwner().getCity() + ", " + bookingsList.get(position).getPetOwner().getState() + ", "
                + bookingsList.get(position).getPetOwner().getZip();
        Intent intent = new Intent(UpcomingActivity.this,OwnerProfileActivity.class);
        intent.putExtra("key", ownerName);
        intent.putExtra("key2", ownerAddress);
        intent.putExtra("key3", ownerZCS);

        startActivity(intent);
    }


    /**
     * Method for if user is finsihed with booking, gets the amount of points they've earned
     * @param view
     */
    public void finished(View view) {
        final ListView listView;
        //listView = (ListView) findViewById(R.id.listView);
        //find out which one was clicked on (index)
        View parentRow = (View) view.getParent();
        listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        Log.w("MA", "Position: " + position);
        String startDate = bookingsList.get(position).getStartDate();
        String endDate = bookingsList.get(position).getEndDate();
        int bookingId = bookingsList.get(position).getId();
        bookingsList.remove(position);

        int dateDifference = (int) getDateDiff(new SimpleDateFormat("yyyy-MM-dd"), endDate, startDate);
        System.out.println("dateDifference: " + dateDifference);

        Log.w("MA", "diff "+ dateDifference);

       // MainActivity.sitter.addPoints(dateDifference);
        int points = MainActivity.sitter.getPoints();
        MainActivity.sitter.setPoints(points + dateDifference);
        ThreadTaskPoints threadTaskPoints = new ThreadTaskPoints(this, points);
        threadTaskPoints.start();
        Log.w("MA", "booking id to remove: " + bookingId);
        ThreadTaskRemoveBooking threadTaskRemoveBooking = new ThreadTaskRemoveBooking(this, bookingId);
        threadTaskRemoveBooking.start();


        if(dateDifference != 0) {
            Log.w("MA", "diff " + dateDifference);
            MainActivity.sitter.addPoints(dateDifference);
        }
        else if(dateDifference == 0)
        {
            Log.w("MA", "diff " + dateDifference);
            MainActivity.sitter.addPoints(1);
        }
        listView.getChildAt(position).setVisibility(View.GONE);
        listView.getChildAt(position).getLayoutParams().height = 1;
    }


    /**
     * Get the difference between the start and end dates
     * @param format
     * @param oldDate
     * @param newDate
     * @return
     */
    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


}

