package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;

import static wash.control.WashingMessage.Order.*;

/**
 * Program 3 for washing machine. This also serves as an example of how washing
 * programs can be structured.
 * 
 * This short program stops all regulation of temperature and water levels,
 * stops the barrel from spinning, and drains the machine of water.
 * 
 * It can be used after an emergency stop (program 0) or a power failure.
 */
public class WashingProgram2 extends ActorThread<WashingMessage> {

    private WashingIO io;
    private ActorThread<WashingMessage> temp;
    private ActorThread<WashingMessage> water;
    private ActorThread<WashingMessage> spin;
    
    public WashingProgram2(WashingIO io,
                           ActorThread<WashingMessage> temp,
                           ActorThread<WashingMessage> water,
                           ActorThread<WashingMessage> spin) 
    {
        this.io = io;
        this.temp = temp;
        this.water = water;
        this.spin = spin;
    }
    
    @Override
    public void run() {
        try {
            io.lock(true);

            water.send(new WashingMessage(this, WATER_FILL));
            ack();

            System.out.println("setting SPIN_SLOW...");
            spin.send(new WashingMessage(this, SPIN_SLOW));
            ack();

            temp.send(new WashingMessage(this, TEMP_SET_40));
            ack();
            
            // Instruct SpinController to rotate barrel slowly, back and forth
            // Expect an acknowledgment in response.

            // Spin for five simulated minutes (one minute == 60000 milliseconds)
            Thread.sleep(20 * 60000 / Settings.SPEEDUP);

            System.out.println("TEMP IDLE");
            temp.send(new WashingMessage(this, TEMP_IDLE));
            ack();

            spin.send(new WashingMessage(this, SPIN_OFF));
            ack();

            System.out.println("DRAINING");
            water.send(new WashingMessage(this, WATER_DRAIN));
            ack();



            water.send(new WashingMessage(this, WATER_FILL));
            ack();

            System.out.println("setting SPIN_SLOW...");
            spin.send(new WashingMessage(this, SPIN_SLOW));
            ack();

            temp.send(new WashingMessage(this, TEMP_SET_60));
            ack();

            Thread.sleep(30 * 60000 / Settings.SPEEDUP);

            System.out.println("TEMP IDLE");
            temp.send(new WashingMessage(this, TEMP_IDLE));
            ack();

            spin.send(new WashingMessage(this, SPIN_OFF));
            ack();

            System.out.println("DRAINING");
            water.send(new WashingMessage(this, WATER_DRAIN));
            ack();

            System.out.println("RINSING");
            for(int i = 0 ; i < 5 ; i++) {
                rinse();
            }

            water.send(new WashingMessage(this, WATER_DRAIN));
            ack();
            
            System.out.println("CENTRIFUGING");
            spin.send(new WashingMessage(this, SPIN_FAST));
            ack();

            Thread.sleep(5 * 60000 / Settings.SPEEDUP);
            System.out.println("klar cent");

            water.send(new WashingMessage(this, WATER_IDLE));
            ack();

            // Instruct SpinController to stop spin barrel spin.
            // Expect an acknowledgment in response.
            System.out.println("setting SPIN_OFF...");
            spin.send(new WashingMessage(this, SPIN_OFF));
            ack();
            
            // Now that the barrel has stopped, it is safe to open the hatch.
            io.lock(false);
            System.out.println("Program done.");
        } catch (InterruptedException e) {
            
            // If we end up here, it means the program was interrupt()'ed:
            // set all controllers to idle

            temp.send(new WashingMessage(this, TEMP_IDLE));
            water.send(new WashingMessage(this, WATER_IDLE));
            spin.send(new WashingMessage(this, SPIN_OFF));
            System.out.println("washing program terminated");
        }

    }
    
    private void rinse() throws InterruptedException {
        water.send(new WashingMessage(this, WATER_FILL));
        ack();
        
        Thread.sleep(2 * 60000 / Settings.SPEEDUP);

        water.send(new WashingMessage(this, WATER_DRAIN));
        ack();
    }

    private void ack() throws InterruptedException {
        WashingMessage ack = receive();
        System.out.println("washing program 1 got " + ack);
    }
}
