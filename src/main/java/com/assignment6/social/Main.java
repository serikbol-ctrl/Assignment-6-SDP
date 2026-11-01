package com.assignment6.social;

import java.util.*;
public final class Main {
 public static void main(String[] args) throws InterruptedException {
  Group group=new Group("g1",List.of(new Post("p1",10),new Comment("review-c1",3),new Image("i1",20),new Video("v1",30)));
  Community community=new Community("social",List.of(group));
  EventHub hub=new EventHub();
  PostService posts=new PostService(hub);
  ReactionService reactions=new ReactionService(hub);
  FeedObserver feed=new FeedObserver();NotificationObserver notifications=new NotificationObserver();
  StatisticsObserver stats=new StatisticsObserver();TrendingObserver trending=new TrendingObserver();
  EventHub reportHub=new EventHub();StatisticsObserver reports=new StatisticsObserver();
  reportHub.subscribe(EventType.MODERATION_REPORT,reports);
  hub.subscribe(EventType.POST_CREATED,feed);hub.subscribe(EventType.POST_CREATED,notifications);
  hub.subscribe(EventType.POST_CREATED,stats);hub.subscribe(EventType.REACTION_ADDED,trending);
  hub.subscribe(EventType.POST_CREATED,new ModerationObserver(Map.of("g1",group),reportHub));
  trending.subscribe(EventType.TREND_UPDATED,notifications);
  posts.create("g1","p1");reactions.react("g1","p1");
  System.out.println("Engagement: "+community.accept(new EngagementVisitor()));
  System.out.println("Statistics: "+community.accept(new StatisticsVisitor()));
  System.out.println("Flagged: "+community.accept(new ModerationVisitor()));
  System.out.println("JSON: "+community.accept(new JsonExportVisitor()));
  System.out.println("Feed: "+feed.count()+", notifications: "+notifications.count()+", reports: "+reports.count());
  System.out.println("Produced: "+new ConcurrentDemo(posts).run(4,3,100));
 }
}
