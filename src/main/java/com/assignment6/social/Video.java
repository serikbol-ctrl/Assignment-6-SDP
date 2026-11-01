package com.assignment6.social;

public record Video(String id, int views) implements Node {
 public <R> R accept(Visitor<R> visitor) {return visitor.visitVideo(this);}
}
