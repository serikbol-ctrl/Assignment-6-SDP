package com.assignment6.social;

public interface Node { <R> R accept(Visitor<R> visitor); String id(); }
