package com.assignment6.social;

import java.util.Objects;
public record SocialEvent(EventType type, String groupId, String itemId, int value, String message) {
 public SocialEvent { Objects.requireNonNull(type); Objects.requireNonNull(groupId); Objects.requireNonNull(itemId); Objects.requireNonNull(message); }
}
