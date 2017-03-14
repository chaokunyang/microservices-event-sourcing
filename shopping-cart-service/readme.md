1、Flux

A Reactive Streams Publisher with basic flow operators.

Static factories on Flux allow for source generation from arbitrary callbacks types.
Instance methods allows operational building, materialized on each Flux#subscribe(), Flux#subscribe() or multicasting operations such as Flux#publish and Flux#publishNext.

[<img src="https://raw.githubusercontent.com/reactor/reactor-core/v3.0.5.RELEASE/src/docs/marble/flux.png" width="500">](http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html)

Flux in action :
```java
Flux.fromIterable(getSomeLongList())
    .mergeWith(Flux.interval(100))
    .doOnNext(serviceA::someObserver)
    .map(d -> d * 2)
    .take(3)
    .onErrorResumeWith(errorHandler::fallback)
    .doAfterTerminate(serviceM::incrementTerminate)
    .subscribe(System.out::println);
```
2、Mono

A Reactive Streams Publisher constrained to ZERO or ONE element with appropriate operators.

Static factories on Mono allow for deterministic zero or one sequence generation from arbitrary callbacks types.
Instance methods allows operational building, materialized on each Mono#subscribe() or Mono#get() eventually called.

[<img src="https://raw.githubusercontent.com/reactor/reactor-core/v3.0.5.RELEASE/src/docs/marble/mono.png" width="500">](http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html)

Mono in action :
```java
Mono.fromCallable(System::currentTimeMillis)
    .then(time -> Mono.first(serviceA.findRecent(time), serviceB.findRecent(time)))
    .timeout(Duration.ofSeconds(3), errorHandler::fallback)
    .doOnSuccess(r -> serviceM.incrementSuccess())
    .subscribe(System.out::println);
```

3、Reactive programming is oriented around data flows and the propagation of change. This means that the underlying execution model will automatically propagate changes through the data flow.



