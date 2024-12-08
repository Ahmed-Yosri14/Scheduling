import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainGui extends JFrame implements ActionListener {
    AlgorithmButton priority, fcai, srtf, sjf;
    private int x = 170, y = 500;

    private List<String> colors = List.of(
            "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#00FFFF",
            "#FF00FF", "#FFA500", "#FFC0CB", "#808080", "#D3D3D3"
    );

    MainGui() {
        priority = new AlgorithmButton("PRIORITY", this, x, y);
        fcai = new AlgorithmButton("FCAI", this, x * 2, y);
        srtf = new AlgorithmButton("SRTF", this, x * 3, y);
        sjf = new AlgorithmButton("SJF", this, x * 4, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
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
        int contextSwitchTime = 0;

        try {
            String fileName = "input.txt";
            File file = new File(fileName);

            if (!file.exists()) {
                throw new IOException("File not found: " + fileName);
            }

            try (Scanner scanner = new Scanner(file)) {
                int numProcesses = Integer.parseInt(scanner.nextLine());
                int processId = 0;
                int colorIndex = 0;

                while (scanner.hasNextLine() && numProcesses > 0) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(" ");

                    if (parts.length == 4) {
                        int arrivalTime = Integer.parseInt(parts[0]);
                        int burstTime = Integer.parseInt(parts[1]);
                        int priority = Integer.parseInt(parts[2]);
                        int quantum = Integer.parseInt(parts[3]);

                        String processColor = colors.get(colorIndex % colors.size());
                        processes.add(new Process(processId, processColor, arrivalTime, burstTime, priority, quantum, contextSwitchTime));
                        processId++;
                        numProcesses--;
                        colorIndex++;
                    }
                }

                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    contextSwitchTime = Integer.parseInt(line);
                }
                for (Process process : processes) {
                    process.setContextSwitchTime(contextSwitchTime);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading input file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FCAI a = new FCAI(processes, new Timeline());
        Scheduling scheduling = null;
        String Algorithm = "";

        if (e.getSource() == priority) {
            scheduling = new Priority(processes, new Timeline(), false);
            Algorithm = "Priority";
        } else if (e.getSource() == fcai) {
            scheduling = new FCAI(processes, new Timeline());
            Algorithm = "Fcai";
        } else if (e.getSource() == srtf) {
            scheduling = new SRTF(processes, new Timeline());
            Algorithm = "Srtf";
        } else if (e.getSource() == sjf) {
            scheduling = new SJF(processes, new Timeline());
            Algorithm = "Sjf";
        }

        // Run the scheduler
        if (scheduling != null) {
            scheduling.schedule();
            SchedulerGUI scheduler = new SchedulerGUI(processes, scheduling.timeline, Algorithm, 0);
        }
    }

    public static void main(String[] args) {
        new MainGui();
    }
}
