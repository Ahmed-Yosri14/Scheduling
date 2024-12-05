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
        ArrayList<Process> deepCopy = new ArrayList<>();
        for (Process process : processes) {
            deepCopy.add(new Process(process.getId(), process.getColor(), process.getArrivalTime(),
                    process.getBurstTime(), process.getPriority(), process.getQuantumTime(), process.getContextSwitchTime()));
        }
        deepCopy.sort(Comparator.comparingInt(Process::getId));
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
            if (last_process_id!=currentProcess.getId()&&last_process_id!=-1){
                currentTime+=currentProcess.getContextSwitchTime();
            }
            else currentProcess.setArrivalTime(currentProcess.getArrivalTime()+1);
            timeline.assign_process(currentTime,1,currentProcess.getId());
            last_process_id=currentProcess.getId();
            currentProcess.setRemainingTime(currentProcess.getRemainingTime()-1);
            currentProcess.setWaitingTime(currentProcess.getWaitingTime()+currentTime-currentProcess.getArrivalTime());
            currentProcess.setArrivalTime(currentTime);
            currentTime++;
            if (currentProcess.getRemainingTime() == 0) {
                completed++;
                currentProcess.setTurnaroundTime(currentTime-currentProcess.getArrivalTime());
                currentProcess.setArrivalTime(deepCopy.get(currentProcess.getId()).getArrivalTime());
            }
        }
    }
}
