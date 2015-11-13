package com.hexad.snackmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.Items.SnackItemService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AxlWu on 2015/11/12.
 */
public class WishList extends Activity {

        ListView list;
        Integer[] imageId={
                Global.images[0],
                Global.images[1],
                Global.images[2],
                Global.images[3],
                Global.images[4],
                Global.images[5]
        };
        List<SnackItem> namelist = SnackItemService.getAllSnackItemsSync();
        String[] name = new String[namelist.size()];
        @Override
        protected void onCreate(Bundle SavedInstanceState) {
            super.onCreate(SavedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.wishlist);

            int i = 0;
            for(SnackItem s:namelist){
                s = Global.list.get(i);
                name[i++] = s.getTitle();
            }

            CustomizeList adapter = new CustomizeList(WishList.this,name,imageId,new ImageLoader(WishList.this));
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(WishList.this,ItemDetailActivity.class);
                    intent.putExtra("itemid",position);
                    startActivity(intent);
                }
            });


    }
}
