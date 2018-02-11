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
import utils.Coordinate;
import utils.CustomGuiEvent;
import utils.DefaultAgentName;
import utils.SimpleMessage;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class DestinationAgent extends Agent {

    static private String RED = "red";
    static private String GREEN = "green";

    private MapManager mm;

    private Coordinate current;
    private Coordinate last;
    private Coordinate start;
    private Coordinate finish;
    private double step = 0.01;
    private String currentColor = GREEN;

    protected void setup() {
        parseArguments();
        start = generateRandomCoordinates();
        finish = generateRandomCoordinates();
        current = start;
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
                System.out.println();
                System.out.println("Current: " + current.latitude + ", " + current.longitude);
                if (isDestinationReached()) {
                    System.out.println("YEAH!!!");
                    switchDestinations();
                    currentColor = currentColor.equals(RED) ? GREEN : RED;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        LatLong currentCoordinates = new LatLong(current.latitude, current.longitude);
                        LatLong lastCoordinates = new LatLong(last.latitude, last.longitude);
//                        mm.drawDot(currentCoordinates);
                        mm.drawLine(lastCoordinates, currentCoordinates, currentColor);
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

    private boolean isDestinationReached() {
        System.out.println("Destination is at " + finish.latitude + ", " + finish.longitude);
        return distance(finish, current) < step;
    }



    private void switchDestinations() {
        Coordinate temp = start;
        start = finish;
        finish = temp;
    }

    private void updateCoordinates() {
        last = current;
        double stepsCount = distance(finish, start) / step;
        double latitudeStep = (finish.latitude - start.latitude) / stepsCount;
        double longitudeStep = (finish.longitude - start.longitude) / stepsCount;
        current = new Coordinate(current.latitude + latitudeStep, current.longitude + longitudeStep);
    }

    private Coordinate generateRandomCoordinates() {
        int latitudeReminder = getRandomInt(0, 9999);
        int longitudeReminder = getRandomInt(0, 9999);
        return new Coordinate(52 + (double)latitudeReminder/10000, 21 + (double)longitudeReminder/10000);
    }

    private int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private double distance(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point1.latitude - point2.latitude, 2) + Math.pow(point1.longitude - point2.longitude, 2));
    }
}

