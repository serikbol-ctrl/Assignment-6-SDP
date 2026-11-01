package com.assignment6.social;

import java.util.*;
// Visitor owns traversal; each concrete visitor controls aggregation.
public abstract class TreeVisitor<R> implements Visitor<R> {
 protected abstract R combine(List<R> results);
 public R visitCommunity(Community node) {return combine(node.children().stream().map(n -> n.accept(this)).toList());}
 public R visitGroup(Group node) {return combine(node.children().stream().map(n -> n.accept(this)).toList());}
}
