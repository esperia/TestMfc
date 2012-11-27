package com.esperia09.android.libs.mfc;

/**
 * FeliCaチップの利用状況を保持します。
 * 
 * @author esperia
 */
public interface FelicaState {
    /**
     * サービスから切断している状態。
     */
    public static final int DISCONNECTED = 0x00;
    /**
     * サービスに接続している時
     */
    public static final int CONNECT = 0x01;
    /**
     * FeliCaチップ利用開始時
     */
    public static final int ACTIVATE = 0x02;
    /**
     * FeliCaチップをオープンしている時
     */
    public static final int OPEN = 0x03;

    /**
     * フェリカにPUSHした時
     */
    public static final int PUSH = 0x10;
}
