package com.mayank13059.primequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Mayank Vachher (2013059) on 26/08/16.
 */
public class CheatActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheat_display);

        Intent intent = getIntent();
        Integer numberToBeDisplayed = intent.getIntExtra("number", 0);
        Boolean isPrime = intent.getBooleanExtra("isPrime", Boolean.FALSE);

        TextView num = (TextView) findViewById(R.id.cheatNumberDisplayed);
        num.setText(numberToBeDisplayed+"");
        TextView prime = (TextView) findViewById(R.id.cheat_isPrime);

        if(isPrime) {
            prime.setText("is a prime number!");
        }
        else {
            prime.setText("is not a prime number!");
        }
    }
}