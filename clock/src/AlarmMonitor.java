import clock.io.ClockOutput;

public class AlarmMonitor {

    ClockOutput out;
    int hour;
    int minute;
    int second;
    boolean alarmOn = false;

    public AlarmMonitor(ClockOutput out) {
        this.out = out;
    }

    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getTime() {
        String str = String.valueOf(hour) + String.valueOf(minute) + String.valueOf(second);
        return Integer.parseInt(str);
    }

    public void toggleAlarm() {
        alarmOn = alarmOn ^ true;
        out.setAlarmIndicator(alarmOn);
    }

    public void alarm() {
        out.alarm();
    }
    
}
