package com.assignment6.social;

public record Post(String id, int likes) implements Node {
 public <R> R accept(Visitor<R> visitor) {return visitor.visitPost(this);}
}
