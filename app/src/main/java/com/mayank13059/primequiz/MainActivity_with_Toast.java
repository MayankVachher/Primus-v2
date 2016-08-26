package com.mayank13059.primequiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by D4RKF0RCE on 15/08/16.
 */
public class MainActivity_with_Toast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_toast);

        TextView numberDisplayed = (TextView) findViewById(R.id.numberDisplayed);
        numberDisplayed.setText(genRandomNumber()+"");

        Button yesButton = (Button) findViewById(R.id.button_yes);
        yesButton.setOnClickListener(isPrimeListener);
        Button noButton = (Button) findViewById(R.id.button_no);
        noButton.setOnClickListener(isPrimeListener);
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setOnClickListener(nextButtonListener);

    }

    private int genRandomNumber() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(1000) + 1;
    }

    private View.OnClickListener isPrimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView numberDisplayed = (TextView) findViewById(R.id.numberDisplayed);
            Integer numberToCheck = Integer.parseInt(numberDisplayed.getText().toString());

            Boolean result = checkPrime(numberToCheck);

            switch (v.getId()) {
                case R.id.button_yes:
                    if(result) {
                        Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                        Log.i("Primus", "Correct!");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                        Log.i("Primus", "Incorrect!");
                    }
                    break;
                case R.id.button_no:
                    if(result) {
                        Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                        Log.i("Primus", "Incorrect!");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                        Log.i("Primus", "Correct!");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private Boolean checkPrime(int num) {

        if(num <= 2 || num%2 == 0) {
            return Boolean.FALSE;
        }

        for(int i = 3; i*i <= num; i+=2) {
            if((num % i) == 0) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private View.OnClickListener nextButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView number__primeNumberDisplay = (TextView) findViewById(R.id.numberDisplayed);
            number__primeNumberDisplay.setText(genRandomNumber()+"");
        }
    };
}
