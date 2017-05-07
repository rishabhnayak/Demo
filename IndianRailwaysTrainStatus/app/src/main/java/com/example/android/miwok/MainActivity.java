/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sd;
Boolean gotthekey=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        TextView numbers= (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CanceledTrains.class);
                startActivity(i);
            }
        });
        TextView RescheduledTrains= (TextView) findViewById(R.id.RescheduledTrains);
        RescheduledTrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RescheduledTrains.class);
                startActivity(i);
            }
        });
        TextView train_route= (TextView) findViewById(R.id.train_route);
        train_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TrainRoute.class);
                startActivity(i);
            }
        });

        TextView select_station= (TextView) findViewById(R.id.select_station);
        select_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Select_Station.class);
                startActivity(i);
            }
        });

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        gotthekey=false;
        getkeyval();

    }

//    public void Numbers_activity(View view) {
//        Intent i = new Intent(this, NumbersActivity.class);
//        startActivity(i);
//    }

    void getkeyval(){
        try {
            DownloadTask task = new DownloadTask();
            task.execute("http://enquiry.indianrail.gov.in/ntes/");
        } catch (Exception e) {
            Log.e("error 1",e.toString());
        }
    }





    public void DivertedTrains_Activity(View view) {

        Intent i = new Intent(this, DivertedTrains.class);
        startActivity(i);
    }

    public void Phrases_Activity(View view) {
        Intent i = new Intent(this, PhrasesActivity.class);
        startActivity(i);
    }


//    public void ReschueduledTrains_Activity(View view) {
//        Intent i = new Intent(this, PhrasesActivity.class);
//        startActivity(i);
//    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;


                HttpURLConnection urlConnection = null;

                try {

                    url = new URL(urls[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setReadTimeout(20000);
                    urlConnection.connect();
                    //   InputStream in = urlConnection.getInputStream();
                    //     InputStreamReader reader = new InputStreamReader(in);
                    //     BufferedReader br =new BufferedReader(reader);

                    Object localObject2;
                    if (urlConnection.getResponseCode() == 200) {
                        Object localObject3 = new CookieManager();
                        Object localObject1;
                        localObject1 = (List) urlConnection.getHeaderFields().get("Set-Cookie");
                        Object localObject4;
                        localObject4 = null;

                        Log.i("cookie found :", String.valueOf(urlConnection.getHeaderFields().get("Set-Cookie")));
                        if (localObject1 != null) {
                            localObject1 = ((List) localObject1).iterator();
                            while (((Iterator) localObject1).hasNext()) {
                                localObject2 = (String) ((Iterator) localObject1).next();
                                System.out.println(localObject2);
                                ((CookieManager) localObject3).getCookieStore().add(null, (HttpCookie) HttpCookie.parse((String) localObject2).get(0));

                            }
                        }

                        localObject1 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        localObject2 = new StringBuilder();
                        for (; ; ) {
                            localObject4 = ((BufferedReader) localObject1).readLine();
                            if (localObject4 == null) {
                                break;
                            }
                            ((StringBuilder) localObject2).append((String) localObject4 + "\n");
                            //  System.out.println(localObject4);
                        }
                        localObject4 = ((StringBuilder) localObject2).toString().replaceAll("\\s+", "");
                        localObject1 = Pattern.compile("<script>_.*?=\"(.*?)\";").matcher((CharSequence) localObject4);
                        if (((Matcher) localObject1).find()) {
                            localObject2 = ((Matcher) localObject1).group(1);
                            localObject1 = localObject2;
                            if (localObject2 != null) {
                                localObject2 = Pattern.compile("name=\"" + (String) localObject1 + "\"value=\"(.*?)\"").matcher((CharSequence) localObject4);
                                if (((Matcher) localObject2).find()) {
                                    localObject2 = ((Matcher) localObject2).group(1);
                                    if (((String) localObject2).length() == 10) {

                                        localObject3 = ((CookieManager) localObject3).getCookieStore().getCookies().toString();
//                                    System.out.println("cookie :" + localObject3);
//                                    System.out.println("key :" + localObject1);
//                                    System.out.println("pass :" + (String) localObject2);
//                                    String datam = (String) localObject3;

                                        Log.i("cookie ", localObject3.toString());
                                        sd.edit().putString("cookie", localObject3.toString()).apply();
                                        Log.i("key ", localObject1.toString());
                                        sd.edit().putString("key", localObject1.toString()).apply();
                                        Log.i("pass ", localObject2.toString());
                                        sd.edit().putString("pass", localObject2.toString()).apply();
                                        result= localObject1.toString();

                                        gotthekey=true;
                                    }

                                }
                            }
                        }

                    }
//                    String data = br.readLine();
//                while (data !=null) {
//
//                    String current = (String) data;
//                    result += current;
//                    data =br.readLine();
//                    count++;
//              }

                   // Log.i("here is the count:", String.valueOf(count));

                    return result;

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "key not found", Toast.LENGTH_LONG).show();
                    Log.i("error 2", e.toString());
                }



            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                if(gotthekey)
                Toast.makeText(getApplicationContext(),result +"\nGot the Key" , Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(getApplicationContext(),"Not Got the Key" , Toast.LENGTH_SHORT).show();
                    getkeyval();
                }

              //  Log.i("here is the result:","hii");
                Log.i("here is the result:",result.toString());
              //  resultTextView.setText(result.toString());
            } catch (Exception e) {
               // resultTextView.setText("could not find weather");
                Log.e("error3",e.toString());

            }

        }
    }
}
