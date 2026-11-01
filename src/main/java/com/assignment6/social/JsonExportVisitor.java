package com.assignment6.social;

import java.util.*;
public final class JsonExportVisitor extends TreeVisitor<String> {
 protected String combine(List<String> parts) {return "["+String.join(",",parts)+"]";}
 private String quote(String text) {return "\""+text.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r").replace("\t","\\t")+"\"";}
 private String leaf(String type,String id,String metric,int value) {return "{\"type\":"+quote(type)+",\"id\":"+quote(id)+",\""+metric+"\":"+value+"}";}
 public String visitCommunity(Community n) {return "{\"type\":\"community\",\"id\":"+quote(n.id())+",\"children\":"+super.visitCommunity(n)+"}";}
 public String visitGroup(Group n) {return "{\"type\":\"group\",\"id\":"+quote(n.id())+",\"children\":"+super.visitGroup(n)+"}";}
 public String visitPost(Post n) {return leaf("post",n.id(),"likes",n.likes());}
 public String visitComment(Comment n) {return leaf("comment",n.id(),"likes",n.likes());}
 public String visitImage(Image n) {return leaf("image",n.id(),"views",n.views());}
 public String visitVideo(Video n) {return leaf("video",n.id(),"views",n.views());}
}
