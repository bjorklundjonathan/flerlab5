package train.simulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import train.model.Segment;

public class TrainMonitor {

    Set<Segment> busySegments = new HashSet<>();
    Map<Segment, Semaphore> busy = new HashMap<>();
    Semaphore segmentFree = new Semaphore(1);


    public synchronized boolean segmentBusy(Segment segment) {
        return busySegments.contains(segment);
    }


    public synchronized void enterSegment(Segment segment) throws InterruptedException {
        while(busySegments.contains(segment)) {
            wait();
        }
        busySegments.add(segment);
    }

    public synchronized void exitSegment(Segment segment) {
        busySegments.remove(segment);
        notifyAll();
    }
    
}
