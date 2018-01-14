package agents;

import jade.core.behaviours.SimpleBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import javafx.application.Platform;
import sample.AgentsEnvironmentManager;
import sample.Controller;
import sample.MapManager;

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

    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
