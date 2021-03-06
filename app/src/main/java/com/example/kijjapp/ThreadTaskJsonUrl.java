package com.example.kijjapp;

import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * This is the ThreadTaskJsonUrl Class
 * @authors: Team KIJJ
 */

public class ThreadTaskJsonUrl extends Thread {
    private LoginActivity activity;
    private UpcomingActivity act;
    private View view;

    public ThreadTaskJsonUrl( LoginActivity fromActivity, View view ) {
        activity = fromActivity;
        this.view = view;
    }

    /**
     * Method to run JSON, connects to database
     */
    public void run( ) {
        // update View
        Log.w( "MA", "Inside run JSON" );

        try {
            // create a URL
            URL url = new URL(LoginActivity.URL_JSON + "?email="+ MainActivity.email);
            // create an input stream for the URL
            InputStream is = url.openStream();
            // read from that input stream
            Scanner scan = new Scanner( is );
            String s = "";
            while( scan.hasNext( ) ) {
                s += scan.nextLine( );

                // s is expected to be a JSON string
            }
            activity.updateSitterWithJSON(s, view);
            //act.updateBookingsArray(s);
        } catch( Exception e ) {
            Log.w( "MA", "exception json: " + e.getMessage() );
        }
    }
}

