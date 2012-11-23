
package com.esperia09.android.testmfc.tabs;

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

public class WebPushFragment extends BaseFragment implements OnClickListener, OnMfcEnabledListener {
    public static final String TAB_TAG = "Web";

    private EditText mEditUrl;
    private Button mBtnSend;
    private MfcAccesser mMfc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_webpush, null);

        mEditUrl = (EditText) v.findViewById(R.id.editUrl);
        mBtnSend = (Button) v.findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(this);

        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        mEditUrl.setText("http://www.android.com/");
    }

    @Override
    public void onAvailable(MfcAccesser mfc) {
        mMfc = mfc;
        mBtnSend.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        try {
            mMfc.pushToStartBrowser(mEditUrl.getText().toString());
        } catch (Exception e) {
            Log.e("pushToStartBrowser", e.getMessage(), e);
        }
    }
}
