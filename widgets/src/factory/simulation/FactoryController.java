package factory.simulation;

import factory.model.Conveyor;
import factory.model.Tool;
import factory.model.Widget;

public class FactoryController {
    
    public static void main(String[] args) {
        Factory factory = new Factory();

        Conveyor conveyor = factory.getConveyor();
        FactoryMonitor monitor = new FactoryMonitor(conveyor);
        
        Tool press = factory.getPressTool();
        Tool paint = factory.getPaintTool();

        RunTool t1 = new RunTool(press, Widget.GREEN_BLOB, monitor);
        RunTool t2 = new RunTool(paint, Widget.ORANGE_MARBLE, monitor);

        t1.start();
        t2.start();

        /*while (true) {
            press.waitFor(Widget.GREEN_BLOB);
            conveyor.off();
            press.performAction();
            conveyor.on();
        }*/
    }
}
