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

import com.JamuhuriTech.ExploreKenya.R;
import com.JamuhuriTech.ExploreKenya.ExploreKenyaLocations;



public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);//depo nilifix hapa
    }

    public long getItemId(int position) {
        return position;
    }

	
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
           vi = inflater.inflate(R.layout.locations_list, null);

        TextView NAME = (TextView)vi.findViewById(R.id.artist);     
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); 
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash = data.get(position);
        
        
        NAME.setText(hash.get(ExploreKenyaLocations.KEY_NAME));
        imageLoader.DisplayImage(hash.get(ExploreKenyaLocations.KEY_IMAGE_URL), thumb_image);
        return vi; 
    }
}