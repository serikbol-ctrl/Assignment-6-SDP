package com.assignment6.social;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
class SocialNetworkTest {
 private Group group() {return new Group("g1",List.of(new Post("p",10),new Comment("review-c",2),new Image("i",20),new Video("v",30)));}
 private Community community() {return new Community("c",List.of(group()));}
 @Test void engagementCalculated() {assertEquals(62,community().accept(new EngagementVisitor()));}
 @Test void groupEngagement() {assertEquals(62,group().accept(new EngagementVisitor()));}
 @Test void emptyGroup() {assertEquals(0,new Group("empty",List.of()).accept(new EngagementVisitor()));}
 @Test void statsCounts() {assertEquals(Map.of("posts",1,"comments",1,"images",1,"videos",1),community().accept(new StatisticsVisitor()));}
 @Test void statsAccumulate() {assertEquals(2,new Community("c",List.of(group(),group())).accept(new StatisticsVisitor()).get("posts"));}
 @Test void moderationFilter() {assertEquals(List.of("review-c"),community().accept(new ModerationVisitor()));}
 @Test void moderationEmpty() {assertTrue(new Post("normal",1).accept(new ModerationVisitor()).isEmpty());}
 @Test void jsonNested() {String json=community().accept(new JsonExportVisitor());assertTrue(json.contains("\"children\":[{\"type\":\"group\""));}
 @Test void jsonEscape() {assertTrue(new Post("a\"b",1).accept(new JsonExportVisitor()).contains("a\\\"b"));}
 @Test void eventSubscribe() {EventHub hub=new EventHub();AtomicInteger count=new AtomicInteger();hub.subscribe(EventType.POST_CREATED,e->count.incrementAndGet());new PostService(hub).create("g","p");assertEquals(1,count.get());}
 @Test void eventTypeFiltering() {EventHub hub=new EventHub();AtomicInteger count=new AtomicInteger();hub.subscribe(EventType.POST_CREATED,e->count.incrementAndGet());new ReactionService(hub).react("g","p");assertEquals(0,count.get());}
 @Test void unsubscribe() {EventHub hub=new EventHub();AtomicInteger count=new AtomicInteger();Observer<SocialEvent> o=e->count.incrementAndGet();hub.subscribe(EventType.POST_CREATED,o);hub.unsubscribe(EventType.POST_CREATED,o);new PostService(hub).create("g","p");assertEquals(0,count.get());}
 @Test void unsubscribeInsideCallback() {EventHub hub=new EventHub();AtomicInteger count=new AtomicInteger();@SuppressWarnings("unchecked") Observer<SocialEvent>[] holder=new Observer[1];holder[0]=e->{count.incrementAndGet();hub.unsubscribe(EventType.POST_CREATED,holder[0]);};hub.subscribe(EventType.POST_CREATED,holder[0]);PostService p=new PostService(hub);p.create("g","p");p.create("g","p");assertEquals(1,count.get());}
 @Test void subscribeInsideCallback() {EventHub hub=new EventHub();AtomicInteger count=new AtomicInteger();Observer<SocialEvent> extra=e->count.incrementAndGet();hub.subscribe(EventType.POST_CREATED,e->hub.subscribe(EventType.POST_CREATED,extra));PostService p=new PostService(hub);p.create("g","p");p.create("g","p");assertEquals(1,count.get());}
 @Test void aggregationChain() {EventHub hub=new EventHub();TrendingObserver t=new TrendingObserver();AtomicInteger updates=new AtomicInteger();t.subscribe(EventType.TREND_UPDATED,e->updates.incrementAndGet());hub.subscribe(EventType.REACTION_ADDED,t);ReactionService r=new ReactionService(hub);r.react("g","p");r.react("g","p");assertEquals(2,updates.get());assertEquals(2,t.score("g","p"));}
 @Test void integrationRelatedGroupOnly() {EventHub hub=new EventHub();EventHub reports=new EventHub();AtomicInteger flags=new AtomicInteger(-1);reports.subscribe(EventType.MODERATION_REPORT,e->flags.set(e.value()));hub.subscribe(EventType.POST_CREATED,new ModerationObserver(Map.of("g1",group(),"g2",new Group("g2",List.of(new Post("clean",0)))),reports));new PostService(hub).create("g2","clean");assertEquals(0,flags.get());new PostService(hub).create("g1","p");assertEquals(1,flags.get());}
 @Test void immutableChildren() {List<Node> children=new ArrayList<>();children.add(new Post("p",1));Group g=new Group("g",children);children.clear();assertEquals(1,g.children().size());assertThrows(UnsupportedOperationException.class,()->g.children().clear());}
 @Test void stressFourThreadsThousandEvents() throws Exception {EventHub hub=new EventHub();StatisticsObserver stats=new StatisticsObserver();hub.subscribe(EventType.POST_CREATED,stats);int count=new ConcurrentDemo(new PostService(hub)).run(4,4,1000);assertEquals(4000,count);assertEquals(4000,stats.count());}
 @Test void noDuplicateSubscription() {EventHub hub=new EventHub();AtomicInteger count=new AtomicInteger();Observer<SocialEvent> o=e->count.incrementAndGet();hub.subscribe(EventType.POST_CREATED,o);hub.subscribe(EventType.POST_CREATED,o);new PostService(hub).create("g","p");assertEquals(1,count.get());}
}
