package com.assignment6.social;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
// Observer + Subject: aggregates reaction events and republishes trend updates.
public final class TrendingObserver implements Observer<SocialEvent>, Subject<SocialEvent> {
 private final Subject<SocialEvent> downstream=new EventHub();
 private final ConcurrentMap<String,AtomicInteger> counts=new ConcurrentHashMap<>();
 public void onEvent(SocialEvent e) {
  int count=counts.computeIfAbsent(e.groupId()+":"+e.itemId(),k->new AtomicInteger()).addAndGet(e.value());
  downstream.publish(new SocialEvent(EventType.TREND_UPDATED,e.groupId(),e.itemId(),count,"Trend updated"));
 }
 public void subscribe(EventType type,Observer<SocialEvent> o) {downstream.subscribe(type,o);}
 public void unsubscribe(EventType type,Observer<SocialEvent> o) {downstream.unsubscribe(type,o);}
 public void publish(SocialEvent e) {downstream.publish(e);}
 public int score(String group,String item) {var a=counts.get(group+":"+item);return a==null?0:a.get();}
}
