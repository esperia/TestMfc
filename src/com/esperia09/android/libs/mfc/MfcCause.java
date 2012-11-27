
package com.esperia09.android.libs.mfc;

import java.util.Map;
import java.util.WeakHashMap;

import com.esperia09.android.testmfc.utils.Logger;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaException;

/**
 * エラーの原因をログに出力するためのクラスです。
 * @see <a href="http://www.felicanetworks.co.jp/tech/adhoc.html">SDK</a>
 */
public class MfcCause {

    /**
     * FelicaExceptionの種別を文字列に変換するためのマップ。
     */
    private static final Map<Integer, String> FELICA_EXCEPTION_ID_CONVERSION_MAP = new WeakHashMap<Integer, String>() {

        {
            put(FelicaException.ID_UNKNOWN_ERROR, "ID_UNKNOWN_ERROR");
            put(FelicaException.ID_ILLEGAL_STATE_ERROR, "ID_ILLEGAL_STATE_ERROR");
            put(FelicaException.ID_IO_ERROR, "ID_IO_ERROR");
            put(FelicaException.ID_GET_KEY_VERSION_ERROR, "ID_GET_KEY_VERSION_ERROR");
            put(FelicaException.ID_READ_ERROR, "ID_READ_ERROR");
            put(FelicaException.ID_WRITE_ERROR, "ID_WRITE_ERROR");
            put(FelicaException.ID_SET_NODECODESIZE_ERROR, "ID_SET_NODECODESIZE_ERROR");
            put(FelicaException.ID_OPEN_ERROR, "ID_OPEN_ERROR");
            put(FelicaException.ID_GET_NODE_INFORMATION_ERROR, "ID_GET_NODE_INFORMATION_ERROR");
            put(FelicaException.ID_GET_PRIVACY_NODE_INFORMATION_ERROR,
                    "ID_GET_PRIVACY_NODE_INFORMATION_ERROR");
            put(FelicaException.ID_SET_PRIVACY_ERROR, "ID_SET_PRIVACY_ERROR");
            put(FelicaException.ID_PERMISSION_ERROR, "ID_PERMISSION_ERROR");
            put(FelicaException.ID_GET_BLOCK_COUNT_INFORMATION_ERROR,
                    "ID_GET_BLOCK_COUNT_INFORMATION_ERROR");
        }
    };

    /**
     * FelicaExceptionのタイプを文字列に変換するためのマップ。
     */
    private static final Map<Integer, String> FELICA_EXCEPTION_TYPE_CONVERSION_MAP = new WeakHashMap<Integer, String>() {

        {
            put(FelicaException.TYPE_NOT_OPENED, "TYPE_NOT_OPENED");
            put(FelicaException.TYPE_CURRENTLY_ONLINE, "TYPE_CURRENTLY_ONLINE");
            put(FelicaException.TYPE_NOT_SELECTED, "TYPE_NOT_SELECTED");
            put(FelicaException.TYPE_NOT_ACTIVATED, "TYPE_NOT_ACTIVATED");
            put(FelicaException.TYPE_INVALID_RESPONSE, "TYPE_INVALID_RESPONSE");
            put(FelicaException.TYPE_TIMEOUT_OCCURRED, "TYPE_TIMEOUT_OCCURRED");
            put(FelicaException.TYPE_OPEN_FAILED, "TYPE_OPEN_FAILED");
            put(FelicaException.TYPE_SELECT_FAILED, "TYPE_SELECT_FAILED");
            put(FelicaException.TYPE_GET_KEY_VERSION_FAILED, "TYPE_GET_KEY_VERSION_FAILED");
            put(FelicaException.TYPE_SERVICE_NOT_FOUND, "TYPE_SERVICE_NOT_FOUND");
            put(FelicaException.TYPE_BLOCK_NOT_FOUND, "TYPE_BLOCK_NOT_FOUND");
            put(FelicaException.TYPE_PIN_NOT_CHECKED, "TYPE_PIN_NOT_CHECKED");
            put(FelicaException.TYPE_READ_FAILED, "TYPE_READ_FAILED");
            put(FelicaException.TYPE_PURSE_FAILED, "TYPE_PURSE_FAILED");
            put(FelicaException.TYPE_CASH_BACK_FAILED, "TYPE_CASH_BACK_FAILED");
            put(FelicaException.TYPE_INVALID_PIN, "TYPE_INVALID_PIN");
            put(FelicaException.TYPE_CHECK_PIN_LIMIT, "TYPE_CHECK_PIN_LIMIT");
            put(FelicaException.TYPE_CHECK_PIN_OVERRUN, "TYPE_CHECK_PIN_OVERRUN");
            put(FelicaException.TYPE_WRITE_FAILED, "TYPE_WRITE_FAILED");
            put(FelicaException.TYPE_ENABLE_PIN_FAILED, "TYPE_ENABLE_PIN_FAILED");
            put(FelicaException.TYPE_FELICA_NOT_SET, "TYPE_FELICA_NOT_SET");
            put(FelicaException.TYPE_DEVICELIST_NOT_SET, "TYPE_DEVICELIST_NOT_SET");
            put(FelicaException.TYPE_LISTENER_NOT_SET, "TYPE_LISTENER_NOT_SET");
            put(FelicaException.TYPE_COMMUNICATION_START_FAILED, "TYPE_COMMUNICATION_START_FAILED");
            put(FelicaException.TYPE_SET_NODECODESIZE_FAILED, "TYPE_SET_NODECODESIZE_FAILED");
            put(FelicaException.TYPE_GET_CONTAINER_ISSUE_INFORMATION_FAILED,
                    "TYPE_GET_CONTAINER_ISSUE_INFORMATION_FAILED");
            put(FelicaException.TYPE_NOT_IC_CHIP_FORMATTING, "TYPE_NOT_IC_CHIP_FORMATTING");
            put(FelicaException.TYPE_ILLEGAL_NODECODE, "TYPE_ILLEGAL_NODECODE");
            put(FelicaException.TYPE_GET_NODE_INFORMATION_FAILED,
                    "TYPE_GET_NODE_INFORMATION_FAILED");
            put(FelicaException.TYPE_GET_PRIVACY_NODE_INFORMATION_FAILED,
                    "TYPE_GET_PRIVACY_NODE_INFORMATION_FAILED");
            put(FelicaException.TYPE_SET_PRIVACY_FAILED, "TYPE_SET_PRIVACY_FAILED");
            put(FelicaException.TYPE_NOT_CLOSED, "TYPE_NOT_CLOSED");
            put(FelicaException.TYPE_ILLEGAL_METHOD_CALL, "TYPE_ILLEGAL_METHOD_CALL");
            put(FelicaException.TYPE_PUSH_FAILED, "TYPE_PUSH_FAILED");
            put(FelicaException.TYPE_ALREADY_ACTIVATED, "TYPE_ALREADY_ACTIVATED");
            put(FelicaException.TYPE_GET_BLOCK_COUNT_INFORMATION_FAILED,
                    "TYPE_GET_BLOCK_COUNT_INFORMATION_FAILED");
            put(FelicaException.TYPE_RESET_FAILED, "TYPE_RESET_FAILED");
            put(FelicaException.TYPE_GET_SYSTEM_CODE_LIST_FAILED,
                    "TYPE_GET_SYSTEM_CODE_LIST_FAILED");
            put(FelicaException.TYPE_GET_CONTAINER_ID_FAILED, "TYPE_GET_CONTAINER_ID_FAILED");
            put(FelicaException.TYPE_REMOTE_ACCESS_FAILED, "TYPE_REMOTE_ACCESS_FAILED");
            put(FelicaException.TYPE_CURRENTLY_ACTIVATING, "TYPE_CURRENTLY_ACTIVATING");
            put(FelicaException.TYPE_ILLEGAL_SYSTEMCODE, "TYPE_ILLEGAL_SYSTEMCODE");
            put(FelicaException.TYPE_GET_RFS_STATE_FAILED, "TYPE_GET_RFS_STATE_FAILED");
            put(FelicaException.TYPE_INVALID_SELECTED_INTERFACE, "TYPE_INVALID_SELECTED_INTERFACE");
            put(FelicaException.TYPE_FELICA_NOT_AVAILABLE, "TYPE_FELICA_NOT_AVAILABLE");
        }
    };

