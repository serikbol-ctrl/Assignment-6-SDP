package com.assignment6.social;

import java.util.concurrent.*;
import java.util.*;
public final class EventHub implements Subject<SocialEvent> {
 private final ConcurrentMap<EventType, CopyOnWriteArrayList<Observer<SocialEvent>>> listeners = new ConcurrentHashMap<>();
 public void subscribe(EventType type, Observer<SocialEvent> listener) { listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).addIfAbsent(listener); }
 public void unsubscribe(EventType type, Observer<SocialEvent> listener) { var list=listeners.get(type); if(list!=null) list.remove(listener); }
 // Snapshot semantics: a change during a callback affects subsequent publications.
 public void publish(SocialEvent event) { for(var listener:listeners.getOrDefault(event.type(),new CopyOnWriteArrayList<>())) listener.onEvent(event); }
}
