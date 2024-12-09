import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Priority extends Scheduling {
    private final boolean aging;
    int add = 0;
    public Priority(ArrayList<Process> processes, Timeline timeline, boolean aging) {
        super(processes, timeline);
        this.aging = aging;
    }

    @Override
    public void schedule() {
        add = processes.get(0).getContextSwitchTime();
        ArrayList<Process>copy = new ArrayList<>(processes);
        copy.sort(Comparator.comparingInt(Process::getArrivalTime));
        int time = 0 ;
        int cnt = 0 ;
        int idx=0;
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        while (cnt < copy.size()) {
            if (idx<copy.size()) {
                while (copy.get(idx).getArrivalTime() <= time) {
                    queue.add(copy.get(idx));
                    idx++;
                    if (idx==copy.size()) break;
                }
            }
            if (!queue.isEmpty()){
                cnt++;
                Process p = queue.poll();
                timeline.assign_process(time,p.getBurstTime(),p.getId());
                processes.get(p.getId()).setWaitingTime(Math.max(time-p.getArrivalTime(), 0));
                processes.get(p.getId()).setTurnaroundTime(processes.get(p.getId()).getWaitingTime()+processes.get(p.getId()).getBurstTime());
                time+=p.getBurstTime();
                time+=p.getContextSwitchTime();
                add=p.getContextSwitchTime();
            }
            else {time++;add--;}
            if (aging){
                for (Process p : queue) {
                    p.increasePriority();
                }
            }
        }
        processes=copy;
    }
}
