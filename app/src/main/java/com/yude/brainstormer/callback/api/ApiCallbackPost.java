package com.yude.brainstormer.callback.api;

import org.springframework.http.ResponseEntity;

public interface ApiCallbackPost<T> {

    void postResult(ResponseEntity<T> responseEntity);
}
