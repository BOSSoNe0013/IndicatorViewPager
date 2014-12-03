package com.b1project.indicatorviewpagertestapplication;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.b1project.indicatorviewpager.IndicatorViewPager;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StaticPagerAdapter staticPagerAdapter = new StaticPagerAdapter();
        IndicatorViewPager mViewPager = (IndicatorViewPager) findViewById(R.id.helpViewPager);
        mViewPager.setAdapter(staticPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class StaticPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int resId = 0;
            switch (position){
                case 0:
                    resId = R.id.page1;
                    break;
                case 1:
                    resId = R.id.page2;
                    break;
                case 2:
                    resId = R.id.page3;
                    break;
                case 3:
                    resId = R.id.page4;
                    break;
            }
            return findViewById(resId);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //do nothing
            //container.removeView((View) object);
        }
    }
}

