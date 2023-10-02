package com.marwaeltayeb.das.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

    private List<String> itemList;

    public ColorAdapter(Context context, List<String> itemList) {
        mContext = context;
        this.itemList = itemList;
        this.colorItemList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
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
//                Item clickedItem = itemList.get(position);
//                cartItemList.add(clickedItem);
                Toast.makeText(mContext, "Set color: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        Button colorItem;
//        ImageView imageView;
//        TextView textView, plus, price;
    }
}
