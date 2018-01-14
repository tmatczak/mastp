package agents;

import com.lynden.gmapsfx.javascript.object.LatLong;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import javafx.application.Platform;
import sample.MapManager;

import java.util.concurrent.ThreadLocalRandom;

public class MovingAgent extends Agent {

    private MapManager mm;
    private double latitude;
    private double longitude;
    private double step = 0.001;

    protected void setup() {
        parseArguments();
        setRandomCoordinates();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mm.setMarker(getLocalName(), new LatLong(latitude, longitude));
            }
        });
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

