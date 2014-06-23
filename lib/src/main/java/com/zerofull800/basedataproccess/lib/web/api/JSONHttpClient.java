package com.zerofull800.basedataproccess.lib.web.api;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.zerofull800.basedataproccess.lib.web.exceptions.DownloadException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by NestorLuis on 1/4/14.
 */
public abstract class JSONHttpClient<T> {
    private final String TAG = getClass().getName();

    @SuppressWarnings("hiding")
    protected <T> T PostObject(final String url, final T object, final Class<T> objectClass) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {

            StringEntity stringEntity = new StringEntity(new GsonBuilder().create().toJson(object));
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip");

            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    inputStream = new GZIPInputStream(inputStream);
                }

                String resultString = convertStreamToString(inputStream);
                inputStream.close();
                return new GsonBuilder().create().fromJson(resultString, objectClass);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @SuppressWarnings("hiding")
    protected <T> T PostParams(String url, final List<NameValuePair> params, final Class<T> objectClass) {
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        return PostObject(url, null, objectClass);
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return stringBuilder.toString();
    }

    @SuppressWarnings("hiding")
    protected <T> T Get(String url, List<NameValuePair> params, final Type objectClass) throws DownloadException {

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        if (params != null) {
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url += "?" + paramString;
        }

        HttpGet httpGet = new HttpGet(url);
        try {

            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Accept-Encoding", "gzip");

            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    inputStream = new GZIPInputStream(inputStream);
                }

//                android.util.JsonReader reader = new android.util.JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                String resultString = convertStreamToString(inputStream);

                if (resultString.isEmpty() || resultString.equalsIgnoreCase("[]")) {
                    return null;
                }
                inputStream.close();
                return new GsonBuilder().create().fromJson(resultString, objectClass);

            }

        } catch (UnsupportedEncodingException e) {
            throw new DownloadException("Error descargando la informacion: " + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new DownloadException("Error descargando la informacion: " + e.getMessage());
        } catch (IOException e) {
            throw new DownloadException("Error descargando la informacion: " + e.getMessage());
        }
        return null;
    }

    protected boolean Delete(String url, final List<NameValuePair> params) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        HttpDelete httpDelete = new HttpDelete(url);

        HttpResponse httpResponse = null;
        try {
            httpResponse = defaultHttpClient.execute(httpDelete);
            return httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return false;
    }
}
