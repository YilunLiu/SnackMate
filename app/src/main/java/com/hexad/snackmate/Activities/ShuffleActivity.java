package com.hexad.snackmate.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hexad.snackmate.Models.SnackItem;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Services.SnackItemService;
import com.hexad.snackmate.Utils.Image.ImageLoader;
import com.parse.ParseUser;

import java.util.Random;

import at.markushi.ui.CircleButton;

/**
 * Created by Lun Li on 11/05/15.
 * Shuffle Activity
 * Slide shuffled item to like or dislike
 */
public class ShuffleActivity extends AppCompatActivity {

    private CircleButton like;
    private CircleButton dismiss;
    private ImageLoader imageLoader;
    private int size = SnackItemService.list.size();
    int windowwidth;
    int screenCenter;
    int x_cord, y_cord, x, y;
    int Likes = 0;
    private Context m_context;
    RelativeLayout parentView;
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle);
        m_context = ShuffleActivity.this;

        parentView = (RelativeLayout) findViewById(R.id.shuffle_root);
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        screenCenter = windowwidth / 2;
        imageLoader = new ImageLoader(this);
        like = (CircleButton)findViewById(R.id.wish_list_button);
        dismiss = (CircleButton)findViewById(R.id.trash_button);
        generateItem();
    }

    // generate a new shuffled item
    private void generateItem(){
        int i = new Random().nextInt(size);
        final SnackItem item = SnackItemService.list.get(i);
        LayoutInflater inflate = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View m_view = inflate.inflate(R.layout.shuffle_card, null);
        ImageView m_image = (ImageView) m_view.findViewById(R.id.sp_image);
        TextView textView = (TextView)m_view.findViewById(R.id.sp_msg);
        textView.setText(item.getTitle() + "($" + item.getPrice() + ")");
        LinearLayout m_topLayout = (LinearLayout) m_view.findViewById(R.id.sp_color);

        // final RelativeLayout myRelView = new RelativeLayout(this);
        m_view.setLayoutParams(new LinearLayout.LayoutParams(windowwidth - 200, windowwidth - 200));
        m_view.setX(100);
        m_view.setY(200);
        m_view.setTag(i);
        imageLoader.displayImage(item.getImageURL(),m_image);
        parentView.addView(m_view);


//            // ADD dynamically like button on image.
//            final Button imageLike = new Button(m_context);
//            imageLike.setLayoutParams(new LinearLayout.LayoutParams(100, 50));
//            imageLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.like));
//            imageLike.setX(20);
//            imageLike.setY(-250);
//            imageLike.setAlpha(alphaValue);
//            m_topLayout.addView(imageLike);
//
//            // ADD dynamically dislike button on image.
//            final Button imagePass = new Button(m_context);
//            imagePass.setLayoutParams(new LinearLayout.LayoutParams(100, 50));
//            imagePass.setBackgroundDrawable(getResources().getDrawable(R.drawable.dislike));
//
//            imagePass.setX(260);
//            imagePass.setY(-300);
//            imagePass.setAlpha(alphaValue);
//            m_topLayout.addView(imagePass);

        // Click listener on the bottom layout to open the details of the
        // image.

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_view.animate().translationX(windowwidth*2).setDuration(400);
                ParseUser user = ParseUser.getCurrentUser();
                user.addUnique("wishList", item);
                user.saveInBackground();

                generateItem();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_view.animate().translationX(-windowwidth).setDuration(400);

                generateItem();
            }
        });
        // Touch listener on the image layout to swipe image right or left.
        m_topLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        Log.v("On touch", x + " " + y);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        x_cord = (int) event.getRawX(); // Updated for more
                        y_cord = (int) event.getRawY();
                        m_view.setX(100 + x_cord - x);
                        m_view.setY(200 + y_cord - y);
                        if (x_cord > x)
                            like.setPressed(false);
                        else if (x_cord < x)
                            dismiss.setPressed(false);
                        if (x_cord >= screenCenter) {

                            m_view.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));

                        } else {

                            m_view.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));

                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        x_cord = (int) event.getRawX();
                        y_cord = (int) event.getRawY();

                        Log.e("X Point", "" + x_cord + " , Y " + y_cord);
//                            imagePass.setAlpha(0);
//                            imageLike.setAlpha(0);

//                                    imageLike.setAlpha(1);
                        if (x_cord > x + screenCenter / 2) {
                            Likes = 2;
                        } else if (x_cord < x - screenCenter / 2) {
                            Likes = 1;
                        } else Likes = 0;

//                                imagePass.setAlpha(0);
                        if (Likes == 0) {
                            // Log.e("Event Status", "Nothing");
                            m_view.setX(100);
                            m_view.setY(200);
                            m_view.setRotation(0);
                        } else if (Likes == 1) {




                            dismiss.performClick();
                            // Log.e("Event Status", "Passed");

                        } else if (Likes == 2) {


                            like.performClick();
                            // Log.e("Event Status", "Liked");

                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });



    }

}
