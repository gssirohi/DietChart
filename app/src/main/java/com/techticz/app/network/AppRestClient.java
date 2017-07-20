package com.techticz.app.network;

import android.util.Log;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppConstants;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.repository.cache.CacheRepository;
import com.techticz.app.utility.AppLogger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gssirohi on 3/3/16.
 */
public class AppRestClient {
    private final String TAG = getClass().getName();
    private InputStream reponseStream;
    private ServiceRequest request;
    private HttpsURLConnection httpsURLConnection;
    private HttpURLConnection httpURLConnection;

    public AppRestClient(ServiceRequest request) throws MalformedURLException {
        this.request = request;
        if (request.isSecureConnectionRequest()) {
            this.request.setUrl(AppConstants.SECURE_BASE_URL);
        }

    }

    public ResponseContainer execute() {
        ResponseContainer responseContainer = null;
        StringBuilder stringBuilder = new StringBuilder(this.request.getUrl());
        try {
            stringBuilder.append(this.request.getPath());
            stringBuilder.append(this.request.getMethod());
            if (request.getRequestMethod().ordinal() == RequestMethod.GET.ordinal())
                stringBuilder.append(this.request.getRequestString());

            URL url = new URL(stringBuilder.toString());
            AppLogger.i(this, "URL:" + url.toString());
            long timeInit = System.currentTimeMillis();
            /*if (request.isSecureConnectionRequest()) makeSecureHttpConnection(url);
            else makeHttpConnection(url);*/
            CacheRepository cache = (CacheRepository) AppCore.getInstance().getProvider().getAppRepository(Repositories.CACHE);
            responseContainer = cache.getResponseFromMemCache(url.toString());
            if (responseContainer == null) {

                makeHttpConnection(url);

                responseContainer = TransformableFactory.parseJson(getReponseStream(), request.getResponsibleClass());

                AppLogger.i(this, "Response from Server");
                long timeFinal = System.currentTimeMillis();
                AppLogger.i(this, "Time taken to get response " + (timeFinal - timeInit));
                responseContainer.setRequestCode(request.getRequestCode());
                responseContainer.setCurrentRequest(request.getCurrentRequest());

                cache.addResponseToMemoryCache(url.toString(), responseContainer);

            } else {
                AppLogger.i(this, "Response from CACHE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContainer;
    }


    public void makeHttpConnection(URL url) {
        try {
            this.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(request.getConnectionTImeout());
            httpURLConnection.setRequestMethod(request.getRequestMethod().toString());
            httpURLConnection.setReadTimeout(request.getConnectionTImeout());
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            if (request.getRequestMethod().ordinal() == RequestMethod.POST.ordinal()) {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os));
                writer.write(request.getRequestString());
                writer.flush();
                writer.close();
                os.close();
                Log.d("===== POST Params", request.getRequestString());
            }
            httpURLConnection.connect();
            setInputStream(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInputStream(HttpURLConnection httpURLConnection) throws IOException {
        InputStream is = httpURLConnection.getInputStream();
        String contentEncoding = httpURLConnection.getContentEncoding();
        if (contentEncoding != null && ("gzip").equalsIgnoreCase(contentEncoding)) {
            reponseStream = new GZIPInputStream(is);
        } else {
            reponseStream = is;
        }
    }

    public void abort() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                if (httpsURLConnection != null)
                    httpsURLConnection.disconnect();

            }
        });
        thread.start();
    }

    public InputStream getReponseStream() {
        return reponseStream;
    }
}
