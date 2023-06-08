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
    private int max;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //final ActorSystem actorSystem = ActorSystem.create("actor-system");

    ActorRef actorNew;

    public Cribler(int premier, int N) {
        this.premier = premier;
        this.max = N;
        log.info("Cribler constructor, eating premier " + premier);
    }

    Procedure<Object> feuille = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Integer) {
                if ((Integer) msg % premier != 0) {
                    actorNew = getContext().actorOf(Props.create(Cribler.class, msg, max), "cribler");
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
                if ((Integer) msg % premier != 0) {
                    actorNew.tell(msg, null);
                }
                /*if ((Integer) msg == max) {
                    //actorSystem.terminate();

                }*/
            } else {
                actorNew.tell(msg, null);
                getContext().stop(self());
            }
        }
    };


    @Override
    public void onReceive(Object msg) throws Exception {
        feuille.apply(msg);
    }
}
