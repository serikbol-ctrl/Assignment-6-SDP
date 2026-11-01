package com.assignment6.social;

public final class ReactionService {
 private final Subject<SocialEvent> events;
 public ReactionService(Subject<SocialEvent> events) {this.events=events;}
 public void react(String group,String post) {events.publish(new SocialEvent(EventType.REACTION_ADDED,group,post,1,"Reaction"));}
}
