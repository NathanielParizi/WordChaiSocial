package com.psiarb.go.wordchai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter{


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){

            case 0:
                CardsFragment CardsFragment = new CardsFragment();
                return CardsFragment;
            case 1:
                MiscFragment MiscFragment = new MiscFragment();
                return MiscFragment;
            case 2:
                ChatFragment ChatFragment = new ChatFragment();
                return ChatFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){


        switch(position) {
            case 0:
                return "CARDS";
            case 1:
                return "DECK";
            case 2:
                return "CHAT";
            default:
                return null;
        }

    }



}
