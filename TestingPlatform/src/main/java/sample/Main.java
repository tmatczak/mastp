package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {

    private AgentsEnvironmentManager aem = new AgentsEnvironmentManager();
    private MapManager mm = new MapManager();

    private HBox mapContainer;
    private Button btnAdd;
    private Button btnRemove;
    private ListView listView;
    private ScrollPane scrollView;
    private AnchorPane scrollViewContainer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Testing platform");

        try {
            aem.startContainer();
            aem.addRemoteMonitoringAgent();
        } catch (Exception e) {
            System.out.println("Agents container failure");
            System.exit(0);
        } finally {
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
                Date now = new Date();
                Object[] arguments = new Object[1];
                arguments[0] = mm;
                aem.addAgentToMainContainer(now.toString(), "agents.RandomLocationAgent", arguments);
            }
        });
    }
}
