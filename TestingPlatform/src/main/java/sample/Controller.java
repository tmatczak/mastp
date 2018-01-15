package sample;

import agents.GUIAgent;
import agents.InformingAgent;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import utils.GUIEvent;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AgentsEnvironmentManager aem;
    private MapManager mm;
    private GUIAgent ga;

    @FXML private SplitPane rootContainer;
    @FXML private HBox mapContainer;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private ListView<String> listView;
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

    public void createInformingAgent() {
        GuiEvent ge = new GuiEvent(this, GUIEvent.ADD_AGENT);
        ga.postGuiEvent(ge);
    }

    public void removeAgent() {
        GuiEvent ge = new GuiEvent(this, GUIEvent.DELETE_AGENT);
        ge.addParameter(listView.getItems().get(listView.getSelectionModel().getSelectedIndex()));
        ga.postGuiEvent(ge);
        btnRemove.setDisable(true);
    }

    public void addAIDToList(String aid) {
        listView.getItems().add(aid);
    }

    public void removeSelectedAIDFromList() {
        listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
        btnRemove.setDisable(false);
    }
}
