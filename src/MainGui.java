import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGui extends JFrame implements ActionListener {
    AlgorithmButton priority , fcai,srtf,sjf;
    private int x=170 , y=500 ;
    MainGui(){
        priority = new AlgorithmButton("PRIORITY",this,x , y);
        fcai = new AlgorithmButton("FCAI",this,x*2 , y);
        srtf = new AlgorithmButton("SRTF",this,x*3,y);
        sjf = new AlgorithmButton("SJF",this,x*4,y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.add(priority);
        this.add(fcai);
        this.add(srtf);
        this.add(sjf);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(0, "#FF0000", 0, 17, 4,4));
        processes.add(new Process(1, "#00FF00", 3, 6, 9,3));
        processes.add(new Process(2, "#0000FF", 4, 10, 3,5));
        processes.add(new Process(3, "#111111", 29, 4, 10,3));
        FCAI a = new FCAI(processes,new Timeline());
        Scheduling scheduling = null;
        String Algorithm = "";
        if (e.getSource() == priority) {
            scheduling = new Priority(processes,new Timeline(), false);
            Algorithm = "Priority";
        }
        else if (e.getSource() == fcai) {
            scheduling = new FCAI(processes,new Timeline());
            Algorithm = "Fcai";
        }
        else if (e.getSource() == srtf) {
            scheduling = new SRTF(processes,new Timeline());
            Algorithm = "Srtf";
        }
        else if (e.getSource() == sjf) {
            scheduling = new SJF(processes,new Timeline());
            Algorithm = "Sjf";
        }
        scheduling.schedule();
        SchedulerGUI scheduler = new SchedulerGUI(processes,scheduling.timeline,Algorithm,5);
    }
}
