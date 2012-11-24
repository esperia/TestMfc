
package com.esperia09.android.testmfc;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.esperia09.android.testmfc.MfcAccesser.FelicaState;
import com.esperia09.android.testmfc.MfcAccesser.OnMfcActivatedListener;
import com.esperia09.android.testmfc.MfcAccesser.OnMfcListener;
import com.esperia09.android.testmfc.tabs.IntentPushFragment;
import com.esperia09.android.testmfc.tabs.MailPushFragment;
import com.esperia09.android.testmfc.tabs.WebPushFragment;
import com.esperia09.android.testmfc.utils.Logger;

public class TestMfcActivity extends FragmentActivity implements OnMfcListener,
        OnMfcActivatedListener, OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private MfcAccesser mMfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMfc = new MfcAccesser(getApplicationContext(), this);

        setContentView(R.layout.activity_test_mfc);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(
                WebPushFragment.TAB_TAG).setIndicator(WebPushFragment.TAB_TAG),
                WebPushFragment.class, null
                );
        mTabHost.addTab(mTabHost.newTabSpec(
                MailPushFragment.TAB_TAG).setIndicator(MailPushFragment.TAB_TAG),
                MailPushFragment.class, null
                );
        mTabHost.addTab(mTabHost.newTabSpec(
                IntentPushFragment.TAB_TAG).setIndicator(IntentPushFragment.TAB_TAG),
                IntentPushFragment.class, null
                );
        mTabHost.setOnTabChangedListener(this);

        mMfc.connect();
    }
    
    @Override
    protected void onResume() {
        super.onResume();

        mMfc.activateFelica(this);
    }

    @Override
    public void onServiceConnected() {
        Logger.d();
        mMfc.activateFelica(this);
    }

    @Override
    public void onActivated() {
        Logger.d();
        if (mMfc.open()) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
            if (f instanceof OnMfcEnabledListener) {
                OnMfcEnabledListener l = (OnMfcEnabledListener) f;
                l.onAvailable(mMfc);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        mMfc.close();
        mMfc.inactivateFelica();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMfc.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_test_mfc, menu);
        return true;
    }

    @Override
    public void onServiceDisconnected() {
        Logger.d();
        Toast.makeText(getApplicationContext(), "onServiceDisconnected", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onMfcException(int state, Exception e) {
        Log.e("Push", "onMfcException", e);
        switch (state) {
            case FelicaState.OPEN:
                mMfc.inactivateFelica();
                break;
            default:
                break;
        }
        Toast.makeText(getApplicationContext(), "error occured!=" + e.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public interface OnMfcEnabledListener {
        /**
         * OnMfcEnabled
         */
        public void onAvailable(MfcAccesser mfc);
    }

    @Override
    public void onTabChanged(String tag) {
        int state = mMfc.getState();
        if (state == FelicaState.OPEN) {
            callAvailable();
        }
    }
    
    /**
     * FIXME タイミングを一つずらす。onTabChangedでFragmentを呼ぶと切り替え前のものが出てくる問題の対処
     */
    private void callAvailable() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
                if (f instanceof OnMfcEnabledListener) {
                    OnMfcEnabledListener l = (OnMfcEnabledListener) f;
                    l.onAvailable(mMfc);
                }
            }
        });
    }

    public MfcAccesser getMfc() {
        return mMfc;
    }
}
