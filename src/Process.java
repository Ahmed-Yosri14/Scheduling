public class Process {
    private final int id;
    private final String color;
    private final int arrivalTime;
    public int burstTime;
    private final int priority;
    public int waitingTime;
    private int turnaroundTime;
    public int remainingTime;


    public Process(int id, String color, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime=burstTime;
    }

    public int getId() {
        return id;
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
                "Process{id='%s', color='%s', arrivalTime=%d, burstTime=%d, priority=%d}",
                id, color, arrivalTime, burstTime, priority
        );
    }
}
