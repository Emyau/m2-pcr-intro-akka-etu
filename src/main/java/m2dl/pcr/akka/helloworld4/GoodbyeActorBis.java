package m2dl.pcr.akka.helloworld4;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class GoodbyeActorBis extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public GoodbyeActorBis() {
        log.info("GoodbyeActorBis constructor");
    }

    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof String) {
            log.info("Goodbye" + "!");
        } else {
            unhandled(msg);
        }
    }
}
