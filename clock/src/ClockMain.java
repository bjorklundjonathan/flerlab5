import java.util.concurrent.Semaphore;

import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class ClockMain {

    public static void main(String[] args) throws InterruptedException {
        AlarmClockEmulator emulator = new AlarmClockEmulator();
        CounterMonitor mainMonitor = new CounterMonitor(emulator.getOutput());
        CountMainClock clock = new CountMainClock(mainMonitor);
        AlarmMonitor alarmMonitor = new AlarmMonitor(emulator.getOutput());

        clock.start();

        ClockInput  in  = emulator.getInput();
        Semaphore mutex = new Semaphore(1);

        System.out.println("testtest");

        while (true) {
            System.out.println(in.getSemaphore().availablePermits());
            in.getSemaphore().acquire();
            mutex.acquire();
            UserInput userInput = in.getUserInput();
            int choice = userInput.getChoice();
            int h = userInput.getHours();
            int m = userInput.getMinutes();
            int s = userInput.getSeconds();

            System.out.println("hasllooeoadawed");

            switch (choice) {
                case ClockInput.CHOICE_SET_TIME:
                    mainMonitor.setTime(h, m, s);
                    break;
                case ClockInput.CHOICE_SET_ALARM:
                    alarmMonitor.setTime(h, m, s);
                    break;
                case ClockInput.CHOICE_TOGGLE_ALARM:
                    alarmMonitor.toggleAlarm();
                    break;
            }

            System.out.println("choice=" + choice + " h=" + h + " m=" + m + " s=" + s);
            in.getSemaphore().release();
            mutex.release();
        }
    }
}
