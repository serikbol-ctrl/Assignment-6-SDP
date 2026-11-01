package com.assignment6.social;

import java.util.concurrent.atomic.AtomicInteger;
public final class FeedObserver implements Observer<SocialEvent> {
 private final AtomicInteger updates=new AtomicInteger();
 public void onEvent(SocialEvent e) {updates.incrementAndGet();}
 public int count() {return updates.get();}
}
