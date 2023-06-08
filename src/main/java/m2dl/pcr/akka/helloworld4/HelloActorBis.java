package m2dl.pcr.akka.helloworld4;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HelloActorBis extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public HelloActorBis() {
        log.info("HelloActorBis constructor");
    }

    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof String) {
            log.info("Hello" + "!");
        } else {
            unhandled(msg);
        }
    }
}
