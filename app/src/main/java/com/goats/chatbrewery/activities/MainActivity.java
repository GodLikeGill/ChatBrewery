package com.goats.chatbrewery.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.adapters.PageViewerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        PageViewerAdapter adapter = new PageViewerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.chats:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.friends:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.updates:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.chats);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.friends);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.updates);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}