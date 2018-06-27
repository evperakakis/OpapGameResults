package com.psifiaka.light.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OpapREST extends AppCompatActivity {

    EditText game, date;
    TextView username;
    Button fetchButton, buttonLucky;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    List<ListItem> listItems;

    public static List<String> drawDateList;
    public static List<String> drawNoList;
    public static List<String> drawResultsList;

    public static String stringOfFetchResults = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opap_rest_main);

        Intent intentFromMain = getIntent();
        String providedUsername = intentFromMain.getStringExtra("providedUsername");


        username = findViewById(R.id.textViewUsername);

        game = findViewById(R.id.editTextGame);

        date = findViewById(R.id.editTextDate);

        fetchButton = findViewById(R.id.buttonFetchData);

        buttonLucky = findViewById(R.id.buttonLucky);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        username.setText(providedUsername);

        date.setOnClickListener(v -> {

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    OpapREST.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener, year, month, day);

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (view, year, month, day) -> {
            month++;
            String newDate = day + "-" + month + "-" + year;

            date.setText(newDate);
        };


        fetchButton.setOnClickListener(v -> {

            FetchResults process = new FetchResults();

            String gameChoice = game.getText().toString();

            String dateValue = date.getText().toString();

            String desiredUrl;

            System.out.println("DateValue: " + dateValue);

            if (dateValue.equals("")) {
                desiredUrl = ("http://applications.opap.gr/DrawsRestServices/" + gameChoice + "/last.json");
            } else {
                desiredUrl = ("http://applications.opap.gr/DrawsRestServices/" + gameChoice + "/drawDate/" + dateValue + ".json");
            }


            try { // using waitObject to make sure async process has been executed
                Object waitObject = process.execute(desiredUrl).get();
            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println("PRINT String of fetch results");
            System.out.println(stringOfFetchResults);

            listItems = new ArrayList<>();

            for (int i = 0; i < drawDateList.size(); i++) {

                ListItem listItem = new ListItem((i + 1) + ") " + "Draw DateTime: " + drawDateList.get(i), "Draw Number: " + drawNoList.get(i),
                        "Draw Results: " + drawResultsList.get(i));

                listItems.add(listItem);
            }

            adapter = new RecyclerViewAdapter(listItems, this);

            recyclerView.setAdapter(adapter);


        });

        buttonLucky.setOnClickListener(v -> {

            Intent FeelingLuckyIntent = new Intent(OpapREST.this, FeelingLucky.class);

            startActivity(FeelingLuckyIntent);
        });

    }
}
