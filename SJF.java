import java.util.*;

public class SJF {

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
    System.out.print("Enter no of process:");
    int n = sc.nextInt();
    int process[] = new int[n];
    int arrivaltime[] = new int[n];
    int burstTime[] = new int[n];
    int completionTime[] = new int[n];
    int TAT[] = new int[n];
    int waitingTime[] = new int[n];
    int visit[] = new int[n];
    int remburstTime[] = new int[n];
    int temp, start = 0, total = 0;
    float avgwt = 0, avgTAT = 0;

    for (int i = 0; i < n; i++) {
      System.out.println(" ");
      process[i] = (i + 1);
      System.out.print("Enter Arrival Time for processor " + (i + 1) + ":");
      arrivaltime[i] = sc.nextInt();
      System.out.print("Enter Burst Time for processor " + (i + 1) + ": ");
      burstTime[i] = sc.nextInt();
      remburstTime[i] = burstTime[i];
      visit[i] = 0;
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (arrivaltime[i] < arrivaltime[j]) {
          temp = process[j];
          process[j] = process[i];
          process[i] = temp;
          temp = arrivaltime[j];
          arrivaltime[j] = arrivaltime[i];
          arrivaltime[i] = temp;
          temp = remburstTime[j];
          remburstTime[j] = remburstTime[i];
          remburstTime[i] = temp;
          temp = burstTime[j];
          burstTime[j] = burstTime[i];
          burstTime[i] = temp;
        }
      }
    }
    while (true) {
      int min = 99, c = n;
      if (total == n) {
        break;
      }
      for (int i = 0; i < n; i++) {
        if ((arrivaltime[i] <= start) && (visit[i] == 0) && (burstTime[i] < min)) {
          min = burstTime[i];
          c = i;
        }
      }

      if (c == n)
        start++;

      else {
        burstTime[c]--;
        start++;
        if (burstTime[c] == 0) {
          completionTime[c] = start;
          visit[c] = 1;
          total++;
        }
      }
    }

    for (int i = 0; i < n; i++) {
      TAT[i] = completionTime[i] - arrivaltime[i];
      waitingTime[i] = TAT[i] - remburstTime[i];
      avgwt += waitingTime[i];
      avgTAT += TAT[i];
    }
    System.out.println("*** Shortest Job First Scheduling (Preemptive) ***");
    System.out.println("Processor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
    System.out.println(
        "----------------------------------------------------------------------------------------------------------");
    for (int i = 0; i < n; i++) {
      System.out.println("P" + process[i] + "\t\t" + arrivaltime[i] + "ms\t\t" + remburstTime[i] + "ms\t\t"
          + completionTime[i] + "ms\t\t\t" + TAT[i] + "ms\t\t\t" + waitingTime[i] + "ms");
    }

    avgTAT /= n;
    avgwt /= n;

    System.out.println("\nAverage turn around time is " + avgTAT);
    System.out.println("Average waiting time is " + avgwt);
    sc.close();
  }
}
/*The `SJF` program simulates **Shortest Job First (SJF) Scheduling** with preemption, which is a scheduling algorithm used by operating systems to manage how CPU time is allocated to different processes. In this algorithm, the process with the shortest burst time (time needed to complete) is given priority. Since it’s preemptive, the CPU can switch to a new, shorter process if it arrives while another process is still running.

Here’s a simple explanation of the code:

### Key Variables

- **n**: Total number of processes.
- **process**: Stores process IDs (for display purposes).
- **arrivaltime**: Arrival time of each process.
- **burstTime**: Initial CPU time required by each process (burst time).
- **completionTime**: Time at which each process completes.
- **TAT** (Turn Around Time): The total time taken by each process from arrival to completion.
- **waitingTime**: The time each process spends waiting for the CPU.
- **visit**: A flag array to indicate if a process has finished executing.
- **remburstTime**: Remaining burst time for each process, used to manage preemptive scheduling.
- **start**: Tracks the current time in the schedule.
- **total**: Counts the number of completed processes.
- **avgwt** and **avgTAT**: Store the average waiting time and turn-around time, calculated at the end.

### Steps in the Program

1. **User Input**:
   - The program asks for the number of processes (`n`).
   - For each process, the user inputs:
     - `arrivaltime`: when the process arrives.
     - `burstTime`: the total CPU time the process requires.
   - Each process is assigned an ID (P1, P2, etc.) and stored in `process[]` for reference.

2. **Sorting by Arrival Time**:
   - The processes are sorted based on their arrival times to ensure that processes are considered in the order they arrive.

3. **Scheduling Logic**:
   - The main scheduling loop runs until all processes have been completed.
   - For each time step (`start`), the program:
     - Searches for the process with the shortest burst time that has arrived (`arrivaltime[i] <= start`), hasn’t finished (`visit[i] == 0`), and has the smallest `burstTime`.
     - If such a process is found, the CPU works on it for 1 unit of time (`burstTime[c]--`), simulating the execution.
     - If a process completes (its `burstTime` becomes 0), it records the `completionTime`, sets `visit[i]` to 1 to mark it as done, and increments `total`.
     - If no eligible process is found (e.g., all processes arrived later), `start` increments to advance time.

4. **Calculating Turn Around and Waiting Time**:
   - After all processes are completed, the program calculates:
     - **Turn Around Time (TAT)** for each process: `completionTime - arrivaltime`.
     - **Waiting Time** for each process: `TAT - remburstTime`.
   - The program also accumulates these times for calculating averages later.

5. **Output**:
   - The program displays a table of results for each process, showing:
     - Arrival time
     - Original burst time
     - Completion time
     - Turn-around time
     - Waiting time
   - It then calculates and displays:
     - **Average Turn Around Time**: The mean TAT for all processes.
     - **Average Waiting Time**: The mean waiting time for all processes.

### Example Walkthrough

Let’s say there are 3 processes with the following attributes:

| Process | Arrival Time | Burst Time |
|---------|--------------|------------|
| P1      | 0 ms        | 5 ms       |
| P2      | 1 ms        | 3 ms       |
| P3      | 2 ms        | 1 ms       |

1. **Step-by-Step Execution**:
   - **Start Time 0**: P1 is the only process available, so it starts.
   - **Start Time 1**: P2 arrives, but P1 (5ms) is still shorter than P2 (3ms).
   - **Start Time 2**: P3 arrives with 1 ms burst time, which is shorter than P1 (remaining 4 ms) and P2 (3 ms), so the CPU switches to P3.
   - **Start Time 3**: P3 completes, and the CPU resumes with the shortest process left, which is now P2.
   - **Following Steps**: The CPU continues based on the shortest burst time available until all processes complete.

The program then calculates and displays completion times, TATs, waiting times, and averages. 

### Summary
This preemptive SJF scheduling approach minimizes waiting time by prioritizing processes with shorter burst times, switching to them as they arrive, and recording results based on completion times.*/

