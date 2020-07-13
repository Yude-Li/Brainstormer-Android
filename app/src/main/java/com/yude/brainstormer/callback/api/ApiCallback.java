package com.yude.brainstormer.callback.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ApiCallback<T> {
//    void getResult(HttpStatus httpStatus, Object data);
//    void postResult(HttpStatus httpStatus, String result);
    void postResult(ResponseEntity<T> responseEntity);
}
