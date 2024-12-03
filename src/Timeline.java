import java.util.ArrayList;
import java.util.HashMap;

public class Timeline {
    private ArrayList <Pair> timeline;
    private int time;
    private String algorithm;
    Timeline(){
        timeline = new ArrayList<>();
        time=0;
    }
    public void assign_process( int start, int time_spent,int process_id){
        if (timeline.isEmpty()){
            timeline.add(new Pair(start,time_spent,process_id));
        }
        else if (timeline.getLast().getId()==process_id){
            timeline.getLast().setSecond(timeline.getLast().getSecond()+time_spent);
        }
        else {
            timeline.add(new Pair(start,time_spent,process_id));
        }
    }


    public ArrayList<Pair> getTimeline() {
        return timeline;
    }
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
