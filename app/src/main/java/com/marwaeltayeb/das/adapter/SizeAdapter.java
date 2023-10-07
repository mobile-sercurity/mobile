package com.marwaeltayeb.das.adapter;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.graphics.Color;
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

public class SizeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    List<String> sizeItemList = new ArrayList<>();

    private List<String> itemList;
    private OnSizeClickListener onSizeClickListener; // Add this variable
    private int positionSelect = -1;

    public SizeAdapter(Context context, List<String> itemList, OnSizeClickListener onSizeClickListener) {
        mContext = context;
        this.itemList = itemList;
        this.sizeItemList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.onSizeClickListener = onSizeClickListener;

    }
    public interface OnSizeClickListener {
        void onSizeClick(String Size); // Define a method to handle item click
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
    public void setItemSelect(int position) {
        this.positionSelect = position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.size_list_item, parent, false);
            holder = new ViewHolder();
            holder.sizeItem = convertView.findViewById(R.id.size_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = itemList.get(position);
        holder.sizeItem.setText(item);

        if(position == positionSelect){
            holder.sizeItem.setBackgroundResource(R.drawable.border);
        } else {
            holder.sizeItem.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.sizeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSizeClickListener.onSizeClick(item);
                setItemSelect(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Select Size: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {

        Button sizeItem;
//        ImageView imageView;
//        TextView textView, plus, price;
    }
}
