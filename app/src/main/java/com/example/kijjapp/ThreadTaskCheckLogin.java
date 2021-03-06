package com.example.kijjapp;

/**
 * This is the ThreadTaskCheckLogin Class
 * @authors: Team KIJJ
 */

import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
public class ThreadTaskCheckLogin extends Thread {
        private LoginActivity activity;
        private UpcomingActivity act;
        private View view;

        public ThreadTaskCheckLogin(LoginActivity fromActivity, View view) {
            activity = fromActivity;
            this.view = view;
        }

    /**
     * Method to run login, connects to database
     */
    public void run( ) {
            // update View
            Log.w( "MA", "Inside run URL" );

               try {
                //create a URL
                   String email = MainActivity.email;
                   String pass = activity.getPass();
                   URL url = new URL(LoginActivity.CheckLoginURL  + "?email=" +email +"&password="+pass);
                   URLConnection connection = url.openConnection();
                   connection.setDoOutput( true );
                   // create an input stream for the URL
                   InputStream is = url.openStream();
                   // read from that input stream
                   Scanner scan = new Scanner( is );
                   String s = "";
                   while( scan.hasNext( ) ) {
                       s += scan.nextLine( );
                   }
                   activity.isValidLogin(s,view);
               } catch( Exception e ) {
                    Log.w( "MA", "exception Login check: " + e.getMessage() );
              }
        }
}
