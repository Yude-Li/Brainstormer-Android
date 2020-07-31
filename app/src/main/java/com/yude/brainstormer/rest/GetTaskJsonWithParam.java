package com.yude.brainstormer.rest;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.yude.brainstormer.callback.api.ApiCallback;
import com.yude.brainstormer.callback.api.ApiCallbackGet;
import com.yude.brainstormer.callback.api.ApiJsonForm;
import com.yude.brainstormer.dao.DaoFactory;
import com.yude.brainstormer.model.form.CurrentBrainIdeasForm;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class GetTaskJsonWithParam<T extends ApiJsonForm, E> extends AsyncTask<T, Void, ResponseEntity<E>> {

    private final Class<E> eClass;
    private ApiCallbackGet<E> apiCallback;

    public GetTaskJsonWithParam(Class<E> eClass, ApiCallbackGet<E> apiCallback) {
        this.eClass = eClass;
        this.apiCallback = apiCallback;
    }

    @Override
    protected ResponseEntity<E> doInBackground(T... ts) {
        final String url = ts[0].getURL();
        JSONObject jsonObject = ts[0].getJson();
        String username = DaoFactory.getDataDao().getCurrentBrain().getUsername();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        uriBuilder.appendQueryParameter("username", username);
        String myurl = uriBuilder.build().toString();

        try {
            RestTemplate restTemplate = new RestTemplate(true);
            return restTemplate.exchange(myurl, HttpMethod.GET, httpEntity, eClass);
        } catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<E> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            Log.e("GetTaskJsonWithParam", "Network Error: " + responseEntity.getStatusCode().toString());
        } else {
            apiCallback.getResult(responseEntity);
        }
    }
}
