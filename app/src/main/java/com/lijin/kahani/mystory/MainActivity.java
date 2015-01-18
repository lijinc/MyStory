package com.lijin.kahani.mystory;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


public class MainActivity extends ActionBarActivity {
    ViewPager vpPager;
    FragmentPagerAdapter adapterViewPager;
    View line1;
    View line2;
    View line3;
    View line4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewPager();


    }
    //Todo add more pages
    public void setViewPager(){
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        FrameLayout homeTab =(FrameLayout)findViewById(R.id.tab_button_home);
        /*FrameLayout tab2 =(FrameLayout)findViewById(R.id.tabbutton2);
        FrameLayout tab3 =(FrameLayout)findViewById(R.id.tabbutton3);
        FrameLayout tab4 =(FrameLayout)findViewById(R.id.tabbutton4);*/
        line1 =findViewById(R.id.line1);
        line2 =findViewById(R.id.line2);
        line3 =findViewById(R.id.line3);
        line4 =findViewById(R.id.line4);
        line1.setVisibility(View.VISIBLE);
        line2.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);
        line4.setVisibility(View.INVISIBLE);
        homeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpPager.setCurrentItem(0);
            }
        });
        /*tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpPager.setCurrentItem(1);
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpPager.setCurrentItem(2);
            }
        });
        tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpPager.setCurrentItem(3);
            }
        });*/
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);
                switch (position){
                    case 0:
                        line1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        line2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        line3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        line4.setVisibility(View.VISIBLE);
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent=new Intent(this,AddStoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
//Todo add other items
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 1;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
            /*    case 1:
                    return TrendingFragment.newInstance();
                case 2:
                    return LibraryFragment.newInstance();
                case 3:
                    return MyFragment.newInstance();*/
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }


}
