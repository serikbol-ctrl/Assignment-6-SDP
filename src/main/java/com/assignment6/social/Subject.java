package com.assignment6.social;

public interface Subject<T> {
 void subscribe(EventType type, Observer<T> listener);
 void unsubscribe(EventType type, Observer<T> listener);
 void publish(T event);
}
