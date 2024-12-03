import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(0, "#FF0000", 0, 15, 2));
        processes.add(new Process(1, "#00FF00", 1, 3, 1));
        processes.add(new Process(2, "#0000FF", 2, 2, 3));

        SRTF a = new SRTF(processes,new Timeline());
        a.schedule();
        Timeline timeline = a.timeline;


        new SchedulerGUI(processes, timeline,"Shortest- Remaining Time First");

    }
}
