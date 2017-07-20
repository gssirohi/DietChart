package com.techticz.app.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.techticz.app.constant.AppConstants;
import com.techticz.app.utility.AppLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServiceRequest {

    private static String sessionId;
    protected boolean isSecureConnectionRequest;
    private String requestString = "";
    private String payload;
    private String url;
    private Class<? extends ResponseContainer> responsibleClass;
    private RequestMethod requestMethod;
    private RequestCodes requestCode;
    private String path = "";
    private String method = "";
    private int ConnectionTimeout = AppConstants.MAX_CONNECTION_TIMEOUT;
    private Request currentRequest;
    private String appId;

    public ServiceRequest(Request request, Context context) {
        url = AppConstants.BASE_URL;
        requestMethod = request.getRequestMethod();
        currentRequest = request;
        HashMap<String, String> requestParamMap = request.getRequestParams();
        String appVersion = "";
        try {
            appId = context.getPackageName();
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(appId, 0);
            appVersion = pInfo.versionCode + "";
            //requestParamMap.put("appId", appId);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appVersion != null) {
            //requestParamMap.put("appVersion", appVersion);
        }
        if (sessionId != null) {
            //requestParamMap.put("sessionId", sessionId);
        }
        //requestParamMap.put("deviceId", CommonUtils.getDeviceId(context));
        //requestParamMap.put("osVersion", android.os.Build.VERSION.SDK_INT + "");

        if ((requestMethod == RequestMethod.POST) || (requestMethod == RequestMethod.PUT)) {
            if (request.getRequestFormat().equals(RequestFormat.JSON)) {
                try {
                    payload = TransformableFactory.tranformObjectToJson(request, TransformableFactory.GsonType.DEFAULT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.requestString = getPostDataString(request.getRequestParams());
                AppLogger.i(this, " Post Method Request String is " + requestString);
            }
        } else {
            requestString = buildEncodedQueryString(request.getRequestParams());
        }
    }

    public static String buildEncodedQueryString(
            HashMap<String, String> requestParams) {
        String queryString = "?";
        if (requestParams == null) {
            return "";
        }
        Iterator<Map.Entry<String, String>> it = requestParams.entrySet()
                .iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            // null pointer exception handle via checking pair key is not null if it is null then we pass empty value.
            try {
                String value;
                if (pairs.getValue() != null) {
                    value = pairs.getValue().toString();
                } else {
                    value = " ";
                }
                queryString += URLEncoder.encode(pairs.getKey().toString(),
                        "UTF-8")
                        + "="
                        + URLEncoder.encode(value,
                        "UTF-8") + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }
        if (queryString.length() > 0)
            queryString = queryString.substring(0, queryString.length() - 1);
        return queryString;
    }


    public Request getCurrentRequest() {
        return currentRequest;
    }

    private String getPostDataString(HashMap<String, String> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getConnectionTImeout() {
        return ConnectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        ConnectionTimeout = connectionTimeout;
    }

    public boolean isSecureConnectionRequest() {
        return isSecureConnectionRequest;
    }

    public void setSecureConnectionRequest(boolean isSecureConnectionRequest) {
        this.isSecureConnectionRequest = isSecureConnectionRequest;
    }

    public String getRequestString() {
        return requestString;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class<? extends ResponseContainer> getResponsibleClass() {
        return responsibleClass;
    }

    public void setResponsibleClass(Class<? extends ResponseContainer> responsibleClass) {
        this.responsibleClass = responsibleClass;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public RequestCodes getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(RequestCodes requestCode) {
        this.requestCode = requestCode;
    }

}
