package com.hexad.snackmate.Framgments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hexad.snackmate.Enumerations.SortType;
import com.hexad.snackmate.R;
import com.hexad.snackmate.ViewAdapters.DataAdapter;
import com.hexad.snackmate.ViewAdapters.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Ji on 11/28/2015.
 */
public class SortFragment extends Fragment implements View.OnClickListener{
    private ListView menuLv;
    private ListView subjectLv;
    private DataAdapter menuAdapter;
    private DataAdapter subjectAdapter;
    private View foldBtn;
    private View foldContent;
    private View lamp;
    private int[] location;
    private boolean clicked;
    private String selected;
    private String[] choices;
    private String[] data;


    public static SortFragment newInstance(int [] location,String[] choices, String[] data){
        SortFragment fg = new SortFragment();
        Bundle bundle = new Bundle();
        bundle.putIntArray("LOCATION", location);
        bundle.putStringArray("CHOICES", choices);
        bundle.putStringArray("DATA", data);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            int [] l = args.getIntArray("LOCATION");
            if(l != null && l.length == 2)location = l;

            String [] c = args.getStringArray("CHOICES");
            if(c != null && c.length != 0) choices = c;

            String [] d = args.getStringArray("DATA");
            if(d != null && d.length != 0) data = d;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.sort_menu, container, false);
        initView(layout);
        return layout;
    }

    private void initView(final View layout){
//        lamp = layout.findViewById(R.id.v_lamp);
        clicked = false;
        menuLv = (ListView) layout.findViewById(R.id.lv_menu);
        subjectLv = (ListView) layout.findViewById(R.id.lv_subject);
        foldBtn = layout.findViewById(R.id.ll_title_sp);
        foldContent = layout.findViewById(R.id.ll_lv_sp);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)foldBtn.getLayoutParams();
        lp.leftMargin = location[0];
        lp.topMargin = location[1];
        foldBtn.setLayoutParams(lp);
        foldBtn.setOnClickListener(this);
        menuAdapter = new DataAdapter(getActivity().getBaseContext());

        menuAdapter.setData(allocateData());
        menuLv.setAdapter(menuAdapter);
        menuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String sortType = adapterView.getItemAtPosition(i).toString();
                menuAdapter.checked(i);
                subjectAdapter.setData(allocateSubject(i));
                subjectAdapter.notifyDataSetChanged();
                layout.bringToFront();
            }
        });
        subjectAdapter = new DataAdapter(getActivity().getBaseContext());
        subjectAdapter.checked(-1);
        subjectAdapter.setData(allocateSubject(0));
        subjectLv.setAdapter(subjectAdapter);

        subjectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                int pos = ((DataAdapter) menuLv.getAdapter()).getPosition();

                SortType sortType = SortType.Default;
                if(pos == 0){
                    switch(selected) {
                        case "Low to High":
                            sortType = SortType.Price_low_to_high;
                            break;
                        case "High to Low":
                            sortType = SortType.Price_high_to_low;
                            break;
                        default:
                    }
                }
                else if(pos == 1){
                    switch(selected) {
                        case "Low to High":
                            sortType = SortType.Rating_low_to_high;
                            break;
                        case "High to Low":
                            sortType = SortType.Rating_high_to_low;
                            break;
                        default:
                    }
                }
                else if(pos == 2) {
                    switch(selected) {
                        case "A to Z":
                            sortType = SortType.Alphabetical_a_to_z;
                            break;
                        case "Z to A":
                            sortType = SortType.Alphabetical_z_to_a;
                            break;
                        default:
                    }
                }

                Activity activity = getActivity();
                GridView gridView = (GridView) activity.findViewById(R.id.homepage_gridview);
                HomePageAdapter adapter = (HomePageAdapter) gridView.getAdapter();
                adapter.SortByType(sortType);

            }
        });
    }

    @Deprecated
    private List<String> allocateSubject(int i){

        //init test data
        List<String> mList = new ArrayList<String>();
            if (i == 0 || i == 1) {
                for (int index = 0; index < data.length / 2; index++) {
                    mList.add(data[index]);
                }
            } else{
                for (int index = data.length / 2; index < data.length; index++) {
                    mList.add(data[index]);
                }
            }

        return mList;
    }
    @Deprecated
    private List<String> allocateData(){
        //init test data
        List<String> mList = new ArrayList<String>();
        for(String str : choices)
            mList.add(str);

        return mList;
    }

    @Override
    public void onClick(View view) {


        if (clicked == false){
            clicked = true;
            exitAnim();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        enterAnim();
    }

    public boolean onBackPressed(){
        exitAnim();
        return true;
    }

    private void enterAnim(){
        Animation turnOn = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.fade_in);
//        lamp.startAnimation(turnOn);

        Animation in = AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.popupwindow_slide_in_from_top);
        foldContent.startAnimation(in);
    }

    private void exitAnim(){
        Animation turnOff = AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.fade_out);
//        lamp.startAnimation(turnOff);
        Animation out = AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.popupwindow_slide_out_to_top);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                lamp.setVisibility(View.INVISIBLE);
                foldContent.setVisibility(View.INVISIBLE);
                getFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        foldContent.startAnimation(out);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}