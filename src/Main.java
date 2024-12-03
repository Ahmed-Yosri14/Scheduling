import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(0, "#FF0000", 0, 17, 4));
        processes.add(new Process(1, "#00FF00", 3, 6, 9));
        processes.add(new Process(2, "#0000FF", 4, 10, 3));
        processes.add(new Process(3, "#111111", 29, 4, 10));
        processes.get(0).waitingTime=4;
        processes.get(1).waitingTime=3;
        processes.get(2).waitingTime=5;
        processes.get(3).waitingTime=3;
        //SRTF a = new SRTF(processes,new Timeline());
        FCAI a=new FCAI(processes,new Timeline());
        a.schedule();
        Timeline timeline = a.timeline;


        new SchedulerGUI(processes, timeline,"Shortest- Remaining Time First");

    }
}
