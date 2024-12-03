import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Priority extends Scheduling {
    private final boolean aging;
    public Priority(ArrayList<Process> processes, Timeline timeline, boolean aging) {
        super(processes, timeline);
        this.aging = aging;
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        int time = 0 ;
        int cnt = 0 ;
        int idx=0;
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        while (cnt < processes.size()) {
            if (idx<processes.size()) {
                while (processes.get(idx).getArrivalTime() <= time) {
                    queue.add(processes.get(idx));
                    idx++;
                    if (idx==processes.size()) break;
                }
            }
            if (!queue.isEmpty()){
                cnt++;
                Process p = queue.poll();
                time+=p.getBurstTime();
                timeline.assign_process(time,p.getBurstTime(),p.getId());
                processes.get(p.getId()).setWaitingTime(Math.max(time-p.getArrivalTime(), 0));
                processes.get(p.getId()).setTurnaroundTime(processes.get(p.getId()).getWaitingTime()+processes.get(p.getId()).getBurstTime());
            }
            else time++;
            if (aging){
                for (Process p : queue) {
                    p.increasePriority();
                }
            }
        }
    }
}
