import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SRTF extends Scheduling {
    public SRTF(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.getArrivalTime()));
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));
        int currentTime = 0;
        int completed = 0;
        while (completed < processes.size()) {
            for (Process process : processes) {
                int x=(process.getArrivalTime()),y=process.remainingTime;
                if (process.getArrivalTime() <= currentTime && process.remainingTime > 0&&!queue.contains(process)) {
                    queue.add(process);
                }
            }
            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }
            Process currentProcess = queue.poll();
            timeline.assign_process(currentTime,1,currentProcess.getId());
            currentProcess.remainingTime--;
            currentTime++;
            if (currentProcess.remainingTime == 0) {
                completed++;
                currentProcess.setTurnaroundTime(currentTime-currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime()-currentProcess.getBurstTime());
            }
        }
    }
}
