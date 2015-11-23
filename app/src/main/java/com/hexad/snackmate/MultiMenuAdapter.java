package com.hexad.snackmate;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Michael Ji on 11/22/2015.
 */
public class MultiMenuAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    String[] filter;
    int last_item;
    private int selectedPosition = -1;

    public MultiMenuAdapter(Context context, String[] filter) {
        this.context = context;
        this.filter = filter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return filter.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mylist_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textview_mylist);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.colorlayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置选中效果
        if (selectedPosition == position) {
            holder.textView.setTextColor(Color.BLUE);


            holder.layout.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.textView.setTextColor(Color.WHITE);
            holder.layout.setBackgroundColor(Color.TRANSPARENT);
        }


        holder.textView.setText(filter[position]);
        holder.textView.setTextColor(Color.BLACK);

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public LinearLayout layout;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

}
