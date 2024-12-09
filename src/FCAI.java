import java.util.*;

public class FCAI extends Scheduling {
    double v1 = -1;
    double v2 = -1;
    int add = 0;
    public FCAI(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }
    public void updateFactor(){
        for (Process process : processes) {
            v1=Math.max(process.getArrivalTime(),v1);
            v2=Math.max(process.getBurstTime(),v2);
        }
        for (Process process : processes) {
            process.updateFcaiFactor(v1/10,v2/10);
        }
    }

    @Override
    public void schedule() {
        processes.sort(Comparator.comparingInt(Process::getId));
        updateFactor();
        ArrayList<FcaiPair> readyQueue = new ArrayList<>();
        ArrayList<Process> processCopy = new ArrayList<>();
        for (Process process : processes) {
            processCopy.add(process.copy());
        }
        processCopy.sort(Comparator.comparingInt(Process::getArrivalTime));
        int time = 0 ;
        int lastid = -1;
        boolean get_by_factor = true;
        while (!processCopy.isEmpty()||!readyQueue.isEmpty()) {
            if (!processCopy.isEmpty()) {
                while (processCopy.getFirst().getArrivalTime() <= time) {
                    readyQueue.addLast(new FcaiPair(time,processCopy.getFirst()));
                    processCopy.removeFirst();
                    if (processCopy.isEmpty()) break;
                }
            }
            Process p = null;
            if (!readyQueue.isEmpty()&&!get_by_factor) {
                readyQueue.sort(Comparator.comparingInt(FcaiPair::getFirst));
                p = readyQueue.getFirst().getSecond();
                get_by_factor=true;
            }
            else if (!readyQueue.isEmpty()&&get_by_factor){
                int val = readyQueue.getFirst().getSecond().getFcaiFactor();
                int id = 0;
                int cnt=0;
                for (FcaiPair p2 : readyQueue) {
                    if (p2.getSecond().getFcaiFactor()<val){
                        val = p2.getSecond().getFcaiFactor();
                        id = cnt;
                    }
                    cnt++;
                }
                if (id>0){
                    FcaiPair temp = readyQueue.get(id);
                    readyQueue.set(id, readyQueue.get(0));
                    readyQueue.set(0, temp);
                }
                p = readyQueue.getFirst().getSecond();

            }
            else {
                time++;
                add--;
                continue;
            }
                if (lastid!=p.getId()) {
                    for (FcaiPair q : readyQueue) {
                        if (q.getSecond().getId()==lastid){
                            if (time-q.getSecond().getArrivalTime()==(q.getSecond().getQuantumTime())){
                                q.getSecond().setQuantumTime(q.getSecond().getQuantumTime()+2);
                            }
                            else q.getSecond().setQuantumTime(2*q.getSecond().getQuantumTime()-(time-q.getSecond().getArrivalTime()));
                            break;
                        }
                    }
                    time+=add;
                    int addt = (int) Math.ceil(0.4*p.getQuantumTime());
                    addt = Math.min(addt,p.getBurstTime());
                    addt = Math.max(addt,0);
                    timeline.assign_process(time, addt, p.getId());

                    p.setBurstTime(p.getBurstTime()-addt);
                    p.updateFcaiFactor(v1/10,v2/10,processes.get(p.getId()).getArrivalTime());
                    p.setArrivalTime(time);
                    time+=addt;
                    lastid=p.getId();
                    add= p.getContextSwitchTime();
                }
                else if (lastid==p.getId()){
                    timeline.assign_process(time, 1, p.getId());
                    time++;
                    p.setBurstTime(p.getBurstTime()-1);
                    p.updateFcaiFactor(v1/10,v2/10,processes.get(p.getId()).getArrivalTime());
                    if (time-p.getArrivalTime()==p.getQuantumTime()){
                        get_by_factor=false;
                    }
                    add= p.getContextSwitchTime();
                }
                readyQueue.removeFirst();
                if (p.getBurstTime()>0) {readyQueue.addLast(new FcaiPair(time,p));}
                else {
                    processes.get(p.getId()).setTurnaroundTime(time-processes.get(p.getId()).getArrivalTime());
                    processes.get(p.getId()).setWaitingTime(processes.get(p.getId()).getTurnaroundTime()-processes.get(p.getId()).getBurstTime());
                    lastid=-1;get_by_factor=false;}
        }
    }
}
