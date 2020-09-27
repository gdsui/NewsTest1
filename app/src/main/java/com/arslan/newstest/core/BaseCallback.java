package com.arslan.newstest.core;

public interface BaseCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}
