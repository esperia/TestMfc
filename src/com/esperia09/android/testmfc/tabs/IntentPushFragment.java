
package com.esperia09.android.testmfc.tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esperia09.android.testmfc.BaseFragment;
import com.esperia09.android.testmfc.MfcAccesser;
import com.esperia09.android.testmfc.R;
import com.esperia09.android.testmfc.TestMfcActivity.OnMfcEnabledListener;

public class IntentPushFragment extends BaseFragment implements OnClickListener,
        OnMfcEnabledListener {
    public static final String TAB_TAG = "Intent";

    private Button mBtnSend;
    private MfcAccesser mMfc;

    private EditText mEditLatitude;
    private EditText mEditLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_intentpush, null);

        mEditLatitude = (EditText) v.findViewById(R.id.editLatitude);
        mEditLongitude = (EditText) v.findViewById(R.id.editLongitude);
        mBtnSend = (Button) v.findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mEditLatitude.setText("34.706852");
        mEditLongitude.setText("135.503158");
        // Intent { act=android.intent.action.MAIN
        // cmp=com.android.settings/.ApplicationSettings }

    }

    @Override
    public void onAvailable(MfcAccesser mfc) {
        mMfc = mfc;
        mBtnSend.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" + mEditLatitude.getText().toString() + ","
                    + mEditLongitude.getText().toString() + ""));
            // intent.setPackage(mEditPackage.getText().toString());
            // intent.setPackage(mEditPackage.getText().toString());
            mMfc.pushStartIntent(intent);
        } catch (Exception e) {
            Log.e("pushToStartBrowser", e.getMessage(), e);
        }
    }
}
