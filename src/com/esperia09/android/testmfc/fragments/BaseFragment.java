package com.esperia09.android.testmfc.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    
    private Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }
    
    public Context getApplicationContext() {
        return mContext;
    }
}
