package com.yude.brainstormer.rest;

import android.os.AsyncTask;

import com.yude.brainstormer.ApiCallbackPost;
import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.callback.api.ApiJsonForm;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class PostTaskJson<T extends ApiJsonForm> extends AsyncTask<T, Void, ResponseEntity<T>> {

    private final Class<T> tClass;
    private ApiCallback apiCallback;

    public PostTaskJson(Class<T> tClass, ApiCallback apiCallback) {
        this.tClass = tClass;
        this.apiCallback = apiCallback;
    }

    @Override
    protected ResponseEntity<T> doInBackground(T... ts) {
        final String url = ts[0].getURL();
        JSONObject jsonObject = ts[0].getJson();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(url, HttpMethod.POST, httpEntity, tClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<T> responseEntity) {
        apiCallback.postResult(responseEntity);
    }
}
