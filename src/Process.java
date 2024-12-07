public class Process {
    private final int id;
    private final String color;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int waitingTime;
    private int turnaroundTime;
    private int remainingTime;
    private int quantumTime;
    private int contextSwitchTime;
    private int effectiveBurstTime;

    public Process(int id, String color, int arrivalTime, int burstTime, int priority, int quantumTime,int contextSwitchTime) {
        this.id = id;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime=burstTime;
        this.quantumTime=quantumTime;
        this.contextSwitchTime=contextSwitchTime;
        this.effectiveBurstTime = burstTime;
    }
    public int getContextSwitchTime() {
        return contextSwitchTime;
    }
    public void setContextSwitchTime(int contextSwitchTime) {
        this.contextSwitchTime = contextSwitchTime;
    }


    public int getQuantumTime() {
        return quantumTime;
    }
    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
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
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }
    public void increasePriority() {
        this.priority--;
        return;
    }

    public int getWaitingTime() {
        return waitingTime;
    }


    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public void setArrivalTime(int Time) {
        this.arrivalTime = Time;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
    public int getEffectiveBurstTime() {
        return effectiveBurstTime;
    }

    public void setEffectiveBurstTime(int effectiveBurstTime) {
        this.effectiveBurstTime = effectiveBurstTime;
    }

    @Override
    public String toString() {
        return String.format(
                "Process{id='%s', color='%s', arrivalTime=%d, burstTime=%d, priority=%d}",
                id, color, arrivalTime, burstTime, priority
        );
    }
}
