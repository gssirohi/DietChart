package com.techticz.app.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techticz.app.utility.AppLogger;
import com.techticz.app.utility.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;

public class TransformableFactory {

    public enum GsonType {DEFAULT, EXCLUDE_TRANSIENT_FIELDS, EXCLUDE_FIELDS_WITHOUT_EXPOSE}

    public static Gson newGson(GsonType gsonType) {
        if (gsonType == GsonType.EXCLUDE_TRANSIENT_FIELDS)
            return new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        else if (gsonType == GsonType.EXCLUDE_FIELDS_WITHOUT_EXPOSE)
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        else
            return new Gson();
    }

    public static String tranformObjectToJson(Transformable obj, GsonType gsonType) throws Exception {
        String jsonString = "";
        try {
            Gson gson = newGson(gsonType);
            jsonString = gson.toJson(obj);
            //Log.i("JSON", jsonString);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }

    public static ResponseContainer parseJson(InputStream responseStream, Class<? extends ResponseContainer> responsibleClass) throws IOException {
        ResponseContainer mResponse = null;
        try {
            String responseString = "";
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
            responseString = CommonUtils.convertStreamToString(responseStream);
            AppLogger.i("TransformableFactory", "Response::" + responseString);
            mResponse = gson.fromJson(responseString, responsibleClass);

            if (mResponse != null) {
                AppLogger.i("TransformableFactory", mResponse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (responseStream != null) {
                responseStream.close();
            }
        }
        return mResponse;
    }

}
