package agents;

import com.lynden.gmapsfx.javascript.object.LatLong;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import sample.MapManager;
import utils.CustomGuiEvent;
import utils.DefaultAgentName;
import utils.SimpleMessage;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class InformingAgent extends Agent {

    private MapManager mm;
    private double latitude;
    private double longitude;
    private double step = 0.001;

    protected void setup() {
        parseArguments();
        setRandomCoordinates();
        setupBehaviours();
    }

    private void parseArguments() {
        Object[] args = getArguments();
        if (args != null) {
            mm = (MapManager) args[0];
//            latitude = (double) args[1];
//            longitude = (double) args[2];
        }
    }

    private void setupBehaviours() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    SimpleMessage sm = new SimpleMessage(this.getAgent().getAID().getLocalName(), CustomGuiEvent.ADD_AGENT);
                    msg.setContentObject(sm);
                    msg.addReceiver(new AID(DefaultAgentName.CUSTOM_GUI_AGENT, AID.ISLOCALNAME));
                    send(msg);
                } catch (IOException e) {
                    System.out.println("Exception in InformingAgent");
                }
            }
        });


        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
                updateCoordinates();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mm.setMarker(getLocalName(), new LatLong(latitude, longitude));
                    }
                });
            }
        });
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    addBehaviour(new OneShotBehaviour() {
                        @Override
                        public void action() {
                            try {
                                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                                SimpleMessage sm = new SimpleMessage(this.getAgent().getAID().getLocalName(), CustomGuiEvent.DELETE_AGENT);
                                msg.setContentObject(sm);
                                msg.addReceiver(new AID(DefaultAgentName.CUSTOM_GUI_AGENT, AID.ISLOCALNAME));
                                send(msg);
                                doDelete();
                            } catch (IOException e) {
                                System.out.println("Exception in InformingAgent");
                            }
                        }
                    });
                }
                block();
            }
        });

    }

    private void updateCoordinates() {
        this.latitude += getRandomInt(-1, 1) * step;
        this.longitude += getRandomInt(-1, 1) * step;
    }

    private void setRandomCoordinates() {
        int latitudeReminder = getRandomInt(0, 9999);
        int longitudeReminder = getRandomInt(0, 9999);
        this.latitude = 52 + (double)latitudeReminder/10000;
        this.longitude = 21 + (double)longitudeReminder/10000;
    }

    private int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
