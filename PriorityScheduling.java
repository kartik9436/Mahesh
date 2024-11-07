import java.util.Scanner;

class PriorityScheduling {

  public static void main(String[] args) {

    System.out.println("*** Priority Scheduling (Preemptive) ***");
    System.out.print("Enter Number of Process: ");
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int process[] = new int[n];
    int arrivaltime[] = new int[n];
    int burstTime[] = new int[n];
    int completionTime[] = new int[n];
    int priority[] = new int[n + 1];
    int TAT[] = new int[n];
    int waitingTime[] = new int[n];
    int burstTimecopy[] = new int[n];
    int min = 0, count = 0;
    int temp, time = 0, end;
    double avgWT = 0, avgTAT = 0;
    for (int i = 0; i < n; i++) {
      process[i] = (i + 1);
      System.out.println("");
      System.out.print("Enter Arrival Time for processor " + (i + 1) + ":");
      arrivaltime[i] = sc.nextInt();
      System.out.print("Enter Burst Time for processor " + (i + 1) + " : ");
      burstTime[i] = sc.nextInt();
      System.out.print("Enter Priority for " + (i + 1) + " process: ");
      priority[i] = sc.nextInt();
    }
    for (int i = 0; i < n - 1; i++) {
      for (int j = i + 1; j < n; j++) {
        if (arrivaltime[i] > arrivaltime[j]) {
          temp = process[i];
          process[i] = process[j];
          process[j] = temp;
          temp = arrivaltime[j];
          arrivaltime[j] = arrivaltime[i];
          arrivaltime[i] = temp;
          temp = priority[j];
          priority[j] = priority[i];
          priority[i] = temp;
          temp = burstTime[j];
          burstTime[j] = burstTime[i];
          burstTime[i] = temp;
        }
        if (arrivaltime[i] == arrivaltime[j] && priority[j] > priority[i]) {
          temp = process[i];
          process[i] = process[j];
          process[j] = temp;
          temp = arrivaltime[j];
          arrivaltime[j] = arrivaltime[i];
          arrivaltime[i] = temp;
          temp = priority[j];
          priority[j] = priority[i];
          priority[i] = temp;
          temp = burstTime[j];
          burstTime[j] = burstTime[i];
          burstTime[i] = temp;
        }
      }
    }
    System.arraycopy(burstTime, 0, burstTimecopy, 0, n);
    priority[n] = 999;
    for (time = 0; count != n; time++) {
      min = n;
      for (int i = 0; i < n; i++) {
        if (arrivaltime[i] <= time && priority[i] < priority[min] && burstTime[i] > 0)
          min = i;
      }
      burstTime[min]--;
      if (burstTime[min] == 0) {
        count++;
        end = time + 1;
        completionTime[min] = end;
        waitingTime[min] = end - arrivaltime[min] - burstTimecopy[min];
        TAT[min] = end - arrivaltime[min];
      }
    }

    for (int i = 0; i < n; i++) {
      avgTAT += TAT[i];
      avgWT += waitingTime[i];
    }
    System.out.println("\n*** Priority Scheduling (Preemptive) ***");
    System.out.println("Processor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
    System.out.println(
        "----------------------------------------------------------------------------------------------------------");
    for (int i = 0; i < n; i++) {
      System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + burstTimecopy[i] + "ms\t\t"
          + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");

    }
    avgWT /= n;
    avgTAT /= n;
    System.out.println("\nAverage Wating Time: " + avgWT);
    System.out.println("Average Turn Around Time: " + avgTAT);

    sc.close();
  }
}
/*This Java program implements the **Preemptive Priority Scheduling** algorithm, which is a CPU scheduling algorithm where processes with higher priority are given preference, even if they arrive while another process is executing. Here, "preemptive" means that if a new process with a higher priority arrives, it can interrupt (preempt) the currently running process.

Let’s go through the program step-by-step:

### Step-by-Step Explanation

1. **Input Process Details**:
   - The program first asks for the number of processes.
   - For each process, it collects:
     - **Arrival Time**: The time when the process arrives in the ready queue.
     - **Burst Time**: The time needed by the process to complete execution.
     - **Priority**: A priority level for each process (lower values mean higher priority).

2. **Sorting Processes**:
   - The processes are sorted based on their arrival times. This helps in managing which processes can be selected as time progresses.
   - If two processes have the same arrival time, the program will prioritize the one with a higher priority (lower priority number).

3. **Preemptive Priority Scheduling Logic**:
   - The scheduling begins at `time = 0`, and it continues until all processes are complete.
   - In each time unit, the algorithm selects the process that:
     - Has arrived by the current time.
     - Has the highest priority (smallest priority value).
   - The selected process runs for 1 time unit (since it's preemptive, the process may be interrupted after that).
   - The **burst time** of the selected process is reduced by 1 each time it runs.

4. **Completion and Waiting Time Calculations**:
   - If a process’s burst time reaches zero (it has completed its execution), the following details are recorded:
     - **Completion Time**: The time when the process completes execution.
     - **Turnaround Time (TAT)**: The total time taken by the process from arrival to completion, calculated as:
       \[
       \text{TAT} = \text{Completion Time} - \text{Arrival Time}
       \]
     - **Waiting Time**: The time the process spent waiting in the queue before executing, calculated as:
       \[
       \text{Waiting Time} = \text{TAT} - \text{Original Burst Time}
       \]
   - The program then tracks the number of completed processes and keeps iterating until all processes have completed.

5. **Calculating Averages**:
   - The program sums up the total waiting time and total turnaround time for all processes, and then calculates the **average waiting time** and **average turnaround time** by dividing these sums by the number of processes.

6. **Output**:
   - The program displays a table showing:
     - **Processor ID**
     - **Arrival Time**
     - **Burst Time**
     - **Completion Time**
     - **Turnaround Time**
     - **Waiting Time**
   - It then displays the **average waiting time** and **average turnaround time** for all processes.

### Example Output Structure

For each process, the output might look like this:

```
Processor	Arrival time	Burst time	Completion Time	Turn around time	Waiting time
P1		0ms		5ms		10ms		10ms			5ms
P2		2ms		3ms		8ms		6ms			3ms
...

Average Waiting Time: X ms
Average Turn Around Time: Y ms
```

### Key Takeaways

- **Priority Scheduling**: The process with the highest priority (lowest priority number) runs first. New arrivals with higher priority can interrupt running processes.
- **Preemptive**: The scheduler can switch to a different process whenever a new process with a higher priority arrives.
- **Calculations**: Turnaround and waiting times help assess the efficiency of this scheduling algorithm. Lower average waiting and turnaround times indicate better performance.

This program helps visualize how a preemptive priority-based scheduler would handle processes, where each process may get interrupted if a higher-priority one arrives, leading to varying waiting and turnaround times.*/
