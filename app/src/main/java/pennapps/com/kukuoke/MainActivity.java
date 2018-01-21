package pennapps.com.kukuoke;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static FirebaseUser FBU = null;
    private Toolbar toolbar;

    public static boolean tab1AdapterInitialized = false;
    public static boolean tab2AdapterInitialized = false;
    public static boolean tab3AdapterInitialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.GREEN);
        tabLayout.setTabTextColors(Color.BLACK, Color.CYAN);

        tabItem = (TabItem) findViewById(R.id.tabItem);
        tabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabItem.setBackgroundColor(Color.GRAY);
            }
        });
        */

        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
//            intent1.putExtra("uid", FBU.getUid());
//            startActivity(intent1);
//            return true;
//        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_image:
                return true;
            case R.id.action_user:
                Intent intent2 = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                case 2:
                    Tab3 tab3 = new Tab3();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Contacts";
                case 1:
                    return "Chat";
                case 2:
                    return "Online";
                default:
                    return null;
            }
        }
    }
}
