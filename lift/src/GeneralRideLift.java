import java.util.ArrayList;
import java.util.List;

import lift.Lift;
import lift.LiftMonitor;
import lift.LiftView;
import lift.Passenger;
import lift.PassengerThread;

public class GeneralRideLift {
    
    public static void main(String[] args) {

        final int NBR_FLOORS = 7, MAX_PASSENGERS = 4;
        LiftView  view = new LiftView(NBR_FLOORS, MAX_PASSENGERS);
        LiftMonitor monitor = new LiftMonitor(view, NBR_FLOORS);
        Lift lift = new Lift(monitor, view);
        List<PassengerThread> passengers = new ArrayList<>();

        for(int i = 0 ; i < 20 ; i++) {
            passengers.add(new PassengerThread(view.createPassenger(), monitor));
        }

        for(int i = 0 ; i < 20 ; i++) {
            passengers.get(i).start();
        }

        lift.start();
    }
}
