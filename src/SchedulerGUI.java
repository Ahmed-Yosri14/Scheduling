import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SchedulerGUI extends JFrame {
    private ArrayList<Process> processes;
    private Timeline timeline;
    private int context_time;
    public SchedulerGUI(ArrayList<Process> processes, Timeline timeline,String SelectedAlgorithm, int context_time) {
        this.processes = processes;
        this.timeline = timeline;
        this.context_time = context_time;
        setTitle("CPU Scheduling Simulator");
        setSize(900, 600);
        setLayout(new BorderLayout());
        add(createGraphPanel(), BorderLayout.CENTER);
        add(createProcessTablePanel(), BorderLayout.EAST);
        add(createStatisticsPanel(SelectedAlgorithm), BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createGraphPanel() {
        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTimeline(g);
            }
        };
        graphPanel.setPreferredSize(new Dimension(600, 400));
        graphPanel.setBorder(BorderFactory.createTitledBorder("CPU Scheduling Graph"));
        return graphPanel;
    }

    private void drawTimeline(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        int graphWidth = getWidth();
        int graphHeight = getHeight();
        int padding = 20;
        g2d.drawLine(padding, padding, padding, graphHeight - padding);
        int x = 20, y = 50, height = 30;

        for (int i = 0; i < processes.size(); i++) {
            g2d.drawString("P" + processes.get(i).getId(), padding-15 , y+(i * height) + 20);
            g2d.setColor(Color.BLACK);
        }
        int time = 0 ;
        g2d.drawLine(padding, graphHeight - padding, graphWidth - padding, graphHeight - padding);
        for (Pair pair : timeline.getTimeline()) {
            Process process = processes.get(pair.getId());
            x+=(pair.getFirst()-time)*2;
            g.setColor(Color.decode(process.getColor()));
            g.fillRect(x, y + (process.getId() * height), pair.getSecond() * 10, height - 5);
            g.setColor(Color.WHITE);
            x += pair.getSecond() * 10;
            x+=3*context_time;
            time= pair.getFirst()+pair.getSecond();
        }
    }

    private JPanel createProcessTablePanel() {
        String[] columnNames = {"Process", "Color", "Arrival Time", "Burst Time", "Priority", "Wait Time", "Turnaround Time"};
        Object[][] data = new Object[processes.size()][7];

        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            data[i][0] = "P" + process.getId();
            data[i][1] = process.getColor();
            data[i][2] = process.getArrivalTime();
            data[i][3] = process.getBurstTime();
            data[i][4] = process.getPriority();
            data[i][5] = process.getWaitingTime();      // Waiting Time (WT)
            data[i][6] = process.getTurnaroundTime();   // Turnaround Time (TAT)
        }

        JTable processTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(processTable);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setPreferredSize(new Dimension(350, 400));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Processes Information"));

        return tablePanel;
    }


    private JPanel createStatisticsPanel(String SelectedAlgorithm) {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1));

        JLabel scheduleNameLabel = new JLabel("Schedule Name: " + SelectedAlgorithm);
        JLabel avgWaitingTimeLabel = new JLabel("AWT: " + calculateAverageWaitingTime());
        JLabel avgTurnaroundTimeLabel = new JLabel("ATAT: " + calculateAverageTurnaroundTime());

        statsPanel.add(scheduleNameLabel);
        statsPanel.add(avgWaitingTimeLabel);
        statsPanel.add(avgTurnaroundTimeLabel);

        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        return statsPanel;
    }

    private double calculateAverageWaitingTime() {
        int totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }
        return (double) totalWaitingTime / processes.size();
    }

    private double calculateAverageTurnaroundTime() {
        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        return (double) totalTurnaroundTime / processes.size();
    }
}
