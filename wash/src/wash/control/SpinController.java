package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.control.WashingMessage;
import wash.control.WashingMessage.Order;

public class SpinController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    WashingIO io;
    boolean left = true;
    boolean spin = false;

    public SpinController(WashingIO io) {
        this.io = io;
    }

    @Override
    public void run() {
        try {

            // ... TODO ...

            while (true) {
                // wait for up to a (simulated) minute for a WashingMessage
                WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);

                // if m is null, it means a minute passed and no message was received
                
                if (m != null) {
                    System.out.println("got " + m);
                    ActorThread<WashingMessage> sender = m.getSender();
                    switch(m.getOrder()) {
                        case SPIN_SLOW:
                            System.out.println("spinnar slow");
                            io.setSpinMode(WashingIO.SPIN_LEFT);
                            left = false;
                            spin = true;
                            break;
                        case SPIN_FAST:
                            io.setSpinMode(WashingIO.SPIN_FAST);
                            spin = true;
                            break;
                        case SPIN_OFF:
                            io.setSpinMode(WashingIO.SPIN_IDLE);
                            spin = false;
                            break;
                        default:
                            break;
                    }
                    sender.send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                } else if(spin) {
                    if(!left) {
                        io.setSpinMode(WashingIO.SPIN_RIGHT);
                        left = true;
                    } else {
                        io.setSpinMode(WashingIO.SPIN_LEFT);
                        left = false;
                    }
                }
                
                // ... TODO ...
            }
        } catch (InterruptedException unexpected) {
            // we don't expect this thread to be interrupted,
            // so throw an error if it happens anyway
            throw new Error(unexpected);
        }
    }
}
