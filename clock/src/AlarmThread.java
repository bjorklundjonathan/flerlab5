public class AlarmThread extends Thread {
    AlarmMonitor aMonitor;
    CounterMonitor cMonitor;

    public AlarmThread(AlarmMonitor aMonitor, CounterMonitor cMonitor) {
        this.aMonitor = aMonitor;
        this.cMonitor = cMonitor;
    }

    public void run() {
        while(true) {
            if(cMonitor.currentTime() == aMonitor.getTime()) {
                aMonitor.alarm();   
            }
        }
    }
    
}