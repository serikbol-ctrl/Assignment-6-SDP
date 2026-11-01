package com.assignment6.social;

public interface Visitor<R> {
 R visitCommunity(Community node); R visitGroup(Group node);
 R visitPost(Post node); R visitComment(Comment node); R visitImage(Image node); R visitVideo(Video node);
}
