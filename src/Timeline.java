import java.util.ArrayList;
import java.util.HashMap;

public class Timeline {
    private ArrayList <Pair> timeline;
    private int time;
    Timeline(){
        timeline = new ArrayList<>();
        time=0;
    }
    public void assign_process(int process_id, int time_spent){
        if (timeline.isEmpty()){
            timeline.add(new Pair(process_id,time_spent));
        }
        else if (timeline.getLast().getFirst()==process_id){
            timeline.getLast().setSecond(timeline.getLast().getSecond()+time_spent);
        }
        else {
            timeline.add(new Pair(process_id,time_spent));
        }
    }


}
