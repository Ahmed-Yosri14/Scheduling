import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJF extends Scheduling {
    public SJF(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)); // 0 2 3 4
        ArrayList<Process> copy = new ArrayList<>(processes);
        int currentTime = 0;
        List<Process> readyQueue = new ArrayList<>();
        while (!copy.isEmpty() || !readyQueue.isEmpty()) {
            while (!copy.isEmpty() && copy.get(0).getArrivalTime() <= currentTime) {
                readyQueue.add(copy.remove(0));
            }
            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(Process::getBurstTime));
                Process currentProcess = readyQueue.remove(0);
                currentTime += currentProcess.getBurstTime();
                int waitingTime = currentTime - currentProcess.getArrivalTime() - currentProcess.getBurstTime();
                int turnaroundTime = currentTime - currentProcess.getArrivalTime();
                currentProcess.setWaitingTime(waitingTime);
                currentProcess.setTurnaroundTime(turnaroundTime);
                timeline.assign_process(currentTime - currentProcess.getBurstTime() , currentProcess.getBurstTime() , currentProcess.getId());

            } else {
                currentTime++;
            }
        }
    }
}
