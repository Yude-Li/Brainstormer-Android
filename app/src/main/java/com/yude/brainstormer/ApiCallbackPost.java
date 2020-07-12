package com.yude.brainstormer;

import org.springframework.http.ResponseEntity;

public interface ApiCallbackPost<T> {

    void postResult(ResponseEntity<T> responseEntity);
}
