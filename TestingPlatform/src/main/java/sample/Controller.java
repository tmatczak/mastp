package sample;

import agents.GUIAgent;
import jade.gui.GuiAgent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AgentsEnvironmentManager aem;
    private MapManager mm;
    private GuiAgent ga;

    @FXML private SplitPane rootContainer;
    @FXML private HBox mapContainer;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private ListView listView;
    @FXML private ScrollPane scrollView;
    @FXML private AnchorPane scrollViewContainer;

    public Controller(AgentsEnvironmentManager aem, MapManager mm) {
        this.aem = aem;
        this.mm = mm;
    }

    public void setupGuiAgent(GUIAgent ga) {
        this.ga = ga;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createMovingAgent() {
        Date now = new Date();
        Object[] arguments = new Object[1];
        arguments[0] = mm;
        aem.addAgentToMainContainer(now.toString(), "agents.MovingAgent", arguments);
    }

    public void removeAgent() {
        System.out.println("remove agent");
    }
}
