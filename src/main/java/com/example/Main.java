package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Main extends AbstractBehavior<String> {
    public Main(ActorContext<String> context) {
        super(context);
    }

    static Behavior<String> create() {
        return Behaviors.setup(Main::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("start", this::start)
                .onMessageEquals("stop", this::stop)
                .build();
    }

    private Behavior<String> stop() {
        ActorRef<String> first = getContext().spawn(StartStopActor1.create(), "first");
        first.tell("stop");
        return Behaviors.same();
    }

    private Behavior<String> start() {
        ActorRef<String> firstRef = getContext().spawn(PrintMyActorRefActor.create(), "first-actor");

        System.out.println("First: " + firstRef);
        firstRef.tell("printit");
        return Behaviors.same();
    }
}
