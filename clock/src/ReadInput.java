import clock.io.ClockInput;

public class ReadInput extends Thread {
    AlarmMonitor monitor;
    ClockInput in;


    public ReadInput(AlarmMonitor monitor, ClockInput in) {
        this.monitor = monitor;
        this.in = in;
    }

    public void run() {
        while(true) {
            
        }
    }

}
