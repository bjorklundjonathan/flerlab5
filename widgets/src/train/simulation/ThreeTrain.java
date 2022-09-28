package train.simulation;

import java.util.LinkedList;
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
        
        Segment first = route.next();
        Segment second = route.next();
        Segment third = route.next();

        try {
            System.out.println("adding?");
            addSegment(first);
            System.out.println("added one");
            addSegment(second);
            addSegment(third);
            System.out.println("added");
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
        System.out.println("trying");
        monitor.enterSegment(seg);
        System.out.println("done");
        seg.enter();
        queue.addFirst(seg);
    }

    private void remove() {
        Segment seg = queue.removeLast();
        monitor.exitSegment(seg);
        seg.exit();
    }
}
 