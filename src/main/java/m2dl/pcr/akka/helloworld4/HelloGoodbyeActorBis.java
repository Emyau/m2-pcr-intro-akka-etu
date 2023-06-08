package m2dl.pcr.akka.helloworld4;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import m2dl.pcr.akka.helloworld2.NameActor;

public class HelloGoodbyeActorBis extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef helloActorRef;
    private ActorRef goodbyeActorRef;

    public HelloGoodbyeActorBis() {
        log.info("HelloGoodbyeActorBis constructor");
        helloActorRef = getContext().actorOf(Props.create(HelloActorBis.class), "hello-actor-bis");
        goodbyeActorRef = getContext().actorOf(Props.create(GoodbyeActorBis.class), "goodbye-actor-bis");
    }

    Procedure<Object> hello = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                //log.info("Hello " + msg + "!");
                helloActorRef.tell(msg,getSelf());
                getContext().become(goodbye,false);
            } else {
                unhandled(msg);
            }
        }
    };

    Procedure<Object> goodbye = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                //log.info("Good bye " + msg + "!");
                goodbyeActorRef.tell(msg,getSelf());
                getContext().unbecome();
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        hello.apply(msg);
    }

}
