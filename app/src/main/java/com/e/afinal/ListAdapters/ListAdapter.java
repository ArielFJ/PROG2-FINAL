package com.e.afinal.ListAdapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.e.afinal.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    // Adapter para el despliegue de la lista principal

    private Context context;
    private ArrayList<RowModel> itemsList;
    private boolean darkMode;

    private RowModel item;

    public ListAdapter(Context context, ArrayList<RowModel> itemsList, boolean darkMode) {
        this.context = context;
        this.itemsList = itemsList;
        this.darkMode = darkMode;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        item = (RowModel) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item, null);

        LinearLayout ll = convertView.findViewById(R.id.layoutRowModel);
        ImageView imgFoto = convertView.findViewById(R.id.item_img);
        TextView txtName = convertView.findViewById(R.id.item_name);
        TextView rating = convertView.findViewById(R.id.item_rating);


        // Cambia el tema de la lista principal según el dark mode esté activado o no

        if(darkMode){
            ll.setBackground(new ColorDrawable( context.getResources().getColor(R.color.DTBGColor)));
            txtName.setTextColor(context.getResources().getColor(R.color.DTText_TintColor));
            rating.setTextColor(context.getResources().getColor(R.color.DTText_TintColor));
        }else{
            ll.setBackground(new ColorDrawable( context.getResources().getColor(R.color.LTBGColor)));
            txtName.setTextColor(context.getResources().getColor(R.color.LTTextColor));
            rating.setTextColor(context.getResources().getColor(R.color.LTTextColor));
        }

        Glide.with(context)
                .load(item.getImage())
                .into(imgFoto);

        txtName.setText(item.getName());
        rating.setText(item.getRating() + "/5.0");


        return convertView;
    }

}
