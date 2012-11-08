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

import com.JamuhuriTech.ExploreKenya.ExploreKenyaEvents;
import com.JamuhuriTech.ExploreKenya.R;



public class LazyAdapter_Events extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter_Events(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.events_list_row, null);

        TextView EVENT_NAME = (TextView)vi.findViewById(R.id.event_name); // EVENT Name
        TextView CATEGORY = (TextView)vi.findViewById(R.id.category); // CATEGORY
        TextView REF= (TextView)vi.findViewById(R.id.ref); // Ref:
        TextView DATE = (TextView)vi.findViewById(R.id.date); // Event date
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); //  thumb image
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash = data.get(position);
        
        // Setting all values in listview
        EVENT_NAME.setText(hash.get(ExploreKenyaEvents.KEY_EVENT_NAME));
        CATEGORY.setText("CATEGORY: "+hash.get(ExploreKenyaEvents.KEY_CATEGORY));
        REF.setText("REF:"+hash.get(ExploreKenyaEvents.KEY_REF));
        DATE.setText("EVENT DATE: "+hash.get(ExploreKenyaEvents.KEY_DATE));
        imageLoader.DisplayImage(hash.get(ExploreKenyaEvents.KEY_IMAGE_URL), thumb_image);
        return vi;
    }
}