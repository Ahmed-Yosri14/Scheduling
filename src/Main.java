import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(0, "#FF0000", 0, 17, 4,4));
        processes.add(new Process(1, "#00FF00", 0, 6, 9,3));
        processes.add(new Process(2, "#0000FF", 0, 10, 3,5));
        processes.add(new Process(3, "#111111", 0, 4, 10,3));
        Priority a=new Priority(processes,new Timeline(),false);
        a.schedule();
        Timeline timeline = a.timeline;


        new SchedulerGUI(processes, timeline,"Shortest- Remaining Time First");

    }
}
