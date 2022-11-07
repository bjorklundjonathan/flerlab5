package wash.control;

import actor.ActorThread;
import wash.control.WashingMessage.Order;
import wash.io.WashingIO;

public class TemperatureController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    WashingIO io;
    int temp = 0;
    double minTemp = 0;
    int dt = 10;
    boolean heating = false;
    boolean cooling = false;
    boolean peaked = false;
    ActorThread<WashingMessage> sender = null;

    public TemperatureController(WashingIO io) {
        this.io = io;
    }

    @Override
    public void run() {
        while(true) {
            try {
                WashingMessage m = receiveWithTimeout(1000/Settings.SPEEDUP);
                if(m != null) {
                    sender = m.getSender();
                    switch(m.getOrder()) {
                        case TEMP_IDLE:
                            temp = 0;
                            cooling = true;
                            peaked = false;
                            sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case TEMP_SET_40:
                            temp = 40;
                            minTemp = 38.5;
                            break;
                        case TEMP_SET_60:
                            temp = 60;
                            minTemp = 58.5;
                            break;
                        default:
                            break;
                    }
                }
                if(io.getTemperature() < minTemp && temp > 0) {
                    io.heat(true);
                    heating = true;
                }
                if(temp-mu() <= io.getTemperature() && temp > 0) {
                    io.heat(false);
                    if(!peaked) {
                        sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                        peaked = true;
                    }
                }
                /*if(cooling && io.getTemperature() <= 22) {
                    sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                    cooling = false;
                }*/
                //System.out.println(!heat() + " " + heating);
                //System.out.println(!heat() && heating);
                //System.out.println((temp-mu() > io.getTemperature() || io.getTemperature() < m1() ) && temp != 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean heat() {
        return (temp-mu() > io.getTemperature() || io.getTemperature() < 38.5) && temp != 0;
    }

    private double mu() {
        return dt * 0.0478;
    }
    
    private double m1() {
        return dt * 0.000952;
    }
}
