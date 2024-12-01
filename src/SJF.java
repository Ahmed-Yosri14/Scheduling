import java.util.ArrayList;

public class SJF extends Scheduling {
    public SJF(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    public void schedule() {
        System.out.println("SJF scheduling logic will be implemented here.");
    }
}
