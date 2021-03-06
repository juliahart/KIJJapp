package com.example.kijjapp;

import android.util.Log;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


import android.util.Log;

/**
 * This is the ThreadTaskValidBookings Class
 * @authors: Team KIJJ
 */

public class ThreadTaskValidBookings extends Thread{
    private NewBookingsActivity activity;
    ListView listView;

    public ThreadTaskValidBookings(NewBookingsActivity fromActivity) {
        activity = fromActivity;
    }

    /**
     * Method to get valid bookings, connects to database
     */
    public void run( ) {
        // update View
        Log.w( "MA", "Inside run upcoming" );

        try {
            // create a URL
            URL url = new URL(NewBookingsActivity.URL_bookingInfo + "?breed="+activity.getBreed() + "&dog=" + activity.getWantDog() + "&cat=" + activity.getWantCat() + "&other="+activity.getWantOther() +
                    "&all=" +activity.getWantAll() + "&rating="+ activity.getRating() + "&minLat=" + activity.getMinLat() + "&maxLat=" + activity.getMaxLat() + "&minLong=" + activity.getMinLong() + "&maxLong=" + activity.getMaxLong());
            // create an input stream for the URL
            InputStream is = url.openStream();
            // read from that input stream
            Scanner scan = new Scanner( is );
            String s = "";
            while( scan.hasNext( ) ) {
                s += scan.nextLine( );

                // s is expected to be a JSON string
            }
            // Log.w("booking Array: ", s);
            activity.updateAllBookings(s);
            // activity.createList(s);
        } catch( Exception e ) {
            Log.w( "MA", "exception json: " + e.getMessage() );
        }
    }
}
