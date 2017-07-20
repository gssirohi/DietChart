package com.techticz.app.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.techticz.app.utility.AppLogger;
import com.techticz.app.utility.CommonUtils;

import java.util.HashMap;
import java.util.Map;

public class Request implements Transformable, Parcelable {

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        public Request createFromParcel(Parcel source) {
            return new Request(source);
        }

        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
    protected transient RequestMethod requestMethod = RequestMethod.GET;
    private HashMap<String, String> requestParams = new HashMap<String, String>();
    private RequestFormat requestFormat = RequestFormat.MAP;

    protected Request(Parcel source) {

        requestParams = new HashMap<String, String>();
        final int N = source.readInt();

        for (int i = 0; i < N; i++) {
            String key = source.readString();
            String val = source.readString();
            requestParams.put(key, val);
        }
        requestFormat = (RequestFormat) source.readSerializable();
    }

    public Request() {

    }

    public RequestFormat getRequestFormat() {
        return requestFormat;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public HashMap<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(HashMap<String, String> currentRequestParams) {
        AppLogger.i(this, "Request Parameters::" + currentRequestParams);
        this.requestParams.clear();
        if (currentRequestParams == null) {
            return;
        }
        for (Map.Entry<String, String> entry : currentRequestParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (CommonUtils.isNullOrEmpty(value)) {
                value = "";
            }
        }
        this.requestParams.putAll(currentRequestParams);
    }

    @Override
    public int describeContents() {

        return 0;
    }

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        final int N = requestParams.size();
        dest.writeInt(N);
        if (N > 0) {
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }
        dest.writeSerializable(requestFormat);
    }

}
