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
        int agingThreshold = 5;
        List<Process> readyQueue = new ArrayList<>();
        while (!copy.isEmpty() || !readyQueue.isEmpty()) {
            while (!copy.isEmpty() && copy.get(0).getArrivalTime() <= currentTime) {
                Process newProcess = copy.remove(0);
                newProcess.setEffectiveBurstTime(newProcess.getBurstTime());
                readyQueue.add(newProcess);
            }
            for (Process process : readyQueue) {
                int waitingTime = currentTime - process.getArrivalTime();
                if (waitingTime >= agingThreshold) {
                    process.setEffectiveBurstTime(Math.max(1, process.getBurstTime() - (waitingTime / agingThreshold)));
                }
            }
            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(Process::getEffectiveBurstTime));
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