    /**
     * FelicaExceptionの内容に応じて、エラー処理を実施します。 処理の内容は以下の通り。
     * ・ID_UNKNOWN_ERRORが発生した場合に、FeliCaチップのクローズ、およびFeliCaチップの利用終了処理を実施
     * ・(ID_OPEN_ERROR, TYPE_NOT_IC_CHIP_FORMATTING)が発生した場合に、FeliCaチップの利用終了処理を実施
     * ・エラー内容を画面に表示
     * 
     * @param e 処理対象のFelicaException
     */
    public static void handleFelicaException(FelicaException e, Felica felica) {

        String errMsg;
        String iDString = FELICA_EXCEPTION_ID_CONVERSION_MAP.get(e.getID());
        String typeString = FELICA_EXCEPTION_TYPE_CONVERSION_MAP.get(e.getType());
        String additionalMsg = null;

        switch (e.getID()) {
            case FelicaException.ID_UNKNOWN_ERROR:
                // 未知のエラーが発生
                // 復帰不能なのでFelica#close()、#inactivateFelica()を実行
                try {
                    felica.close();
                    felica.inactivateFelica();
                } catch (FelicaException e1) {
                    // 強制終了処理に失敗した場合、キャッチした例外を無視
                }
                break;
            case FelicaException.ID_OPEN_ERROR:
                if (e.getType() == FelicaException.TYPE_NOT_IC_CHIP_FORMATTING) {
                    // Felica#inactivateFelica()を実行し、FeliCaチップの利用終了処理を実施
                    try {
                        felica.inactivateFelica();
                    } catch (FelicaException e1) {
                        // 利用終了処理に失敗した場合、キャッチした例外を無視
                    }
                }
                break;
            default:
                break;
        }

        // エラー内容をViewに表示

        // ベースメッセージ生成
        errMsg = "caught FelicaException\n(ID, TYPE)=(" + iDString + ", " + typeString + ")";

        // 追加メッセージ作成
        switch (e.getID()) {
            case FelicaException.ID_UNKNOWN_ERROR:
                // 復帰困難なエラーが発生した旨を表示
                additionalMsg = "(Non-recoverable error)";
                break;
            case FelicaException.ID_READ_ERROR:
            case FelicaException.ID_WRITE_ERROR:
            case FelicaException.ID_GET_NODE_INFORMATION_ERROR:
            case FelicaException.ID_GET_PRIVACY_NODE_INFORMATION_ERROR:
            case FelicaException.ID_GET_BLOCK_COUNT_INFORMATION_ERROR:
            case FelicaException.ID_SET_PRIVACY_ERROR:
                // ステータスフラグ取得
                additionalMsg = "Status Flag1:" + e.getStatusFlag1() + ", StatusFlag2:"
                        + e.getStatusFlag2();
                break;
            default:
                break;
        }

        if (additionalMsg != null) {
            errMsg += "\n";
            errMsg += additionalMsg;
        }

        Logger.e(errMsg);
    }

}
