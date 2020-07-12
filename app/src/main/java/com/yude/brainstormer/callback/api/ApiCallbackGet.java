package com.yude.brainstormer.callback.api;

import org.springframework.http.ResponseEntity;

public interface ApiCallbackGet<T> {

    void getResult(ResponseEntity<T> responseEntity);

}
