package lift;

public class Lift extends Thread {
    

    LiftMonitor monitor;
    LiftView view;

    public Lift(LiftMonitor monitor, LiftView view) {
        this.monitor = monitor;
        this.view = view;
    }


    public void run() {
        while(true) {
            try {
                view.closeDoors();
                int next = monitor.blockGetNext();
                view.moveLift(monitor.current(), next);
                view.openDoors(next);
                monitor.updateCurret(next);
                monitor.waitForEntering();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
