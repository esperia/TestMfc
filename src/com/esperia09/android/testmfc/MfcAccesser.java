
package com.esperia09.android.testmfc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.PushIntentSegment;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.PushStartAppSegment;
import com.felicanetworks.mfc.PushStartBrowserSegment;
import com.felicanetworks.mfc.PushStartMailerSegment;

/**
 * MFCサービスにアクセスするためのAPIです。
 * 
 * @author esperia
 */
public class MfcAccesser implements ServiceConnection, FelicaEventListener {

    private Context mContext;
    private OnMfcListener mListener;
    private boolean mConnected = false;
    private Felica mFelica;
    private int mState = FelicaState.DISCONNECTED;
    private OnMfcActivatedListener mActivatedListener;

    public MfcAccesser(Context context, OnMfcListener l) {
        if (l == null) {
            throw new NullPointerException("OnMfcListener is MUST be required!!");
        }
        mContext = context;
        mListener = l;
    }

    /**
     * FeliCaサービスに接続します。
     */
    public void connect() {
        if (mConnected) {
            // 接続済み
            return;
        }

        Intent intent = new Intent();
        intent.setClass(mContext, Felica.class);
        if (!mContext.bindService(intent, this, Context.BIND_AUTO_CREATE)) {
            mListener.onMfcException(FelicaState.CONNECT, new Exception(
                    "connect error:Context#bindService() failed."));
        }
        // 接続状態変更はonServiceConnected()が呼び出されたタイミングで実施
    }

    /**
     * FeliCaサービスを切断します。
     * 
     * @throws Exception
     */
    public void disconnect() {
        mState = FelicaState.DISCONNECTED;
        if (!mConnected) {
            return;
        }

        mContext.unbindService(this);

        // 接続状態を解除
        mConnected = false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mState = FelicaState.CONNECT;
        // Felicaとの接続が確立されたので、Felicaインスタンスを取得する
        mFelica = ((Felica.LocalBinder) service).getInstance();
        mConnected = true;

        mListener.onServiceConnected();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mState = FelicaState.DISCONNECTED;
        // Felicaの設定解除
        mFelica = null;
        mConnected = false;

        if (mListener != null) {
            mListener.onServiceDisconnected();
        }
    }

    /**
     * Felicaの利用開始処理を実行します。
     */
    public void activateFelica(OnMfcActivatedListener l) {
        mActivatedListener = l;
        try {
            if (mFelica != null) {
                mFelica.activateFelica(null, this);
            }
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.ACTIVATE, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.ACTIVATE, e);
        }
    }

    /**
     * Felicaチップをオープンします。
     * 
     * @return isOpen
     */
    public boolean open() {
        try {
            mFelica.open();
            mState = FelicaState.OPEN;
            return true;
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.OPEN, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.OPEN, e);
        }
        return false;
    }

    /**
     * Felicaチップをクローズします。
     * 
     * @return
     */
    public boolean close() {
        try {
            mFelica.close();
            mState = FelicaState.CLOSE;
            return true;
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.CLOSE, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.CLOSE, e);
        }
        return false;
    }

    /**
     * Felicaの利用終了処理を実行します。
     */
    public boolean inactivateFelica() {
        try {
            mFelica.inactivateFelica();
            mState = FelicaState.INACTIVATE;
            return true;
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.INACTIVATE, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.INACTIVATE, e);
        }
        return false;
    }

    /**
     * Push送信によって、ブラウザを起動します。
     * 
     * @return
     */
    public boolean pushToStartBrowser(String url) {
        try {
            // ブラウザ起動パラメータの生成
            PushStartBrowserSegment pushSegment = new PushStartBrowserSegment(url, null);

            // Push送信
            mFelica.push(pushSegment);
            return true;
        } catch (IllegalArgumentException e) {
            mListener.onMfcException(FelicaState.PUSH, e);
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.PUSH, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.PUSH, e);
        }
        return false;
    }

    /**
     * Push送信によって、ブラウザを起動します。
     * 
     * @return
     */
    public boolean pushToStartMail(String[] to, String[] cc, String subject, String body,
            String mailerStartupParam) {
        try {
            // ブラウザ起動パラメータの生成
            PushStartMailerSegment pushStartMailerSegment = new PushStartMailerSegment(to, cc,
                    subject, body, mailerStartupParam);

            // Push送信
            mFelica.push(pushStartMailerSegment);
            return true;
        } catch (IllegalArgumentException e) {
            mListener.onMfcException(FelicaState.PUSH, e);
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.PUSH, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.PUSH, e);
        }
        return false;
    }

    /**
     * Push送信によって、ブラウザを起動します。
     */
    public boolean pushStartIntent(Intent intent) {
        PushIntentSegment seg = new PushIntentSegment(intent);
        return pushStartIntent(seg);
    }

    /**
     * Push送信によって、ブラウザを起動します。
     */
    public boolean pushStartIntent(String appURL, String appIdentificationCode,
            String[] appStartupParam) {
        PushStartAppSegment seg = new PushStartAppSegment(appURL, appIdentificationCode,
                appStartupParam);
        return pushStartIntent(seg);
    }

    private boolean pushStartIntent(PushSegment seg) {
        try {

            mFelica.push(seg);
            return true;
        } catch (IllegalArgumentException e) {
            mListener.onMfcException(FelicaState.PUSH, e);
        } catch (FelicaException e) {
            MfcCause.handleFelicaException(e, mFelica);
            mListener.onMfcException(FelicaState.PUSH, e);
        } catch (Exception e) {
            mListener.onMfcException(FelicaState.PUSH, e);
        }
        return false;
    }

    public Felica getFelica() {
        return mFelica;
    }

    public interface OnMfcListener {
        /**
         * FeliCaサービスへの接続が完了した時に呼ばれます。
         * 
         * @param felica
         * @throws IllegalArgumentException
         * @throws FelicaException
         */
        public void onServiceConnected();

        public void onServiceDisconnected();

        /**
         * MFCにて例外エラーが発生した時に呼ばれます。
         * 
         * @param state {@link MfcAccesser}クラスの
         * @param e
         */
        public void onMfcException(int state, Exception e);
    }

    public interface OnMfcActivatedListener {
        /**
         * MFCにてactivateに成功した時に呼ばれます。
         */
        public void onActivated();
    }

    public interface FelicaState {
        public static final int DISCONNECTED = 0x00;
        public static final int CONNECT = 0x01;
        public static final int ACTIVATE = 0x02;
        public static final int OPEN = 0x03;
        public static final int CLOSE = 0x04;
        public static final int INACTIVATE = 0x05;

        public static final int PUSH = 0x10;
    }

    @Override
    public void errorOccurred(int id, String msg, AppInfo otherAppInfo) {
        mState = FelicaState.CONNECT;
        mListener.onMfcException(FelicaState.ACTIVATE, new RuntimeException());
    }

    @Override
    public void finished() {
        mState = FelicaState.ACTIVATE;
        if (mActivatedListener != null) {
            mActivatedListener.onActivated();
        }
    }

    public int getState() {
        return mState;
    }
}
