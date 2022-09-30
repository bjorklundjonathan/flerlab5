package train.simulation;

import java.util.ArrayList;
import java.util.List;

import train.model.Route;
import train.view.TrainView;

public class TrainSimulation {

    public static void main(String[] args) {

        TrainView view = new TrainView();

        List<ThreeTrain> trains = new ArrayList<>();

        TrainMonitor monitor = new TrainMonitor();

        for(int i = 0 ; i < 20 ; i++) {
            Route route = view.loadRoute();
            trains.add(new ThreeTrain(route, monitor));
            trains.get(i).start();
        }
        
        Route route1 = view.loadRoute();
        Route route2 = view.loadRoute();
        Route route3 = view.loadRoute();

        

        ThreeTrain train1 = new ThreeTrain(route1, monitor);
        ThreeTrain train2 = new ThreeTrain(route2, monitor);
        ThreeTrain train3 = new ThreeTrain(route3, monitor);

        //train1.start();
        //train2.start();
        //train3.start();
    }

}
