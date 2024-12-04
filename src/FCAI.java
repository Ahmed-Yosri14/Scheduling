import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FCAI extends Scheduling {
    int calcFcaiFactor(Process obj,int timeSpent,double v1,double v2){

        return (10-obj.getPriority())+(int)Math.ceil(obj.getArrivalTime()/v1+obj.getBurstTime()/v2);
    }

    public FCAI(ArrayList<Process> processes, Timeline timeline) {
        super(processes, timeline);
    }

    @Override
    public void schedule() {
        int itr=0;

        ArrayList<ExtendedProcess>tot=new ArrayList<>();
        PriorityQueue<ExtendedProcess>curr=new PriorityQueue<>(new Comparator<ExtendedProcess>() {
            @Override
            public int compare(ExtendedProcess o1, ExtendedProcess o2) {
                int ret= Integer.compare(o1.fcaiFactor,o2.fcaiFactor);
                if(ret==0)return Integer.compare(o2.getPriority(),o1.getPriority());
                return ret;
            }
        });
        int [][]times=new int [processes.size()+1][3];
        // [idx][0] -> arrival time
        // [idx][1] -> actual execution time
        // [idx][2] -> total time
        int mxBurstTime=0;
        int mxArrivalTime=0;
        for(var i:processes){
            mxBurstTime=Math.max(i.getBurstTime(),mxBurstTime);
            mxArrivalTime=Math.max(i.getArrivalTime(),mxArrivalTime);
        }
        for(var i:processes){
            ExtendedProcess temp=new ExtendedProcess(i,calcFcaiFactor(i,0,mxArrivalTime/10.0,mxBurstTime/10.0));
            tot.add(temp);
        }
        tot.sort(Comparator.comparingInt(p->p.getArrivalTime()));
        int spent=0;
        ExtendedProcess now=null;
        while(!tot.isEmpty() || !curr.isEmpty() || now!=null){
            while(!tot.isEmpty() && tot.getFirst().getArrivalTime()==itr){
                curr.add(tot.getFirst());
                tot.removeFirst();
            }

            if(now==null){
                if(!curr.isEmpty())
                now=curr.poll();
                if(now==null){
                    itr++;
                    continue;
                }
            }

            if(spent>=Math.ceil(now.getQuantumTime()*0.4) ||now.getBurstTime()==0 || spent==now.getQuantumTime() ){
                if(now.getBurstTime()==0){
                    timeline.assign_process(itr-spent,spent,now.getId());
                    System.out.print(String.format("P%d %d -> ",now.getId(),now.getQuantumTime()));
                    now.setQuantumTime(now.getQuantumTime()+2);
                    System.out.println(now.getQuantumTime());
                    if(!curr.isEmpty())now=curr.poll();
                    else now=null;
                    spent=0;
                }
                else if(now.getQuantumTime()==spent){
                    timeline.assign_process(itr-spent,spent,now.getId());
                    now.fcaiFactor=calcFcaiFactor(now,spent,mxArrivalTime/10.0,mxBurstTime/10.0);
                    System.out.print(String.format("P%d %d -> ",now.getId(),now.getQuantumTime()));
                    now.setQuantumTime(now.getQuantumTime()+2);
                    System.out.println(now.getQuantumTime());
                    ExtendedProcess temp=now;
                    if(!curr.isEmpty())now=curr.poll();
                    else now=null;
                    curr.add(temp);
                    spent=0;
                }
                else if(!curr.isEmpty() &&
                        ( curr.peek().fcaiFactor<=calcFcaiFactor(now,spent,mxArrivalTime/10.0,mxBurstTime/10.0))
                        && spent>Math.ceil(now.getQuantumTime()*0.4) ){
                    timeline.assign_process(itr-spent,spent,now.getId());
                    now.fcaiFactor=calcFcaiFactor(now,spent,mxArrivalTime/10.0,mxBurstTime/10.0);
                    System.out.print(String.format("P%d %d -> ",now.getId(),now.getQuantumTime()));
                    now.setQuantumTime(now.getQuantumTime()+(now.getQuantumTime()-spent));
                    System.out.println(now.getQuantumTime());
                    ExtendedProcess temp=now;
                    if(!curr.isEmpty())now=curr.poll();
                    else now=null;
                    curr.add(temp);
                    spent=0;
                }

            }
            if(now!=null)
            now.setBurstTime(now.getBurstTime()-1);
            spent++;
            itr++;
        }
        for(var idx:processes)
        for(var i : timeline.getTimeline()){
            if(i.getId()==idx.getId()){
                times[idx.getId()][0]=idx.getArrivalTime();
                times[idx.getId()][1]+=i.getSecond();
                times[idx.getId()][2]=i.getSecond()+i.getFirst();
            }
        }
        for(var idx:processes){
            idx.setWaitingTime((times[idx.getId()][2]-times[idx.getId()][0])-times[idx.getId()][1]);
            idx.setTurnaroundTime(times[idx.getId()][2]-times[idx.getId()][0]);
        }

    }
}
class ExtendedProcess extends Process{
    public int fcaiFactor;
    public ExtendedProcess(Process process,int fcaiFactor) {
        super(process.getId(), process.getColor(),process.getArrivalTime(),process.getBurstTime(),process.getPriority(), process.getQuantumTime());
        this.fcaiFactor=fcaiFactor;
    }
}
