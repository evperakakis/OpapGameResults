package com.psifiaka.light.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayKinoFive  extends AsyncTask<Integer, Void, Void> {

//    List<Integer> kinoNumbers = new ArrayList<>();
    List<Integer> matchingNumbers = new ArrayList<>();
    int totalProfit = 0;
    String data = "";



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Integer... integers) {

        try {
            URL url = new URL("http://applications.opap.gr/DrawsRestServices/kino/last.json");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                System.out.println(line);
                data = String.format("%s%s", data, line);
            }

            JSONObject json = new JSONObject(data);
            JSONObject draw = json.getJSONObject("draw");
            JSONArray kinoResults = draw.getJSONArray("results");

            Integer[] userNumbers = integers.clone();
            Integer[] kinoNumbers = new Integer[20];

            if (kinoResults != null) {
                for (int i=0; i<kinoResults.length(); i++){
                    kinoNumbers[i] = Integer.valueOf((kinoResults.getString(i)));
                }
            }

            for (int i=0; i<userNumbers.length; i++){
                if (Arrays.asList(kinoNumbers).contains(userNumbers[i])){

                    matchingNumbers.add(userNumbers[i]);

                }
            }

            System.out.println(matchingNumbers);
            System.out.println(matchingNumbers.size());




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        String numbersToPost = matchingNumbers.toString();

        String profitToPost = String.valueOf(matchingNumbers.size()*5);

        FeelingLucky.textViewMatchingNums.setText(numbersToPost);

        FeelingLucky.textViewProfit.setText(profitToPost + " Euros");

    }
}
