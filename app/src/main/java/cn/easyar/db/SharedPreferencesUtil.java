package cn.easyar.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPreferencesUtil {

	/**
	 * 文件名
	 */
	private static final String sh_data_name = "AR_Perry";
	private static final String TAG = "SharedPreferencesUtil";
	private static SharedPreferences saveInfo;
	private static Editor saveEditor;
	private volatile static SharedPreferencesUtil uniqueInstance;

	//本地数据加载
	private String localData = "localData";

	private SharedPreferencesUtil() {
	}

	public static SharedPreferencesUtil getInstance(Context context) {
		if (uniqueInstance == null) {
			synchronized (SharedPreferencesUtil.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new SharedPreferencesUtil();
					saveInfo = context.getSharedPreferences(sh_data_name, Context.MODE_PRIVATE);
					saveEditor = saveInfo.edit();
				}
			}
		}
		return uniqueInstance;
	}


	/**
	 * 保存本次本地数据
	 * @param dataStr
	 * @return
     */
	public boolean putLocalData(String dataStr){
		Log.e(TAG,"putLocalData:"+dataStr);
		saveEditor.putString(localData,dataStr);
		return saveEditor.commit();
	}

	/**
	 * 获取本地数据
	 * @return
     */
	public String getLocalData(){
		return saveInfo.getString(localData,"");
	}

	public boolean removeLocalData() {
		saveEditor.remove(localData);
		return saveEditor.commit();
	}



	/**
	 * device id
	 */
	public String getDeviceId() {
		return saveInfo.getString("decviceid", "");
	}

	public void putDeviceId(String deviceid) {
		saveEditor.putString("decviceid", deviceid);
		saveEditor.commit();
	}

	/**
	 * 保存键盘高度
	 * @param keyboardMethod
	 * @param keyboardHeight
	 */
	public void putKeyBoardHeight(String keyboardMethod, int keyboardHeight) {
		saveEditor.putInt(keyboardMethod, keyboardHeight);
		saveEditor.commit();
	}

	/**
	 * 获取保存的键盘高度
	 * @param keyboardMethod
	 * @return
	 */
	public int getKeyBoardHeight(String keyboardMethod) {
		return saveInfo.getInt(keyboardMethod, 0);
	}

}
