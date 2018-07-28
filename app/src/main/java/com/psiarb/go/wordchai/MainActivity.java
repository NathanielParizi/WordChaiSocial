package com.psiarb.go.wordchai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MiscFragment.MiscFragmentListener{


    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private SharedPreferences myPrefs;
    private static final String PREFS_NAME = "myPrefsFile";

    private TabLayout mTabLayout;



    @Override
    public void createCardDeck(int start, int end) {

        CardsFragment cardsFragment = (CardsFragment) getSupportFragmentManager().findFragmentById(R.id.container_b);
        cardsFragment.setCardDeck(start, end);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.mainPageToolbar);
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);



        Typeface typefaceENG = Typeface.createFromAsset(getApplication().getAssets(), "chibi.ttf");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("WordChai");



    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){


            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
           finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        if(item.getItemId() == R.id.main_settings_btn){

            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);


        }

        if(item.getItemId() == R.id.main_browse_users_btn){

           Intent browseUsersIntent = new Intent(MainActivity.this, BrowseUsersActivity.class);
            startActivity(browseUsersIntent);

        }


        return true;

    }




}
