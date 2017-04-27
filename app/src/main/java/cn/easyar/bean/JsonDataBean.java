
package cn.easyar.bean;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class JsonDataBean extends BaseBean<JsonDataBean> {
    public ArrayList<Target> images;//

    public static class Target implements Serializable {
        public String image;//必填
        public String name;//可填
        public String uid;//预留字段；目前做为了 对应视频地址；但必填
        public ArrayList<Double> size;//[8.56, 5.4]//一般不填写
    }

    public static JsonDataBean createObject(String str){
        JSONObject jsonObject;
        JsonDataBean jsonDataBean = new JsonDataBean();
        try {
             jsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonDataBean;
        }

        jsonDataBean.parseJSON(jsonObject);
        return jsonDataBean;
    }

    @Override
    public JsonDataBean parseJSON(JSONObject jsonObj) {
        if(jsonObj == null) {
            return null;
        }

        JSONArray jsonArray = jsonObj.optJSONArray("images");
        if(jsonArray != null && jsonArray.length()>0){
            images = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonChildObject = jsonArray.optJSONObject(i);
                if(jsonChildObject != null) {
                    Target target = new Target();
                    target.image = jsonChildObject.optString("image");
                    target.uid = jsonChildObject.optString("uid");
                    target.name = jsonChildObject.optString("name");
//                target.size = jsonChildObject.optJSONArray("size");
                    JSONArray jsonSizeArray = jsonChildObject.optJSONArray("size");
                    if(jsonSizeArray != null && jsonSizeArray.length()>0){
                        target.size = new ArrayList<>();
                        for(int j =0;j<jsonSizeArray.length();j++){
                            Double temp = jsonSizeArray.optDouble(j);
                            target.size.add(temp);
                        }
                    }
                    images.add(target);
                }
            }
        }
        return this;
    }

    @Override
    public JSONObject toJSON() {
        if(images == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i=0;i<images.size();i++){
                Target target = images.get(i);
                JSONObject jsonChildObject = new JSONObject();
                jsonChildObject.put("image",target.image);
                jsonChildObject.put("name",target.name);
                jsonChildObject.put("uid",target.uid);
                jsonChildObject.put("size",target.size);
                jsonArray.put(jsonChildObject);
            }
            jsonObject.put("images",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public JsonDataBean cursorToBean(Cursor cursor) {
        return null;
    }

    @Override
    public ContentValues beanToValues() {
        return null;
    }
}
