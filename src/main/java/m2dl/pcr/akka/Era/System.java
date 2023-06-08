package m2dl.pcr.akka.Era;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import m2dl.pcr.akka.helloworld4.HelloGoodbyeActorBis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class System {

    public static final Logger log = LoggerFactory.getLogger(m2dl.pcr.akka.helloworld4.System.class);

    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);

        final ActorRef actorRefDeux = actorSystem.actorOf(Props.create(Cribler.class, 2), "cribler");

        int N = 50;

        for (int i = 3; i < N; i++) {
            actorRefDeux.tell(i, null);
        }

        Thread.sleep(1000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }
}
