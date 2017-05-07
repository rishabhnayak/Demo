package com.example.android.miwok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sahu on 5/3/2017.
 */

public class stn_status_ItemList_Adaptor extends ArrayAdapter<stn_status_Items_Class>{


    public stn_status_ItemList_Adaptor(Station_Status context, ArrayList<stn_status_Items_Class> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.rescheduled_trains_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        stn_status_Items_Class currentAndroidFlavor = getItem(position);

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_name
        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        trainNo.setText(currentAndroidFlavor.getTrainNo());

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_number
        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        trainName.setText(currentAndroidFlavor.getTrainName());

        TextView trainSrc = (TextView) listItemView.findViewById(R.id.trainSrc);
        trainSrc.setText(currentAndroidFlavor.getTrainSrc());

        TextView trainDstn= (TextView) listItemView.findViewById(R.id.trainDstn);
        trainDstn.setText(currentAndroidFlavor.getTrainDstn());
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
