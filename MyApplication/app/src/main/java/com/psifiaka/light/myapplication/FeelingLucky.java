package com.psifiaka.light.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class FeelingLucky extends AppCompatActivity {

    Button buttonPlay;

    EditText editTextNumbers;

    @SuppressLint("StaticFieldLeak")
    public static TextView textViewMatchingNums;
    @SuppressLint("StaticFieldLeak")
    public static TextView textViewProfit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_lucky);


        buttonPlay = findViewById(R.id.buttonPlayKino);

        editTextNumbers = findViewById(R.id.editTextNumbers);

        textViewMatchingNums = findViewById(R.id.textViewMatchingNums);

        textViewProfit = findViewById(R.id.textViewProfit);

        buttonPlay.setOnClickListener(v -> {

            PlayKinoFive process = new PlayKinoFive();

            String playerNumbers = editTextNumbers.getText().toString();

            System.out.println(playerNumbers);

            ArrayList<String> userNumbersArrayList = new ArrayList<>();

            userNumbersArrayList.addAll(Arrays.asList(playerNumbers.split(" ")));


            try {
                if (!hasDuplicate(userNumbersArrayList)) {
                    if (!hasMoreThanFiveNumbers(userNumbersArrayList)) {
                        if(!hasOutOfRangeValues(userNumbersArrayList)) {
                            process.execute(Integer.parseInt(userNumbersArrayList.get(0)), Integer.parseInt(userNumbersArrayList.get(1)),
                                    Integer.parseInt(userNumbersArrayList.get(2)), Integer.parseInt(userNumbersArrayList.get(3)),
                                    Integer.parseInt(userNumbersArrayList.get(4)));
                        }
                        else {
                            editTextNumbers.setText("Out of Range [1-80]");
                        }
                    }
                    else {
                        editTextNumbers.setText("No more than 5 Numbers");
                    }
                }
                else {
                    editTextNumbers.setText("No Duplicate Numbers!");
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                editTextNumbers.setHint("Separate with Space!!!");
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        });


    }

    public boolean hasDuplicate(ArrayList<String> items) {
        Set<String> appeared = new HashSet<>();
        for (String item : items) {
            if (!appeared.add(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMoreThanFiveNumbers(ArrayList<String> items) {
        return items.size() > 5;
    }


    public Predicate<ArrayList> isValidUserEntry() {
        return myList -> myList.size() == 5;
    }

    public boolean hasOutOfRangeValues(ArrayList<String> items) {
        for (String item : items) {
            if(Integer.parseInt(item) > 80 || Integer.parseInt(item) < 1) {
                return true;
            }
        }
        return false;
    }


}
