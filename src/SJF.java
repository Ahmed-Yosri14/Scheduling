import java.util.ArrayList;

public class SJF extends Scheduling {
    public SJF(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }

    @Override
    public void schedule() {
        System.out.println("SJF scheduling logic will be implemented here.");
    }
}
