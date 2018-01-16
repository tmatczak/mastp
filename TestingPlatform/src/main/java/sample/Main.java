package sample;

import agents.CustomGuiAgent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.DefaultAgentName;

public class Main extends Application {

    private AgentsEnvironmentManager aem = new AgentsEnvironmentManager();
    private MapManager mm = new MapManager();
    private Controller controller;

    private HBox mapContainer;
    private Button btnAdd;
    private Button btnRemove;
    private ListView listView;
    private ScrollPane scrollView;
    private AnchorPane scrollViewContainer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Testing platform");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample.fxml"));

        try {
            aem.startContainer();
            aem.addRemoteMonitoringAgent();
        } catch (Exception e) {
            System.out.println("Agents container failure");
            System.exit(0);
        } finally {
            controller = new Controller(aem, mm);
            fxmlLoader.setController(controller);
            startGuiAgent(aem, mm, controller);
            SplitPane root = fxmlLoader.load();
            Scene mainScene = new Scene(root, 800, 600);
            root.applyCss();
            setupViews(mainScene);
            setupListeners();
            primaryStage.setScene(mainScene);
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setupViews(Scene scene) {
        btnAdd = (Button) scene.lookup("#btnAdd");
        btnRemove = (Button) scene.lookup("#btnRemove");
        listView = (ListView) scene.lookup("#listView");
        scrollView = (ScrollPane) scene.lookup("#scrollView");
        scrollViewContainer = (AnchorPane) scene.lookup("#scrollViewContrainer");
        mapContainer = (HBox) scene.lookup("#mapContainer");
        mapContainer.getChildren().add(mm.getMapView());
    }

    private void setupListeners() {
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.createInformingAgent();
            }
        });

        btnRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.removeAgent();
            }
        });
    }

    private void startGuiAgent(AgentsEnvironmentManager aem, MapManager mm, Controller controller) {
        Object[] guiArgs = {aem, mm, controller};
//                //new Object[3];
//        guiArgs[0] = aem;
//        guiArgs[1] = mm;
//        guiArgs[2] = controller;
        aem.addAgentToMainContainer(DefaultAgentName.CUSTOM_GUI_AGENT, CustomGuiAgent.class.getName(), guiArgs);
    }
}

