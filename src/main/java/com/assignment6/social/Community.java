package com.assignment6.social;

import java.util.*;
public record Community(String id, List<Node> children) implements Node {
 public Community {children=List.copyOf(children);}
 public <R> R accept(Visitor<R> visitor) {return visitor.visitCommunity(this);}
}
