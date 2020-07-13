package com.yude.brainstormer.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.yude.brainstormer.callback.api.ApiCallbackGet;
import com.yude.brainstormer.model.Brain;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class GetTaskJson<T> extends AsyncTask<String, Void, ResponseEntity<T>> {

    private final Class<T> tClass;
    private ApiCallbackGet<T> apiCallback;

    public GetTaskJson(Class<T> tClass, ApiCallbackGet<T> apiCallback) {
        this.tClass = tClass;
        this.apiCallback = apiCallback;
    }

    @Override
    protected ResponseEntity<T> doInBackground(String... uri) {
        final String url = uri[0];

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        httpHeaders.setAuthorization(new JwtHandler());

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, tClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<T> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            Log.e("GetTaskJson", "Network Error: " + responseEntity.getStatusCode().toString());
        } else {
            apiCallback.getResult(responseEntity);
        }
    }
}
