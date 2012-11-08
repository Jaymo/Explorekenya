package com.JamuhuriTech.ExploreKenya.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JamuhuriTech.ExploreKenya.TourismDisplay;
import com.JamuhuriTech.ExploreKenya.R;



public class LazyAdapter_tourism_display extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter_tourism_display(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);//nilifix hapa ju am using a custom
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.tour_list_row, null);
//Recycled the Layout from Events since am not changing anything #reuse
        TextView EVENT_NAME = (TextView)vi.findViewById(R.id.event_name); 
        TextView CATEGORY = (TextView)vi.findViewById(R.id.category); 
        TextView DATE = (TextView)vi.findViewById(R.id.date); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); 
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash = data.get(position);
        
        // Setting all values in listview
        EVENT_NAME.setText(hash.get(TourismDisplay.KEY_NAME));
        CATEGORY.setText("INFO:"+hash.get(TourismDisplay.KEY_DESCRIPTION));
        DATE.setText("LOCATION: "+hash.get(TourismDisplay.KEY_LOCATION));
        imageLoader.DisplayImage(hash.get(TourismDisplay.KEY_IMAGE_URL), thumb_image);
        return vi;
    }
}