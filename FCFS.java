import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n, temp;
        float avgtat = 0, avgwt = 0;
        System.out.println("*** First Come First Serve Scheduling ***");
        System.out.print("Enter Number of Process: ");
        n = sc.nextInt();
        int process[] = new int[n];
        int arrivaltime[] = new int[n];
        int burstTime[] = new int[n];
        int completionTime[] = new int[n];
        int TAT[] = new int[n];
        int waitingTime[] = new int[n];
        for (int i = 0; i < n; i++) {
            process[i] = (i + 1);
            System.out.print("\nEnter Arrival Time for processor " + (i + 1) + ":");
            arrivaltime[i] = sc.nextInt();
            System.out.print("Enter Burst Time for processor " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrivaltime[i] > arrivaltime[j]) {
                    temp = process[j];
                    process[j] = process[i];
                    process[i] = temp;
                    temp = arrivaltime[j];
                    arrivaltime[j] = arrivaltime[i];
                    arrivaltime[i] = temp;
                    temp = burstTime[j];
                    burstTime[j] = burstTime[i];
                    burstTime[i] = temp;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                completionTime[i] = arrivaltime[i] + burstTime[i];
            } else {
                if (arrivaltime[i] > completionTime[i - 1]) {
                    completionTime[i] = arrivaltime[i] + burstTime[i];
                } else {
                    completionTime[i] = completionTime[i - 1] + burstTime[i];
                }
            }
        }

        System.out.println("\n*** First Come First Serve Scheduling ***");
        System.out.println("Processor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {

            TAT[i] = completionTime[i] - arrivaltime[i];
            waitingTime[i] = TAT[i] - burstTime[i];
            avgtat += TAT[i];
            avgwt += waitingTime[i];
            System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + burstTime[i] + "ms\t\t"
                    + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
        }
        System.out.println("\nAverage turn around time of processor: " + (avgtat / n)
                + "ms\nAverage waiting time of processor: " + (avgwt / n) + "ms");
        sc.close();
    }
}
/*The given Java program implements the **First-Come, First-Serve (FCFS) scheduling** algorithm, which is one of the simplest CPU scheduling algorithms. In FCFS scheduling, the process that arrives first is the one that gets executed first. This program calculates the **completion time**, **turnaround time**, and **waiting time** for each process based on their arrival and burst times.

Here’s a breakdown of how the program works:

1. **Getting Inputs**:
   - The program first takes the number of processes as input.
   - For each process, it collects the **arrival time** (when the process arrives) and **burst time** (the time needed for the process to complete).

2. **Sorting Processes by Arrival Time**:
   - The program sorts the processes based on their arrival times. This ensures that processes are executed in the order they arrive (the FCFS rule).
   - If a process has an earlier arrival time, it will be scheduled first.

3. **Calculating Completion Times**:
   - For each process:
     - If it’s the first process, its **completion time** is simply its arrival time plus its burst time.
     - For subsequent processes, the program checks if the current process arrived after the previous process finished. If it did, it waits until its arrival time to start, else it starts right after the previous process.
     - The **completion time** is calculated by adding the burst time to the time when the process starts executing.

4. **Calculating Turnaround and Waiting Times**:
   - **Turnaround Time (TAT)** for each process is calculated as:  
     \( \text{TAT} = \text{Completion Time} - \text{Arrival Time} \)
   - **Waiting Time** for each process is calculated as:
     \( \text{Waiting Time} = \text{Turnaround Time} - \text{Burst Time} \)
   - The program also calculates the **average turnaround time** and **average waiting time** for all processes by summing up the respective times and dividing by the number of processes.

5. **Output**:
   - The program prints a table displaying each process along with its arrival time, burst time, completion time, turnaround time, and waiting time.
   - It also outputs the average turnaround time and waiting time for all processes.

Here’s an example of the program’s output format:

```
Processor  Arrival time  Burst time  Completion Time  Turn around time  Waiting time
P1         0ms          4ms         4ms              4ms               0ms
P2         1ms          3ms         7ms              6ms               3ms
...

Average turn around time of processor: X ms
Average waiting time of processor: Y ms
``` 

This program helps to analyze the performance of FCFS scheduling by providing turnaround and waiting times, which are key metrics in CPU scheduling.*/
