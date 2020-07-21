package com.example.playerprofileuichallenge;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playerprofileuichallenge.fragments.ProfileFragment;
import com.example.playerprofileuichallenge.fragments.StatsFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final AppBarLayout appBarLayout = findViewById(R.id.appbar);
        imageView = findViewById(R.id.img);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        int statusBarHeight = getStatusBarHeight();
        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        toolBarLayout.getLayoutParams().height = height + statusBarHeight + 70;

        ViewPager viewPager = findViewById(R.id.viewpager);

        tabLayout = findViewById(R.id.tabLayout);
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
        adapter.addNewTab(new ProfileFragment(), "PROFILE");
        adapter.addNewTab(new StatsFragment(), "STATS");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        appBarLayout.addOnOffsetChangedListener(new OffSetChangeListener());

        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(false, true);
            }
        });

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    class OffSetChangeListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            double vScrollOffset = Math.abs(verticalOffset);
            double scale = (1 - (1.0 / appBarLayout.getTotalScrollRange() * (vScrollOffset) * 0.2));
            imageView.setScaleX((float) scale);
            imageView.setScaleY((float) scale);
            fadeToolbarTitle((1.0 / appBarLayout.getTotalScrollRange() * (vScrollOffset)));

            if (Math.abs(1.0 / appBarLayout.getTotalScrollRange() * vScrollOffset) >= 0.8) {
//                tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ObjectAnimator colorFade = ObjectAnimator.ofObject(tabLayout,
                        "backgroundColor" /*view attribute name*/, new ArgbEvaluator(),
                        getResources().getColor(R.color.colorPrimary)
                        /*from color*/, getResources().getColor(R.color.colorPrimary)/*to color*/);
                colorFade.setDuration(2000);
                colorFade.start();
            } else {
//                tabLayout.setBackgroundColor(getResources().getColor(R.color.bgColor));
                ObjectAnimator colorFade = ObjectAnimator.ofObject(tabLayout,
                        "backgroundColor" /*view attribute name*/, new ArgbEvaluator(),
                        getResources().getColor(R.color.bgColor)
                        /*from color*/, getResources().getColor(R.color.bgColor)/*to color*/);
                colorFade.setDuration(2000);
                colorFade.start();
            }

//            Log.i("SCROLL", "onOffsetChanged: " + Math.abs(1.0 / appBarLayout.getTotalScrollRange() * vScrollOffset));

        }
    }

    private void fadeToolbarTitle(double scale) {
        if (toolbar != null) {
            for (int i = 0; i < toolbar.getChildCount(); i++) {
                if (toolbar.getChildAt(i) instanceof TextView) {
                    TextView title = (TextView) toolbar.getChildAt(i);

                    //You now have the title textView. Do something with it
                    title.setAlpha((float) scale);
                }
            }
        }
    }
}