package com.marwaeltayeb.das.adapter;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.marwaeltayeb.das.R;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    List<String> colorItemList = new ArrayList<>();
    private OnColorClickListener onColorClickListener; // Add this variable

    private List<String> itemList;

    public ColorAdapter(Context context, List<String> itemList, OnColorClickListener onColorClickListener) {
        mContext = context;
        this.itemList = itemList;
        this.colorItemList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.onColorClickListener = onColorClickListener;

    }
    public interface OnColorClickListener {
        void onColorClick(String color); // Define a method to handle item click
    }
    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Drawable colorDrawable = mContext.getResources().getDrawable(R.drawable.border);

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.color_list_item, parent, false);
            holder = new ViewHolder();
            holder.colorItem = convertView.findViewById(R.id.color_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = itemList.get(position);
        holder.colorItem.setBackgroundColor(Color.parseColor(item));

        holder.colorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable colorDrawable = getColorDrawable(item);
                holder.colorItem.setBackground(colorDrawable);
                onColorClickListener.onColorClick(item);
                Toast.makeText(mContext, "Select Color: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private Drawable getColorDrawable(String colorCode) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(Color.parseColor(colorCode));
        drawable.setStroke(8, Color.parseColor("#ffff8800"));
        drawable.setCornerRadius(8);
        return drawable;
    }
    static class ViewHolder {
        Button colorItem;
//        ImageView imageView;
//        TextView textView, plus, price;
    }
}
