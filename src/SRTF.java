import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SRTF extends Scheduling {
    public SRTF(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }
   int last_process_id=-1;
    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.getArrivalTime()));
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.getRemainingTime()));
        int currentTime = 0;
        int completed = 0;
        while (completed < processes.size()) {
            for (Process process : processes) {
                int x =(process.getArrivalTime()),y=process.getRemainingTime();
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0&&!queue.contains(process)) {
                    queue.add(process);
                }
            }
            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }
            Process currentProcess = queue.poll();
            if (last_process_id!=currentProcess.getId()){
                last_process_id=currentProcess.getId();
                currentTime+=currentProcess.getContextSwitchTime();
                continue;
            }
            timeline.assign_process(currentTime,1,currentProcess.getId());
            currentProcess.setRemainingTime(currentProcess.getRemainingTime()-1);
            currentTime++;
            if (currentProcess.getRemainingTime() == 0) {
                completed++;
                currentProcess.setTurnaroundTime(currentTime-currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnaroundTime()-currentProcess.getBurstTime());
            }
        }
    }
}
