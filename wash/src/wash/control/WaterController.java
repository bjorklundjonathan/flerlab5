package wash.control;

import actor.ActorThread;
import wash.control.WashingMessage.Order;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    WashingIO io;
    boolean fill = false;
    boolean drain = false;
    ActorThread<WashingMessage> sender = null;

    public WaterController(WashingIO io) {
        this.io = io;
    }
    /*  SPIN_OFF,
        SPIN_SLOW,
        SPIN_FAST,
        TEMP_IDLE,
        TEMP_SET_40,
        TEMP_SET_60,
        WATER_IDLE,
        WATER_FILL,
        WATER_DRAIN,
        ACKNOWLEDGMENT */

    @Override
    public void run() {
        while(true) {
            try {
                WashingMessage m = receiveWithTimeout(1000/Settings.SPEEDUP);
                if(m != null) {
                    sender = m.getSender();
                    switch(m.getOrder()) {
                        case WATER_IDLE:
                            fill = false;
                            drain = false;
                            sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case WATER_DRAIN:
                            fill = false;
                            drain = true;
                            break;
                        case WATER_FILL:
                            fill = true;
                            drain = false;
                            break;
                        default:
                            break;
                    }
                }
                if(io.getWaterLevel() >= WashingIO.MAX_WATER_LEVEL/2 && fill) {
                    fill = false;
                    sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                    System.out.println("ack från full");
                } else if (drain && io.getWaterLevel() <= 0) {
                    sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                }
                io.drain(drain);
                io.fill(fill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
