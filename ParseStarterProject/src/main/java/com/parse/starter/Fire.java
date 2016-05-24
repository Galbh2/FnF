package com.parse.starter;


import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by gbenhaim on 5/9/16.
 */
public class Fire {

    private final String ADDRESS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?language=iw&";
    private final String KEY = "key=AIzaSyAfuZD5FPkCHj5cQ16_RuCDsQ7pQ-zVpiQ";
    private String query = "location=%s,%s&radius=%s&type=%s&";
    private final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&maxheight=100&maxwidth=100&";
    private final String SINGLE_PLACE ="https://maps.googleapis.com/maps/api/place/details/json?language=iw&placeid=%s&";
  //  private String default_query = "location=32.185533,34.854347&radius=4000&type=restaurant&key=AIzaSyAfuZD5FPkCHj5cQ16_RuCDsQ7pQ-zVpiQ";
    private String NEXT_PAGE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=%s&";
    private final String USER_AGENT = "Mozilla/5.0";
    private final int WAIT = 700;

    private String m_NextPageToken;
    private long m_LastRequestTime = 0;

/*
    public static void main(String[] args) {

        Fire f = new Fire();
        String data = f.doRequest("3000", "restaurant", "333");

        System.out.println(data);
        System.out.println(fromJsonToObjects(data));

      //  System.out.println("next page");

        // System.out.println(f.getNextPage());

    }
*/
    public InputStream downloadImage(String i_PhotoRef) throws IOException {

        String query = String.format(PHOTO_URL, i_PhotoRef) + KEY;
        //Bitmap bitmap = null;



        while ( (System.currentTimeMillis() - m_LastRequestTime) < 3000) {
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        m_LastRequestTime = System.currentTimeMillis();

        return new URL(query).openStream();
    }

    public static MyPlace fromJsonToOneObject(String i_Data){

        MyPlace p = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(i_Data);
            JsonNode result = root.path("result");

            JsonNode photoRef = result.get("photos");
            String photo = null;
            JsonNode photoEntry = null;

            /**
             * Handling empty photo array
             */
            if (photoRef != null && !photoRef.isNull()) {
                if ((photoEntry = photoRef.get(0)) != null && !photoEntry.isNull()) {
                    photo = photoEntry.get("photo_reference").asText();
                }
            }

            p = new MyPlace(
                result.get("place_id").asText(),
                result.get("name").asText(),
                result.get("vicinity").asText(),
                photo);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static ArrayList<MyPlace> fromJsonToObjects(String i_Data){

        ArrayList<MyPlace> list = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(i_Data);
            JsonNode results = root.path("results");
           // Iterator<JsonNode> jsonPlaces = results.elements();

            for (JsonNode jsonPlace : results) {

                JsonNode photoRef = jsonPlace.get("photos");
                String photo = null;
                JsonNode photoEntry = null;

                /**
                 * Handling empty photo array
                 */
                if (photoRef != null && !photoRef.isNull()) {
                    if ((photoEntry = photoRef.get(0)) != null && !photoEntry.isNull()) {
                        photo = photoEntry.get("photo_reference").asText();
                    }
                }

                MyPlace p = new MyPlace(
                    jsonPlace.get("place_id").asText(),
                    jsonPlace.get("name").asText(),
                    jsonPlace.get("vicinity").asText(),
                    photo
                );
                list.add(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public String doRequest(String i_Radius, String i_Type, String i_Latitude, String i_Longitude) {

        String query = buildQuery(i_Radius, i_Type, i_Latitude, i_Longitude);
        return fire(query);
    }

    public String doRequest(String i_Id) {
        String query = String.format(SINGLE_PLACE, i_Id) + KEY;
        Log.d("query", query);
        return fire(query);
    }

    public String getNextPage(){

        if (m_NextPageToken != null) {
            String query = String.format(NEXT_PAGE, m_NextPageToken) + KEY;
            return fire(query);
        }
        else {
            return null;
        }
    }

    private String fire(String query) {

        if ( (System.currentTimeMillis() - m_LastRequestTime) < 3000) {
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        HttpURLConnection urlConnection = (HttpURLConnection) getConnection(query);
        String response = readStream(urlConnection);
        setNextPageToken(response);

        m_LastRequestTime = System.currentTimeMillis();

        return response;
    }

    private void setNextPageToken(String i_Result) {

        if (i_Result == null) {
            return;
        }

        String pattern = "\"next_page_token\".+\n";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(i_Result);

        if (m.find()) {
            m_NextPageToken = m.group(0).split(":")[1].trim().replace("\"", "").replace(",", "");
        } else {
            m_NextPageToken = null;
        }
    }

    private String buildQuery(String i_Radius, String i_Type, String i_Latitude, String i_Longitude) {
        String s = ADDRESS + String.format(query,
                i_Latitude, i_Longitude,
                i_Radius, i_Type) + KEY;

        Log.d("query", s);

        return s;
    }

    private URLConnection getConnection(String i_Link) {

        URL url = null;
        URLConnection urlConnection = null;

        try {
            url = new URL(i_Link);
            urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent",USER_AGENT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    private String readStream(URLConnection i_Connection) {
        try {
            return readStream(i_Connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readStream(InputStream i_In) {

        BufferedReader buffer = new BufferedReader(new InputStreamReader(i_In));

        StringBuilder stringBuilder = new StringBuilder();
        String s;

        try {
            while ( (s = buffer.readLine()) != null) {
                stringBuilder.append(s);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
