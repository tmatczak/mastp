package agents;

import com.lynden.gmapsfx.javascript.object.LatLong;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import javafx.application.Platform;
import sample.AgentsEnvironmentManager;
import sample.Controller;
import sample.MapManager;
import utils.GUIEvent;
import utils.SimpleMessage;

public class GUIAgent extends GuiAgent {

    transient protected Controller controller;
    private AgentsEnvironmentManager aem;
    private MapManager mm;

    protected void setup() {
        parseArguments();
        setupBehaviours();
    }

    private void parseArguments() {
        Object[] args = getArguments();
        if (args != null) {
            aem = (AgentsEnvironmentManager) args[0];
            mm = (MapManager) args[1];
            controller = (Controller) args[2];
            controller.setupGuiAgent(this);
        }
    }

    private void setupBehaviours() {
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    try {
                        SimpleMessage sm = (SimpleMessage) msg.getContentObject();
                        switch (sm.getEvent()) {
                            case GUIEvent.ADD_AGENT: {
                                addAIDToList(sm.getAid());
                                break;
                            }
                            case GUIEvent.DELETE_AGENT: {
                                removeAIDFromList("");
                                break;
                            }

                        }
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }

                }
                block();
            }
        });
    }

    private void addAIDToList(String localName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.addAIDToList(localName);
            }
        });
    }

    private void removeAIDFromList(String localName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.removeSelectedAIDFromList();
            }
        });
    }

    public void sendKillMessage(String localName) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent(this.getAgent().getAID().getLocalName());
                msg.addReceiver(new AID(localName, AID.ISLOCALNAME));
                send(msg);
            }
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
