# Assignment 6 — Social Network 

Java 17, Observer + Visitor + Composite, JUnit 5. No event-bus libraries or `java.util.Observable`.

## Run

Open the folder as a Maven project in IntelliJ IDEA. Run `com.assignment6.social.Main`, or use `mvn test` and then `mvn exec:java -Dexec.mainClass=com.assignment6.social.Main` (exec plugin may need downloading). Alternatively compile main sources with `javac -d out $(find src/main/java -name '*.java')` and run `java -cp out com.assignment6.social.Main` on macOS/Linux.

## Observer design

Two producers: `PostService`, `ReactionService`. `EventHub` is a generic subject using `ConcurrentHashMap` + `CopyOnWriteArrayList` for per-event subscriptions. Five main listeners: `FeedObserver`, `NotificationObserver`, `StatisticsObserver`, `TrendingObserver`, `ModerationObserver`. `TrendingObserver` is also a subject and republishes `TREND_UPDATED` events. `SocialEvent` is an immutable record. **Push** was selected to avoid listeners repeatedly polling for updates. A subscription changed inside a callback affects later notifications; an in-flight publication uses the iteration snapshot.

## Visitor design

`Community → Group → Post / Comment / Image / Video` is a three-level Composite tree with two container types and four leaf types. Elements contain only data and `accept`. `Visitor<R>` uses double dispatch: `node.accept(visitor)` invokes `visitor.visitPost(this)` for a `Post`. **Visitor-owned traversal** was chosen: each visitor defines how child results are combined via `TreeVisitor`. Four visitors: `EngagementVisitor` (numeric total), `StatisticsVisitor` (accumulated counts), `ModerationVisitor` (filtered IDs), `JsonExportVisitor` (nested JSON).

## Integration

A `POST_CREATED` event triggers `ModerationObserver`, which looks up **only the matching group** and applies `ModerationVisitor`. The result is published as `MODERATION_REPORT` to a separate report hub. The demo moderation rule flags IDs starting with `review-` and is not a real content classifier.

## Concurrency

`ConcurrentDemo` uses configurable fixed-size `ExecutorService`, 3+ producers, futures to detect producer failures, `shutdown()` and `awaitTermination()`. Shared counts use `AtomicInteger` or `LongAdder`; registry uses thread-safe collections. Events are delivered synchronously on producer threads, so successful producer completion means handlers completed. A failing listener propagates an exception to the producer future rather than silently dropping it. Do not modify tree nodes during processing; records and immutable child lists make the sample tree safe for concurrent reads.

## Tests

19 JUnit 5 tests including the exact 4 × 1,000 event stress test, hand-calculated engagement 10 + 2 + 20 + 30 = 62, filtering, JSON nesting, runtime subscribe/unsubscribe, chain, integration, and immutability.

## Clean Code

`Main` is the composition root and wires all services via constructor injection. No lazy initialization in business logic. `TreeVisitor` consolidates traversal; concrete visitors keep a single responsibility.

## Trade-off proof — REQUIRED MANUAL GIT COMMITS

This ZIP cannot establish your actual two-week Git history. Make **two separate commits** in your own Git repository:

1. **Add a new visitor** (e.g., `ContentCountVisitor.java`) implementing all six visit methods, and add its tests. Files changed: new visitor and test only; **no existing element files**. Cost: one method per existing element type.
2. **Add a new element** (e.g., `Poll.java`) and its `accept` method, then update `Visitor.java`, `EngagementVisitor.java`, `StatisticsVisitor.java`, `ModerationVisitor.java`, `JsonExportVisitor.java`, and the new visitor plus tests. Cost: every visitor interface implementation must change; adding element types is expensive.

Commit them separately with meaningful messages and describe your actual changed files in this README before submission. Do not claim fabricated commit history.

## UML

See `uml.puml` (PlantUML text). Render with a PlantUML plugin or compatible online renderer.
