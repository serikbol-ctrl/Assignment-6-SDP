package com.assignment6.social;

import java.util.concurrent.atomic.AtomicInteger;
public final class NotificationObserver implements Observer<SocialEvent> {
 private final AtomicInteger notifications=new AtomicInteger();
 public void onEvent(SocialEvent e) {notifications.incrementAndGet();}
 public int count() {return notifications.get();}
}
