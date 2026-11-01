package com.assignment6.social;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
public final class ConcurrentDemo {
 private final PostService posts;
 public ConcurrentDemo(PostService posts) {this.posts=posts;}
 public int run(int threadCount,int producers,int eventsPerProducer) throws InterruptedException {
  if(threadCount<1||producers<3||eventsPerProducer<0) throw new IllegalArgumentException();
  ExecutorService pool=Executors.newFixedThreadPool(threadCount);
  AtomicInteger produced=new AtomicInteger();
  try {
   List<Future<?>> futures=new ArrayList<>();
   for(int i=0;i<producers;i++) {
    final int producer=i;
    futures.add(pool.submit(()->{for(int j=0;j<eventsPerProducer;j++) {posts.create("g1","p"+producer+"-"+j);produced.incrementAndGet();}}));
   }
   for(var future:futures) {
    try {future.get();} catch(ExecutionException e) {throw new IllegalStateException("Producer failed",e.getCause());}
   }
  } finally {
   pool.shutdown();
   if(!pool.awaitTermination(30,TimeUnit.SECONDS)) {pool.shutdownNow();throw new IllegalStateException("Executor did not terminate");}
  }
  return produced.get();
 }
}
