package com.example.android.miwok;

/**
 * Created by sahu on 5/6/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ListViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<AnimalNames> animalNamesList = null;
   private ArrayList<AnimalNames> arraylist;

   public ListViewAdapter(Context context, List<AnimalNames> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<AnimalNames>();
       this.arraylist.addAll(animalNamesList);


   }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println();
    }

    public class ViewHolder {
       TextView name;
       TextView number;


   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public AnimalNames getItem(int position) {
       return animalNamesList.get(position);
   }

   @Override
   public long getItemId(int position) {
       return position;
   }

   public View getView(final int position, View view, ViewGroup parent) {
       final ViewHolder holder;
       if (view == null) {
           holder = new ViewHolder();
           view = inflater.inflate(R.layout.list_view_items, null);
           // Locate the TextViews in listview_item.xml
           holder.name = (TextView) view.findViewById(R.id.name);
           holder.number = (TextView) view.findViewById(R.id.number);
           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }
       // Set the results into TextViews
       holder.name.setText(animalNamesList.get(position).getAnimalName());
       holder.number.setText(animalNamesList.get(position).getAnimalNo());


       return view;
   }

   // Filter Class
   public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());
       animalNamesList.clear();
       if (charText.length() == 0) {
           animalNamesList.addAll(arraylist);
       } else {
           for (AnimalNames wp : arraylist) {
               if (wp.getAnimalName().toLowerCase(Locale.getDefault()).contains(charText)) {
                   animalNamesList.add(wp);
               }
           }
       }
       notifyDataSetChanged();
   }



}