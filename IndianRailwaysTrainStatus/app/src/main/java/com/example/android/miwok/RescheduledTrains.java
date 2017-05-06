package com.example.android.miwok;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RescheduledTrains extends AppCompatActivity {
    SharedPreferences sd=null;
    String value; String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescheduled_trains);


        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        key = sd.getString("key","");
        value = sd.getString("pass","");

        getCanceledTrains();
    }
    void getCanceledTrains() {
        try {

            DownloadTask task = new DownloadTask();
            task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllRescheduledTrains&" + key+ "=" + value);
        } catch (Exception e) {
            Log.e("error 1", e.toString());
        }
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;


            try {
                HttpURLConnection E = null;
                url = new URL(urls[0]);
                E = (HttpURLConnection) url.openConnection();
                String str2=sd.getString("cookie","");
                str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
                E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
                E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
                E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
                E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
                E.setRequestProperty("Method", "GET");
                E.setConnectTimeout(20000);
                E.setReadTimeout(30000);
                E.setDoInput(true);
                E.connect();

                if (E.getResponseCode() != 200) {
                    System.out.println("respose code is not 200");
                } else {
                    System.out.println("Jai hind : " + E.getResponseCode());
                }

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(E.getInputStream()));


                String inputLine =null;
//                    if (inputLine == null) {
//                        System.out.println("fuck off");
//                        Log.i("error ","fuck off");
//                    }

                while ((inputLine=in.readLine()) != null) {
                    result +=inputLine;
                }

                return result;
            }catch (Exception e){
                Log.e("error http get:",e.toString());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {


                String[] rs = result.split("=", 2);
                result = rs[1].trim();
                // result =result.replace("","");
                //  String c = result.substring(150,190);
                //   Log.i("this is the problem :",c);
                Log.i("here is the result:", result.toString());

//                  JSONObject jsonObject = new JSONObject(result.toString());
//                    String tInfo = jsonObject.getString("trainsInStnDataFound");
//                    resultTextView.setText(tInfo);
//                    Log.i("got the data", tInfo);

                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

                while (localObject1.find()) {
                    //  String group = localObject1.group();
                    result = result.replace(localObject1.group(0), "");
                    //  System.out.println(group);
                }
                ArrayList<RescheduledTrainClass> words=new ArrayList<RescheduledTrainClass>();
                words.add(new RescheduledTrainClass("trainNo","trainName","trainSrc","trainDst"));

                JSONObject jsonObject = new JSONObject(result);

                //  System.out.println(jsonObject.getString("trainsInStnDataFound"));
                //  System.out.println(jsonObject.getJSONArray("allTrains"));
                JSONArray arr = jsonObject.getJSONArray("trains");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonpart = arr.getJSONObject(i);
                    String trainNo = "";
                    String trainName = "";
                    String trainSrc= "";
                    String trainDstn ="";


                    trainNo = jsonpart.getString("trainNo");
                    trainName = jsonpart.getString("trainName");
                    trainSrc =jsonpart.getString("trainSrc");
                    trainDstn =jsonpart.getString("trainDstn");

                    //System.out.println(main + " : " + description);
                    //   Log.i("*** ",main +":" +description);
                    RescheduledTrainClass w = new RescheduledTrainClass(trainNo,trainName,trainSrc,trainDstn);
                    words.add(w);
                }





//                String [] rs =result.split("=",2);
//                result =rs[1];
//
//                Log.i("here is the result:","hii");
//                Log.i("here is the result:",result.toString());
//
//                JSONObject jsonObj = new JSONObject(result);
//                JSONArray tInfo = jsonObj.getJSONArray("allCancelledTrains");
//
//
//                for (int i = 0; i < 5; i++) {
//                    JSONObject jsonpart = tInfo.getJSONObject(i);
//                    String main="";
//                    String description="";
//
//                    main= jsonpart.getString("trainNo");
//                    description=jsonpart.getString("trainName");
//                    Log.i("no",jsonpart.getString("trainName"));
//                    Log.i("name",jsonpart.getString("description"));
//
//            }
//                Log.i("t info is here ", tInfo.toString());



//                for(int j=0;j<20;j++){
//                    Word  w= new Word("word :"+j,"bird :"+(20-j) );
//                    words.add(w);
//                }
                //   http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&tqz5a8cgnd=17mdt7n2cg



                RescheduledTrainsAdaptor Adapter =new RescheduledTrainsAdaptor(RescheduledTrains.this,words);

                ListView listView1= (ListView) findViewById(R.id.listview);
                listView1.setAdapter(Adapter);


                //   resultTextView.setText(result.toString());
            } catch (Exception e) {
                //    resultTextView.setText("could not find weather");
                Log.e("error3",e.toString());

            }

        }
    }




}
