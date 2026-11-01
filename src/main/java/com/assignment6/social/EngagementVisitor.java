package com.assignment6.social;

import java.util.*;
public final class EngagementVisitor extends TreeVisitor<Integer> {
 protected Integer combine(List<Integer> values) {return values.stream().mapToInt(Integer::intValue).sum();}
 public Integer visitPost(Post n) {return n.likes();}
 public Integer visitComment(Comment n) {return n.likes();}
 public Integer visitImage(Image n) {return n.views();}
 public Integer visitVideo(Video n) {return n.views();}
}
