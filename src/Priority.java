import java.util.ArrayList;

public class Priority extends Scheduling {
    public Priority(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }

    @Override
    public void schedule() {
        System.out.println("Priority scheduling logic will be implemented here.");
    }
}
