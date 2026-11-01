package com.assignment6.social;

import java.util.concurrent.atomic.LongAdder;
public final class StatisticsObserver implements Observer<SocialEvent> {
 private final LongAdder total=new LongAdder();
 public void onEvent(SocialEvent e) {total.increment();}
 public long count() {return total.sum();}
}
