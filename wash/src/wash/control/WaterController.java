package wash.control;

import actor.ActorThread;
import wash.control.WashingMessage.Order;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    WashingIO io;
    boolean fill = false;
    boolean drain = false;
    double wantedLevel = -1;
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
                    System.out.println("ny sender");
                    switch(m.getOrder()) {
                        case WATER_IDLE:
                            io.drain(false);
                            io.fill(false);
                            wantedLevel = -1;
                            sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case WATER_DRAIN:
                            io.drain(true);
                            io.fill(false);
                            wantedLevel = 0;
                            break;
                        case WATER_FILL:
                            System.out.println("fyller?====");
                            io.drain(false);
                            io.fill(true);
                            wantedLevel = WashingIO.MAX_WATER_LEVEL/2;
                            break;
                        default:
                            break;
                    }
                }

                if(io.getWaterLevel() >= wantedLevel && wantedLevel > 0) {
                    io.fill(false);
                    wantedLevel = -1;
                    sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                    System.out.println("ack från full");
                }
                if (io.getWaterLevel() <= 0 && wantedLevel == 0) {
                    wantedLevel = -1;
                    sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                    System.out.println("ack från tom");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
