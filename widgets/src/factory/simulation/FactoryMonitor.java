package factory.simulation;

import java.util.LinkedList;
import java.util.Queue;

import factory.model.Conveyor;

public class FactoryMonitor {

    Conveyor conveyor;

    Queue<Integer> stack = new LinkedList<>();

    public FactoryMonitor(Conveyor conveyor) {
        this.conveyor = conveyor;
    }

    public synchronized void stopConveyor() {
        conveyor.off();
        stack.add(1);
    }

    public synchronized void tryStartConeveyor() throws InterruptedException {
        while(!stack.isEmpty()) {
            wait();
        }
        conveyor.on();
    }

    public synchronized void markDone() {
        stack.poll();
        notifyAll();
    }
    
}
