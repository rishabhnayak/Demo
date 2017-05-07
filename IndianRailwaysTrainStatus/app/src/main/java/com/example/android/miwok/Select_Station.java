package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sahu on 5/7/2017.
 */

public class Select_Station extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ListViewAdapter Adapter;

    SearchView editsearch;
    ArrayList<AnimalNames> countries;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train);


        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open("stations_code_name.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);


            countries = parseXML(parser);


            Adapter = new ListViewAdapter(Select_Station.this,countries);

            ListView listView1= (ListView) findViewById(R.id.listview);
            listView1.setAdapter(Adapter);


            editsearch = (SearchView) findViewById(R.id.search);
            editsearch.setOnQueryTextListener(this);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    //    Log.d("############","Items " +  MoreItems[arg2] );
                    Object item = arg0.getItemAtPosition(arg2);
                    System.out.println(countries.get(arg2).getAnimalName()+""+countries.get(arg2).getAnimalNo());

                    Intent i = new Intent(Select_Station.this, Station_Status.class);
                    i.putExtra("stn_name",countries.get(arg2).getAnimalName() );
                    i.putExtra("stn_code",countries.get(arg2).getAnimalNo() );
                    startActivity(i);
                }

            });

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        Adapter.filter(text);
        return false;
    }


    private ArrayList<AnimalNames> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<AnimalNames> countries = null;
        int eventType = parser.getEventType();
        AnimalNames country = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("stn")){
                        country = new AnimalNames();
                        // country.id=parser.getAttributeValue(null,"id");
                    } else
                    if (country != null){
                        if (name.equals("code")){
                            country.animalNo = parser.nextText();
                        //    Log.i("name :",country.animalNo);
                        } else if (name.equals("name")){
                            country.animalName = parser.nextText();
                        //    Log.i("capital :",country.animalName);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("stn") && country != null){
                        countries.add(country);
                    }
            }
            eventType = parser.next();
        }

        return countries;

    }
}
