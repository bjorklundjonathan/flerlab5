import java.util.concurrent.Semaphore;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;
import java.time.LocalTime;

public class CounterMonitor {

    ClockOutput out;
    int hour;
    int minute;
    int second;

    public CounterMonitor(ClockOutput out){
        this.out = out;
        LocalTime now = LocalTime.now();
        hour = now.getHour();
        minute = now.getMinute();
        second = now.getSecond();
    }

    public void increment() {
        second++;
        if(sixty(second)) {
            minute++;
            second = 0;
            if(sixty(minute)) {
                hour++;
                minute = 0;
                if(hour == 24) {
                    hour = 0;
                }
            }
        }
        out.displayTime(hour, minute, second);
    }

    private boolean sixty(int t) {
        return t % 60 == 0;
    }

    public int currentTime() {
        String str = String.valueOf(hour) + String.valueOf(minute) + String.valueOf(second);
        return Integer.parseInt(str);
    }

    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

}
