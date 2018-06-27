package com.psifiaka.light.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FetchResults extends AsyncTask<String, Void, Void> {

    public static String data = "";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(String... param) {

        try {
            URL url = new URL(String.join("", param));

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = String.format("%s%s", data, line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            OpapREST.stringOfFetchResults = data;
            OpapREST.drawDateList = findAllDrawDates(data);
            OpapREST.drawNoList = findAllDrawNo(data);
            OpapREST.drawResultsList = findAllDrawResults(data);

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        data="";
    }



    //----------------------------------------------------------------------------------------------
    // Helping methods to split data without using Json Objects/Array etc...

    public List<String> findAllDrawDates(String text) {
        List<String> list = new ArrayList<>();

        String pattern1 = "drawTime\":\"";
        String pattern2 = "\",\"drawNo\":";

        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {

            list.add(m.group(1));
        }

        return list;
    }

    public List<String> findAllDrawNo(String text) {
        List<String> list = new ArrayList<>();

        String pattern1 = ",\"drawNo\":";
        String pattern2 = ",\"results";

        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {

            list.add(m.group(1));
        }

        return list;
    }

    public List<String> findAllDrawResults(String text) {
        List<String> list = new ArrayList<>();

        String pattern1 = "results\":[";
        String pattern2 = "]}";

        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {

            list.add(m.group(1).replace(",","-"));
        }

        return list;
    }

}
