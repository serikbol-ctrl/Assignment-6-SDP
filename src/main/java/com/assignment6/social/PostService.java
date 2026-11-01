package com.assignment6.social;

public final class PostService {
 private final Subject<SocialEvent> events;
 public PostService(Subject<SocialEvent> events) {this.events=events;}
 public void create(String group, String post) {events.publish(new SocialEvent(EventType.POST_CREATED,group,post,1,"New post"));}
}
