
package com.esperia09.android.testmfc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.esperia09.android.libs.mfc.FelicaState;
import com.esperia09.android.libs.mfc.MfcAccesser;
import com.esperia09.android.libs.mfc.MfcAccesser.OnMfcActivatedListener;
import com.esperia09.android.libs.mfc.MfcAccesser.OnMfcListener;
import com.esperia09.android.testmfc.fragments.IntentPushFragment;
import com.esperia09.android.testmfc.fragments.MailPushFragment;
import com.esperia09.android.testmfc.fragments.WebPushFragment;
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

        // フラグメントのタブを追加します。
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
    public void onServiceDisconnected() {
        Logger.d();
        Toast.makeText(getApplicationContext(), "onServiceDisconnected", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onMfcException(int state, Exception e) {
        Logger.e("onMfcException", e);
        switch (state) {
            case FelicaState.ACTIVATE:
            case FelicaState.OPEN:
                mMfc.forceInactivate();
                break;
        }
        Toast.makeText(getApplicationContext(), "error occured!=" + e.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public interface OnMfcEnabledListener {
        /**
         * FeliCaチップのオープンが終わった時、これらを実装しているFragmentに対して通知を行います。
         */
        public void onAvailable(MfcAccesser mfc);
    }

    @Override
    public void onTabChanged(String tag) {
        int state = mMfc.getState();
        if (state == FelicaState.OPEN) {
            // タブ変更後のFragmentを呼び出して、MFCが有効であることを通知する
            Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
            if (f instanceof OnMfcEnabledListener) {
                OnMfcEnabledListener l = (OnMfcEnabledListener) f;
                l.onAvailable(mMfc);
            }
        }
    }
}
