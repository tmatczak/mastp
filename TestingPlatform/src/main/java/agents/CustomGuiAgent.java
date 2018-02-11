package agents;

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
import utils.CustomGuiEvent;
import utils.SimpleMessage;

import java.util.Date;

public class CustomGuiAgent extends GuiAgent {

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
                        parseMessage(sm);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }
                block();
            }
        });
    }

    private void parseMessage(SimpleMessage sm) {
        switch (sm.getEvent()) {
            case CustomGuiEvent.ADD_AGENT: {
                addAIDToList(sm.getAid());
                break;
            }
            case CustomGuiEvent.DELETE_AGENT: {
                removeAIDFromList();
                break;
            }
        }
    }

    private void addAIDToList(String localName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.addAIDToList(localName);
            }
        });
    }

    private void removeAIDFromList() {
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

    private void createMulitDestinationAgent() {
        Date now = new Date();
        Object[] arguments = { mm };
        aem.addAgentToMainContainer(now.toString(), MultiDestinationAgent.class.getName(), arguments);
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        switch (guiEvent.getType()) {
            case CustomGuiEvent.ADD_AGENT: {
                createMulitDestinationAgent();
                break;
            }
            case CustomGuiEvent.DELETE_AGENT: {
                String localName = (String) guiEvent.getParameter(0);
                sendKillMessage(localName);
            }
        }

    }
}
