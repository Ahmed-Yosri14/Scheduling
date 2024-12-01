public class Process {
    private final String name;
    private final String color;
    private final int arrivalTime;
    private final int burstTime;
    private final int priority;
    private int waitingTime;
    private int turnaroundTime;


    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public String toString() {
        return String.format(
                "Process{name='%s', color='%s', arrivalTime=%d, burstTime=%d, priority=%d}",
                name, color, arrivalTime, burstTime, priority
        );
    }
}
