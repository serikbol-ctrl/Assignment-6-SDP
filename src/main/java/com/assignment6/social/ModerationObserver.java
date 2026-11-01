package com.assignment6.social;

import java.util.*;
import java.util.concurrent.*;
public final class ModerationObserver implements Observer<SocialEvent> {
 private final Map<String,Group> groups;
 private final Subject<SocialEvent> reports;
 public ModerationObserver(Map<String,Group> groups,Subject<SocialEvent> reports) {this.groups=Map.copyOf(groups);this.reports=reports;}
 public void onEvent(SocialEvent event) {
  Group group=groups.get(event.groupId());
  if(group==null) return;
  List<String> flagged=group.accept(new ModerationVisitor());
  reports.publish(new SocialEvent(EventType.MODERATION_REPORT,event.groupId(),event.itemId(),flagged.size(),String.join(",",flagged)));
 }
}
