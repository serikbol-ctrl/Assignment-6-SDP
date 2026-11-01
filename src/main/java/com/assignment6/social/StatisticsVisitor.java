package com.assignment6.social;

import java.util.*;
public final class StatisticsVisitor extends TreeVisitor<Map<String,Integer>> {
 protected Map<String,Integer> combine(List<Map<String,Integer>> values) {
  Map<String,Integer> result=new TreeMap<>();
  for(var value:values) value.forEach((key,count)->result.merge(key,count,Integer::sum));
  return Map.copyOf(result);
 }
 private Map<String,Integer> single(String type) {return Map.of(type,1);}
 public Map<String,Integer> visitPost(Post n) {return single("posts");}
 public Map<String,Integer> visitComment(Comment n) {return single("comments");}
 public Map<String,Integer> visitImage(Image n) {return single("images");}
 public Map<String,Integer> visitVideo(Video n) {return single("videos");}
}
