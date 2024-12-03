import java.util.ArrayList;

public abstract class Scheduling {
    protected ArrayList<Process> processes;
    protected Timeline timeline;
    public Scheduling(ArrayList<Process> processes, Timeline timeline) {
        this.processes = processes;
        this.timeline = timeline;
    }

    public abstract void schedule();

    public void printResults() {
        System.out.println("\nScheduling Results:");
        for (Process process : processes) {
            System.out.printf("Process %s - Waiting Time: %d, Turnaround Time: %d\n",
                    process.getId(),
                    process.getWaitingTime(),
                    process.getTurnaroundTime());
        }
    }

    public double calculateAverageWaitingTime() {
        int totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }
        return (double) totalWaitingTime / processes.size();
    }

    public double calculateAverageTurnaroundTime() {
        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        return (double) totalTurnaroundTime / processes.size();
    }
}
