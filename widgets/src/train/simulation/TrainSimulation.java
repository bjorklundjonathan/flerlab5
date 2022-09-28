package train.simulation;

import java.util.LinkedList;
import java.util.Queue;

import train.model.Route;
import train.model.Segment;
import train.view.TrainView;
import train.simulation.ThreeTrain;

public class TrainSimulation {

    private static LinkedList<Segment> queue = new LinkedList<>();

    public static void main(String[] args) {

        TrainView view = new TrainView();
        
        Route route1 = view.loadRoute();
        //Route route2 = view.loadRoute();
        //Route route3 = view.loadRoute();

        TrainMonitor monitor = new TrainMonitor();

        ThreeTrain train1 = new ThreeTrain(route1, monitor);
        //ThreeTrain train2 = new ThreeTrain(route2, monitor);
        //ThreeTrain train3 = new ThreeTrain(route3, monitor);

        train1.start();
        //train2.start();
        //train3.start();
        
    }

}
