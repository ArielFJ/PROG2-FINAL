package com.e.afinal.ListAdapters.GameAct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.afinal.R;

import java.util.ArrayList;

public class ColListAdapter extends RecyclerView.Adapter<ColListAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ImageView imageView;

        public ViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }

    }


    private ArrayList<String> imgs;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ColListAdapter(ArrayList<String> imgs, Context context) {
        this.imgs = imgs;
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ColListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.imageView.setImageResource(imgs[position]);

        Glide.with(context)
                .load(imgs.get(position))
                .into(holder.imageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return imgs.size();
    }

}
