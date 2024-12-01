import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();
        System.out.println("Enter the number of processes:");
        int numProcesses = scanner.nextInt();
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.next();
            System.out.print("Color: ");
            String color = scanner.next();
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            System.out.print("Priority: ");
            int priority = scanner.nextInt();

            processes.add(new Process(name, color, arrivalTime, burstTime, priority));
        }

        System.out.println("\nChoose a scheduling algorithm:");
        System.out.println("1. Priority Scheduling");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.println("3. Shortest Remaining Time First (SRTF)");
        System.out.println("4. FCAI Scheduling");

        int choice = scanner.nextInt();
        Scheduling scheduler = null;

        switch (choice) {
            case 1:
                scheduler = new Priority(processes);
                break;
            case 2:
                scheduler = new SJF(processes);
                break;
            case 3:
                scheduler = new SRTF(processes);
                break;
            case 4:
                scheduler = new FCAI(processes);
                break;
            default:
                System.out.println("Invalid choice!");
                System.exit(0);
        }

        scheduler.schedule();
        scheduler.printResults();
    }
}
