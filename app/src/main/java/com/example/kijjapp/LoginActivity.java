package com.example.kijjapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /*
     * Method to go to the home view
     */
    public void goToHomePage(View view)
    {
        Intent intent = new Intent( this, HomeActivity.class );
        startActivity( intent );
    }

    /*
     * Method to go back to previous view (starting view)
     */
    public void goBack(View view){
        finish();
    }

}
