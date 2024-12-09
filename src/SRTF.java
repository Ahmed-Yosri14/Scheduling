import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SRTF extends Scheduling {
    public SRTF(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }
   int last_process_id=-1;
    int max_waiting_time=Math.max(20*processes.get(0).getContextSwitchTime(),20);
    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(p -> p.getArrivalTime()));
        ArrayList<Process> queue = new ArrayList<>();
        ArrayList<Process> starvation = new ArrayList<>();
        int currentTime = 0;
        int completed = 0;
        while (completed < processes.size()) {
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0&&!queue.contains(process)&&!starvation.contains(process)) {
                    queue.add(process);
                }
            }
            queue.sort(Comparator.comparingInt(Process::getRemainingTime));
            int cnt = 0 ;
            int idx = -1;
            for (Process process : queue) {
                if (currentTime-process.getArrivalTime()>max_waiting_time&&!starvation.contains(process)&&process.getRemainingTime()>0)
                {idx=cnt;}
                cnt++;
            }
            if (idx!=-1){
                Process temp = queue.get(idx);
                queue.set(idx, queue.get(0));
                queue.set(0, temp);
                starvation.add(queue.removeFirst());
            }
            if (queue.isEmpty()&&starvation.isEmpty()) {
                currentTime++;
                continue;
            }
            Process currentProcess;
            if (starvation.isEmpty())
                currentProcess = queue.removeFirst();
            else
                currentProcess = starvation.removeFirst();

            if (last_process_id!=currentProcess.getId()&&last_process_id!=-1){
                currentTime+=currentProcess.getContextSwitchTime();
            }
            timeline.assign_process(currentTime,1,currentProcess.getId());
            last_process_id=currentProcess.getId();
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
