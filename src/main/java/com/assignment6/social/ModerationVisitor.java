package com.assignment6.social;

import java.util.*;
// Demonstration rule: IDs prefixed with 'review-' are flagged.
public final class ModerationVisitor extends TreeVisitor<List<String>> {
 protected List<String> combine(List<List<String>> results) {return results.stream().flatMap(List::stream).toList();}
 private List<String> flagged(String id) {return id.startsWith("review-")?List.of(id):List.of();}
 public List<String> visitPost(Post n) {return flagged(n.id());}
 public List<String> visitComment(Comment n) {return flagged(n.id());}
 public List<String> visitImage(Image n) {return flagged(n.id());}
 public List<String> visitVideo(Video n) {return flagged(n.id());}
}
