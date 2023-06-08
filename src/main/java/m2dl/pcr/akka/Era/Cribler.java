package m2dl.pcr.akka.Era;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class Cribler extends UntypedActor {

    private int premier;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef actorRef;
    final ActorSystem actorSystem = ActorSystem.create("actor-system");

    public Cribler(int premier) {
        log.info("Cribler constructor, eating " + premier);
        this.premier = premier;
        actorRef = getContext().actorOf(Props.create(CalculActor.class), "calcul-actor");
    }

    Procedure<Object> feuille = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Integer) {
                if ((Integer) msg % premier == 0) {
                    final ActorRef actorNew = actorSystem.actorOf(Props.create(Cribler.class, msg), "cribler");
                    getContext().become(chaine,false);
                }
            } else {
                unhandled(msg);
            }
        }
    };

    Procedure<Object> chaine = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Integer) {
                //log.info("Good bye " + msg + "!");
                //goodbyeActorRef.tell(msg,getSelf());
            } else {
                unhandled(msg);
            }
        }
    };


    @Override
    public void onReceive(Object o) throws Exception {

    }
}
