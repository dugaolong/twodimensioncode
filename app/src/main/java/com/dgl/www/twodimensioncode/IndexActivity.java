package com.dgl.www.twodimensioncode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dgl.www.twodimensioncode.wegit.FixedSpeedScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dugaolong on 17/2/21.
 */
public class IndexActivity extends FragmentActivity implements View.OnClickListener{
    private static final String TAG = "IndexActivity";
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    /**
     * 底部四个按钮
     */
    private LinearLayout mTabBtnWeixin;
    private LinearLayout mTabBtnFrd;
    private LinearLayout mTabBtnAddress;
    private LinearLayout mTabBtnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        setContentView(R.layout.index);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        initView();
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            private int currentIndex;

            @Override
            public void onPageSelected(int position)
            {
                resetTabBtn();
                switch (position)
                {
                    case 0:
                        ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_scan))
                                .setImageResource(R.drawable.capture_scan_selected);
                        break;
                    case 1:
                        ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_create))
                                .setImageResource(R.drawable.star_full);
                        break;
                    case 2:
                        ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_history))
                                .setImageResource(R.drawable.capture_history_selected);
                        break;
                    case 3:
                        ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_about))
                                .setImageResource(R.drawable.capture_user_selected);
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

    }

    protected void resetTabBtn()
    {
        ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_scan))
                .setImageResource(R.drawable.capture_scan);
        ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_create))
                .setImageResource(R.drawable.star_empty);
        ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_history))
                .setImageResource(R.drawable.capture_history);
        ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_about))
                .setImageResource(R.drawable.capture_user);
    }

    private void initView()
    {

        mTabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_scan);
        mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_create);
        mTabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_history);
        mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_about);
        mTabBtnWeixin.setOnClickListener(this);
        mTabBtnFrd.setOnClickListener(this);
        mTabBtnAddress.setOnClickListener(this);
        mTabBtnSettings.setOnClickListener(this);
//        MainTabScan tab01 = new MainTabScan();
//        MainTab02 tab02 = new MainTab02();
//        MainTab03 tab03 = new MainTab03();
//        MainTab04 tab04 = new MainTab04();
//        mFragments.add(tab01);
//        mFragments.add(tab02);
//        mFragments.add(tab03);
//        mFragments.add(tab04);
        setViewPagerScrollSpeed();
    }

    @Override
    public void onClick(View view) {
        resetTabBtn();
        int id = view.getId();
        switch (id){
            case R.id.id_tab_bottom_scan:
                mViewPager.setCurrentItem(0);
                ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_scan))
                        .setImageResource(R.drawable.capture_scan_selected);
                break;
            case R.id.id_tab_bottom_create:
                mViewPager.setCurrentItem(1);
                ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_create))
                        .setImageResource(R.drawable.star_full);
                break;
            case R.id.id_tab_bottom_history:
                mViewPager.setCurrentItem(2);
                ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_history))
                        .setImageResource(R.drawable.capture_history_selected);
                break;
            case R.id.id_tab_bottom_about:
                mViewPager.setCurrentItem(3);
                ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_about))
                        .setImageResource(R.drawable.capture_user_selected);
                break;
        }
    }

    /**
     * 设置ViewPager的滑动速度
     *
     * */
    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller( mViewPager.getContext( ) );
            mScroller.set( mViewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }


}
