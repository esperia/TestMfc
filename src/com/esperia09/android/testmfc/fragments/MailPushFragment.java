
package com.esperia09.android.testmfc.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esperia09.android.libs.mfc.MfcAccesser;
import com.esperia09.android.testmfc.R;
import com.esperia09.android.testmfc.TestMfcActivity.OnMfcEnabledListener;

public class MailPushFragment extends BaseFragment implements OnClickListener, OnMfcEnabledListener {
    public static final String TAB_TAG = "Mail";

    private Button mBtnSend;
    private MfcAccesser mMfc;

    private EditText mEditTo;
    private EditText mEditCc;
    private EditText mEditSubject;
    private EditText mEditBody;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_mailpush, null);

        mEditTo = (EditText) v.findViewById(R.id.editTo);
        mEditCc = (EditText) v.findViewById(R.id.editCc);
        mEditSubject = (EditText) v.findViewById(R.id.editSubject);
        mEditBody = (EditText) v.findViewById(R.id.editBody);
        mBtnSend = (Button) v.findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(this);

        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        mEditTo.setText("hogefuga@example.com");
        mEditSubject.setText("タイトル");
        mEditBody.setText("こんにちは！");
    }

    @Override
    public void onAvailable(MfcAccesser mfc) {
        mMfc = mfc;
        mBtnSend.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        try {
            mMfc.pushToStartMail(
                    new String[] {
                        mEditTo.getText().toString()
                    },
                    new String[] {
                        mEditCc.getText().toString()
                    },
                    mEditSubject.getText().toString(),
                    mEditBody.getText().toString(),
                    null
                    );
        } catch (Exception e) {
            Log.e("pushToStartBrowser", e.getMessage(), e);
        }
    }
}
