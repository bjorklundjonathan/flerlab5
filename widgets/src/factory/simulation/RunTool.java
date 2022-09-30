package factory.simulation;
import factory.model.Tool;
import factory.model.Widget;

public class RunTool extends Thread {
    
    Tool tool;
    FactoryMonitor monitor;
    Widget widget;

    public RunTool(Tool tool, Widget widget, FactoryMonitor monitor) {
        this.tool = tool;
        this.monitor = monitor;
        this.widget = widget;
    }

    public void run() {
        while(true) {
            tool.waitFor(widget);
            monitor.stopConveyor();
            tool.performAction();
            monitor.markDone();
            try {
                System.out.println("trying to start");
                monitor.tryStartConeveyor();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
}
