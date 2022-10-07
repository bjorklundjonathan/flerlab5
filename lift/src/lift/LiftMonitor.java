package lift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.Notification;
import javax.swing.plaf.synth.SynthButtonUI;

import org.w3c.dom.Notation;

public class LiftMonitor {
    
    LiftView view;
    int currentFloor = 0;
    int currentPass = 0;
    boolean moving = false;
    boolean goingUp = true;
    boolean doorsOpen = false;
    boolean passengerEntering = false;
    List<PassengerThread> passengers;
    LinkedList<Integer> floorQueue = new LinkedList<>();
    Map<Integer, Boolean> goingtTo = new HashMap<Integer, Boolean>();
    Lift lift;
    int NBR_FLOORS;

    public LiftMonitor(LiftView view, int NBR_FLOORS) {
        this.view = view;
        view.openDoors(currentFloor);
        this.NBR_FLOORS = NBR_FLOORS;

        for(int i = 0 ; i < NBR_FLOORS ; i++) {
            goingtTo.put(i, false);
        }
    }

    private int nextFloor() {
        return (currentFloor + 1) % (NBR_FLOORS);
    }
    
    public synchronized int current() {
        return currentFloor;
    }

    public synchronized void updateCurret(int n) {
        currentFloor = n;
        notifyAll();
    }

    public synchronized void moveLift() throws InterruptedException {
        while(floorQueue.isEmpty()){
            wait();
        }
        closeDoors();
        int next = floorQueue.removeLast();
        view.moveLift(currentFloor, next);
        currentFloor = next;
        openDoors(currentFloor);
        notifyAll();
    }

    public synchronized int blockGetNext() throws InterruptedException {
        while(floorQueue.isEmpty()){
            wait();
        }
        int a = floorQueue.removeLast();
        for(int i = 0 ; i < a-currentFloor ; i++) {
            if(goingtTo.get(i)) {
                goingtTo.put(i, false);
                return i;
            }
        }
        return a;
    }

    public synchronized void openDoors(int floor) {
        view.openDoors(floor);
        doorsOpen = true;
    }

    public synchronized void floorAppend(int f) {
        floorQueue.addFirst(f);
        goingtTo.put(f, true);
        notifyAll();
    }

    public synchronized void waitForEntering() throws InterruptedException {
        while(passengerEntering) {
            wait();
        }
    }

    public synchronized void closeDoors() {
        view.closeDoors();
        doorsOpen = false;
    }

    public synchronized void loadPassenger(Passenger passenger) throws InterruptedException {
        while(!doorsOpen && passenger.getStartFloor() != currentFloor) {
            wait();
        }
        passengerEntering = true;
        passenger.enterLift();
        passengerEntering = false;
    }

    public synchronized void exitPassenger(Passenger passenger) throws InterruptedException {
        while(!doorsOpen && passenger.getDestinationFloor() != currentFloor) {
            wait();
        }
        passengerEntering = true;
        passenger.exitLift();
        passengerEntering = false;
    }




}
