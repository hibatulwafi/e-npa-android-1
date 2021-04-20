package com.sukabumikota.sipajar.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sukabumikota.sipajar.objekpajak.OPFragment;
import com.sukabumikota.sipajar.objekpajak.OPFragment2;

public class TabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                OPFragment frag1 = new OPFragment();
                return frag1;
            case 1:
                OPFragment2 frag2 = new OPFragment2();
                return frag2;
            default:
                return null;
        }
    }
}