package train.simulation;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import train.model.Segment;
import train.model.Route;

public class ThreeTrain extends Thread {

    Route route;
    TrainMonitor monitor;
    private LinkedList<Segment> queue = new LinkedList<>();

    public ThreeTrain(Route route, TrainMonitor monitor) {
        this.route = route;
        this.monitor = monitor;
    }

    public void run() {

        try {
            for(int i = 0 ; i < 4 ; i++) {
                Segment seg = route.next();
                addSegment(seg);
            }
    
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while(true) {
            Segment ns = route.next();
            try {
                addSegment(ns);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            remove();
        }
    }

    private void addSegment(Segment seg) throws InterruptedException {
        monitor.enterSegment(seg);
        seg.enter();
        queue.addFirst(seg);
    }

    private void remove() {
        Segment seg = queue.removeLast();
        seg.exit();
        monitor.exitSegment(seg);
    }
}
 