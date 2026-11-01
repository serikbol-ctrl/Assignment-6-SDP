package com.assignment6.social;

import java.util.*;
public record Group(String id, List<Node> children) implements Node {
 public Group {children=List.copyOf(children);}
 public <R> R accept(Visitor<R> visitor) {return visitor.visitGroup(this);}
}
