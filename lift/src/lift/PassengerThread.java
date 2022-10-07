package lift;

public class PassengerThread extends Thread {
    

    Passenger pass;
    LiftMonitor monitor;

    public PassengerThread(Passenger pass, LiftMonitor monitor) {
        this.pass = pass;
        this.monitor = monitor;
    }

    public void run() {
        pass.begin();
        try {
            monitor.floorAppend(pass.getStartFloor());
            monitor.floorAppend(pass.getDestinationFloor());
            monitor.loadPassenger(pass);
            monitor.exitPassenger(pass);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pass.end();

    }


}
