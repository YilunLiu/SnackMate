package com.hexad.snackmate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andtinder.view.CardContainer;
import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.Utils.ImageLoader;

import java.util.List;
import java.util.Random;
/**
 * Created by Lun Li on 11/05/15.
 * Shuffle Activity
 * Slide shuffled item to like or dislike
 */
public class ShuffleActivity extends Activity {

    private ImageLoader imageLoader;
    private int size = Global.list.size();
    int windowwidth;
    int screenCenter;
    int x_cord, y_cord, x, y;
    int Likes = 0;
    Integer[] myImageList = Global.images;
    private Context m_context;
    RelativeLayout parentView;
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shuffle);
        m_context = ShuffleActivity.this;

        parentView = (RelativeLayout) findViewById(R.id.shuffle_root);
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        screenCenter = windowwidth / 2;
        imageLoader = new ImageLoader(this);
        generateItem();
    }

    // generate a new shuffled item
    private void generateItem(){
        int i = new Random().nextInt(size);
        SnackItem item = Global.list.get(i);
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
                        m_view.setX(100+x_cord - x);
                        m_view.setY(200+y_cord - y);
//                            Log.v("On Move Raw", (+x_cord - x) + " " + (oldY + y_cord - y));
                        if (x_cord >= screenCenter) {
                            m_view.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));

                        } else {
                            // rotate
                            m_view.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                            if (x_cord < (screenCenter / 2)) {
//                                    imagePass.setAlpha(1);
                                if (x_cord < x + screenCenter / 3) {
                                    Likes = 1;
                                } else {
                                    Likes = 0;
                                }
                            } else {
                                Likes = 0;
//                                    imagePass.setAlpha(0);
                            }
//                                imageLike.setAlpha(0);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        x_cord = (int) event.getRawX();
                        y_cord = (int) event.getRawY();

                        Log.e("X Point", "" + x_cord + " , Y " + y_cord);
//                            imagePass.setAlpha(0);
//                            imageLike.setAlpha(0);

//                                    imageLike.setAlpha(1);
                        if (x_cord > x + screenCenter / 3) {
                            Likes = 2;
                        } else if (x_cord < x - screenCenter / 3) {
                            Likes = 1;
                        } else Likes = 0;

//                                imagePass.setAlpha(0);
                        if (Likes == 0) {
                            // Log.e("Event Status", "Nothing");
                            m_view.setX(100);
                            m_view.setY(200);
                            m_view.setRotation(0);
                        } else if (Likes == 1) {
                            generateItem();
                            // Log.e("Event Status", "Passed");
                            parentView.removeView(m_view);
                        } else if (Likes == 2) {
                            generateItem();
                            // Log.e("Event Status", "Liked");
                            parentView.removeView(m_view);
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
