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

import com.hexad.snackmate.Enumerations.Country;
import com.hexad.snackmate.Enumerations.Taste;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Services.SnackItemService;
import com.hexad.snackmate.ViewAdapters.DataAdapter;
import com.hexad.snackmate.ViewAdapters.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Ji on 11/28/2015.
 */

/**
 * This class extends Fragment to push/pop from stack in order to display open/close menu functionality
 */
public class FilterFragment extends Fragment implements View.OnClickListener{
    private ListView menuLv;
    private ListView subjectLv;
    private DataAdapter menuAdapter;
    private DataAdapter subjectAdapter;
    private View foldBtn;
    private View foldContent;
    private int[] location;
    private boolean clicked;
    private String[] choices;
    private String[] data;


    /**
     * Create new instance of FilterFragment with given arguments
     */
    public static FilterFragment newInstance(int [] location,String[] choices, String[] data){
        FilterFragment fg = new FilterFragment();
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


        // get arguments for fragment and store into class variable
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
        View layout = inflater.inflate(R.layout.select_choice, container, false);
        initView(layout);
        return layout;
    }

    /**
     * initialize the fragment view and set adapters and listeners for both level 1 and level 2 menu
     */
    private void initView(final View layout){
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

        //set menu listview to respond to user click to display correct level 2 menu
        menuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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

        //listener for level 2 menu to filter
        subjectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                int pos = ((DataAdapter) menuLv.getAdapter()).getPosition();
                Activity activity = getActivity();
                GridView gridView = (GridView) activity.findViewById(R.id.homepage_gridview);
                HomePageAdapter adapter = (HomePageAdapter) gridView.getAdapter();

                if (pos == 0 ){
                    adapter.setList(SnackItemService.list);
                    adapter.notifyDataSetChanged();
                    return;
                }
                else if (pos == 1) {
                    Country country = Country.All;
                    switch (selected) {
                        case "China":
                            country = Country.China;
                            break;
                        case "Japan":
                            country = Country.Japan;
                            break;
                        case "Others":
                            country = Country.Others;
                            break;
                        case "North Korea":
                            country = Country.North_Korea;
                            break;
                        case "All":
                            adapter.setList(SnackItemService.list);
                            adapter.notifyDataSetChanged();
                            return;
                    }

                    adapter.filterByCountry(country);
                } else if (pos == 2) {
                    Taste taste = Taste.All;
                    switch (selected) {
                        case "Sweet":
                            taste = Taste.Sweet;
                            break;
                        case "Sour":
                            taste = Taste.Sour;
                            break;
                        case "Salty":
                            taste = Taste.Salty;
                            break;
                        case "Spicy":
                            taste = Taste.Spicy;
                            break;
                        case "Others":
                            taste = Taste.Others;
                            break;
                        case "All":
                            adapter.setList(SnackItemService.list);
                            adapter.notifyDataSetChanged();
                            return;
                    }
                    adapter.filterByTaste(taste);
                }
            }
        });
    }

//    @Deprecated
    private List<String> allocateSubject(int i){
        //init test data
        List<String> mList = new ArrayList<>();
            if( i == 0 ) {
                mList.add(data[0]);
            }
            else if (i == 1) {
                for (int index = 0; index < data.length / 2; index++) {
                    mList.add(data[index]);
                }
            } else if (i == 2) {
                for (int index = data.length / 2; index < data.length; index++) {
                    mList.add(data[index]);
                }
            }

        return mList;
    }

//    @Deprecated
    private List<String> allocateData(){
        //init test data
        List<String> mList = new ArrayList<>();
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

        Animation in = AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.popupwindow_slide_in_from_top);
        foldContent.startAnimation(in);
    }

    private void exitAnim(){
        Animation out = AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.popupwindow_slide_out_to_top);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
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