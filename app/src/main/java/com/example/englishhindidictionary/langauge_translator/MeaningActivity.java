package com.example.englishhindidictionary.langauge_translator;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.example.englishhindidictionary.R;
import com.example.englishhindidictionary.langauge_translator.dbhelper.Database;
import com.example.englishhindidictionary.langauge_translator.fragment.EnglishMeaningFragment;
import com.example.englishhindidictionary.langauge_translator.fragment.HindiMeaningFragment;

public class MeaningActivity extends AppCompatActivity {
    public static int wId;
    Database ob_cuznapps_Database;
    TabLayout tabLayout;
    ViewPager viewPager;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_meanings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(17170443));
        toolbar.setTitle((CharSequence) "You Dictionay");
        wId = getIntent().getIntExtra("MEAN_ID", 0);
        this.ob_cuznapps_Database = new Database(this);
        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewPager);
        this.viewPager = viewPager2;
        setupViewPager(viewPager2);
        TabLayout tabLayout2 = (TabLayout) findViewById(R.id.meaningTab);
        this.tabLayout = tabLayout2;
        tabLayout2.setupWithViewPager(this.viewPager);

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    private void setupViewPager(ViewPager viewPager2) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new EnglishMeaningFragment(), "  English  ");
        viewPagerAdapter.addFragment(new HindiMeaningFragment(), "  हिन्दी  ");
        viewPager2.setAdapter(viewPagerAdapter);
    }
}
