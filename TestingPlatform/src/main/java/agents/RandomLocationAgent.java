package agents;

import com.lynden.gmapsfx.javascript.object.LatLong;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import javafx.application.Platform;
import sample.MapManager;

import java.util.concurrent.ThreadLocalRandom;

public class RandomLocationAgent extends Agent {

    private MapManager mm;

    protected void setup() {
        parseArguments();
        addBehaviour(new SimpleBehaviour( this ) {
            private boolean finished = false;

            public void action() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mm.setMarker(getLocalName(), getRandomCoordinates());
                    }
                });
                finished = true;
            }

            public  boolean done() {  return finished;  }
        });
    }

    private void parseArguments() {
        Object[] args = getArguments();
        if (args != null) {
            mm = (MapManager) args[0];
        }
    }

    private LatLong getRandomCoordinates() {
        int latitudeReminder = getRandomInt(0, 9999);
        int longitudeReminder = getRandomInt(0, 9999);
        double latitude = 52 + (double)latitudeReminder/10000;
        double longitude = 21 + (double)longitudeReminder/10000;
        return new LatLong(latitude, longitude);
    }

    private int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
