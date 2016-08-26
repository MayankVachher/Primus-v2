package com.mayank13059.primequiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

/**
 * Created by Mayank Vachher (2013059) on 17/08/16.
 */
public class MainActivity_with_snackbar extends AppCompatActivity {

    private static TextView numberDisplayed;
    private CoordinatorLayout coordinatorLayout;
    private static Button yesButton, noButton, skipButton, showHintButton, cheatButton;
    private static Integer numberDisplayed_int;
    private static Boolean isHintEnabled, isCheatEnabled;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_snackbar);

        numberDisplayed = (TextView) findViewById(R.id.numberDisplayed);
        yesButton = (Button) findViewById(R.id.button_yes);
        noButton = (Button) findViewById(R.id.button_no);
        skipButton = (Button) findViewById(R.id.button_skip);
        showHintButton = (Button) findViewById(R.id.button_showHint);
        cheatButton = (Button) findViewById(R.id.button_cheat);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinatorLayout);

        if(numberDisplayed_int == null) {
            genRandomNumberAndSet(numberDisplayed);
        }
        else {
            numberDisplayed.setText(numberDisplayed_int+"");
        }

        if(isHintEnabled == null) {
            isHintEnabled = Boolean.TRUE;
        }

        if(isCheatEnabled == null) {
            isCheatEnabled = Boolean.TRUE;
        }

        if (yesButton != null) {
            yesButton.setOnClickListener(isPrimeListener);
        }
        if (noButton != null) {
            noButton.setOnClickListener(isPrimeListener);
        }
        if (skipButton != null) {
            skipButton.setOnClickListener(genNextNumberListener);
        }
        if(showHintButton != null) {
            showHintButton.setOnClickListener(showHintListener);
        }
        if(cheatButton != null) {
            cheatButton.setOnClickListener(showCheatListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123) {
            isHintEnabled = Boolean.FALSE;
        }

        if(requestCode == 456) {
            isCheatEnabled = Boolean.FALSE;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("number", numberDisplayed_int);
        outState.putBoolean("hintEnabled", isHintEnabled);
        outState.putBoolean("cheatEnabled", isCheatEnabled);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        numberDisplayed_int = savedInstanceState.getInt("number");
        isHintEnabled = savedInstanceState.getBoolean("hintEnabled");
        isCheatEnabled = savedInstanceState.getBoolean("cheatEnabled");
    }

    private Integer parseInteger(TextView numberDisplayed) {
        Integer numberToCheck = null;
        if (numberDisplayed != null) {
            return Integer.parseInt(numberDisplayed.getText().toString());
        }
        return null;
    }

    private View.OnClickListener isPrimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView numberDisplayed = (TextView) findViewById(R.id.numberDisplayed);
            Integer numberToCheck = parseInteger(numberDisplayed);

            if(numberToCheck == null) {
                return;
            }

            Boolean result = checkPrime(numberToCheck);
            String result_value = "The number "+numberToCheck+" is ";
            int result_color = 0;

            switch (v.getId()) {
                case R.id.button_yes:
                    if(result) {
                        result_color = ContextCompat.getColor(getApplicationContext(), R.color.colorSuccess);
                        result_value += "Prime. Correct!";
//                        Log.i("Primus", "Correct!");
                    }
                    else {
                        result_color = ContextCompat.getColor(getApplicationContext(), R.color.colorFailure);
//                        Log.i("Primus", "Incorrect!");
                        result_value += "not Prime. Incorrect!";
                    }
                    break;
                case R.id.button_no:
                    if(result) {
                        result_color = ContextCompat.getColor(getApplicationContext(), R.color.colorFailure);
//                        Log.i("Primus", "Incorrect!");
                        result_value += "Prime. Incorrect!";
                    }
                    else {
                        result_color = ContextCompat.getColor(getApplicationContext(), R.color.colorSuccess);
//                        Log.i("Primus", "Correct!");
                        result_value += "not Prime. Correct!";
                    }
                    break;
                default:
                    break;
            }
            Snackbar resultSnackbar = Snackbar.make(coordinatorLayout, result_value, Snackbar.LENGTH_SHORT);
            resultSnackbar.getView().setBackgroundColor(result_color);
            resultSnackbar.show();
            genRandomNumberAndSet(numberDisplayed);
        }
    };

    private View.OnClickListener genNextNumberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            genRandomNumberAndSet(numberDisplayed);
            isHintEnabled = Boolean.TRUE;

        }
    };

    private View.OnClickListener showHintListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!isHintEnabled) {
                Toast.makeText(getApplicationContext(), "Hint already used!", Toast.LENGTH_SHORT).show();
            }
            else {
                isHintEnabled = Boolean.FALSE;
                Intent showHintIntent = new Intent(getApplicationContext(), HintActivity.class);
                startActivityForResult(showHintIntent, 123);
            }
        }
    };

    private View.OnClickListener showCheatListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!isCheatEnabled) {
                Toast.makeText(getApplicationContext(), "Cheat already used!", Toast.LENGTH_SHORT).show();
            }
            else {
                isCheatEnabled = Boolean.FALSE;
                Intent showCheatIntent = new Intent(getApplicationContext(), CheatActivity.class);
                TextView numberDisplayed = (TextView) findViewById(R.id.numberDisplayed);
                Integer numberToCheck = parseInteger(numberDisplayed);

                if (numberToCheck == null) {
                    return;
                }

                Boolean result = checkPrime(numberToCheck);
                showCheatIntent.putExtra("number", numberToCheck);
                showCheatIntent.putExtra("isPrime", result);
                startActivityForResult(showCheatIntent, 456);
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

    private void genRandomNumberAndSet(TextView numberDisplayed) {
        Random randomGenerator = new Random();
        numberDisplayed_int = randomGenerator.nextInt(1000) + 1;
        numberDisplayed.setText(numberDisplayed_int+"");
    }
}
