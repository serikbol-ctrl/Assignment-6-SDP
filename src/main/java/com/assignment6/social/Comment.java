package com.assignment6.social;

public record Comment(String id, int likes) implements Node {
 public <R> R accept(Visitor<R> visitor) {return visitor.visitComment(this);}
}
