package com.hexad.snackmate;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Michael Ji on 11/22/2015.
 */
public class SubMultiMenuAdapter extends BaseAdapter {

        Context context;
        LayoutInflater layoutInflater;
        String[][] choices;
        public int choicePosition;

        public SubMultiMenuAdapter(Context context, String[][] choices,int position) {
            this.context = context;
            this.choices = choices;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.choicePosition = position;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return choices[choicePosition].length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return getItem(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder viewHolder = null;
            final int location = position;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.sublist_item, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView
                        .findViewById(R.id.textview_sublist);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(choices[choicePosition][position]);
            viewHolder.textView.setTextColor(Color.BLACK);

            return convertView;
        }

        public static class ViewHolder {
            public TextView textView;
        }

}
