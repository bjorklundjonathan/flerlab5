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


    public void enterSegment(Segment segment) throws InterruptedException {
        
        busySegments.add(segment);
    }

    public void exitSegment(Segment segment) {
        busySegments.remove(segment);
    }
    
}
