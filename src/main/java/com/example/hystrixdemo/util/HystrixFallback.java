package com.example.hystrixdemo.util;

public interface HystrixFallback<T> {
    T fallback(Throwable throwable);
}
