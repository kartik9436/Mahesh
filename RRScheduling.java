import java.util.*;

class Process {
    int processID;
    int arrival, burst, waiting, turnAround, remainingTime;
    int finish, completionTime;
}

public class RRScheduling {

    public static void main(String[] args) {
        int n, sumBurst = 0, quantum, time;
        double avgWAT = 0, avgTAT = 0;
        Scanner sc = new Scanner(System.in);
        Queue<Integer> q = new LinkedList<>();
        System.out.println("*** RR Scheduling (Preemptive) ***");
        System.out.print("Enter Number of Process: ");
        n = sc.nextInt();
        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].processID = i + 1;
            System.out.print("Enter the arrival time for P" + (i + 1) + ": ");
            p[i].arrival = sc.nextInt();
            System.out.print("Enter the burst time for P" + (i + 1) + ": ");
            p[i].burst = sc.nextInt();
            p[i].remainingTime = p[i].burst;
            p[i].finish = 0;
            sumBurst += p[i].burst;
            System.out.println();
        }
        System.out.print("\nEnter time quantum: ");
        quantum = sc.nextInt();
        Process pTemp;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p[i].arrival > p[j].arrival) {
                    pTemp = p[i];
                    p[i] = p[j];
                    p[j] = pTemp;
                }
            }
        }
        q.add(0);
        for (time = p[0].arrival; time < sumBurst;) {
            Integer I = q.remove();
            int i = I.intValue();
            if (p[i].remainingTime <= quantum) {
                time += p[i].remainingTime;
                p[i].remainingTime = 0;
                p[i].finish = 1;
                p[i].completionTime = time;
                p[i].waiting = time - p[i].arrival - p[i].burst;
                p[i].turnAround = time - p[i].arrival;
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if ((p[j].arrival <= time) && (p[j].finish != 1) && (!q.contains(J)))
                        q.add(j);
                }
            } else {
                time += quantum;
                p[i].remainingTime -= quantum;
                for (int j = 0; j < n; j++) {
                    Integer J = Integer.valueOf(j);
                    if (p[j].arrival <= time && p[j].finish != 1 && i != j && (!q.contains(J)))
                        q.add(j);
                }
                q.add(i);
            }
        }
        System.out.println("\n*** RR Scheduling (Preemptive) ***");
        System.out.println("Processor\tArrival time\tBrust time\tCompletion Time\t\tTurn around time\tWaiting time");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i].processID + "\t\t" + p[i].arrival + "ms\t\t" + p[i].burst + "ms\t\t"
                    + p[i].completionTime + "ms\t\t\t" + p[i].turnAround + "ms\t\t\t" + p[i].waiting + "ms");
            avgWAT += p[i].waiting;
            avgTAT += p[i].turnAround;
        }
        System.out.println("\nAverage turn around time of processor: " + (avgTAT / n)
                + "ms\nAverage waiting time of processor: " + (avgWAT / n) + "ms");
    }
}
/*The `OptimalPageReplacement` Java program simulates the optimal page replacement algorithm, which is used in operating systems to manage memory. In this algorithm, pages are loaded into a limited number of memory frames, and the goal is to minimize the number of "page faults" (when a page isn't in memory and must be loaded). The optimal page replacement algorithm always replaces the page that won't be used for the longest time in the future, which minimizes faults but requires knowing future page requests.

Here’s a breakdown of the code:

### Code Explanation

1. **Main Class and Initial Variables**:
   - The program uses a scanner to get user input.
   - Variables are initialized for:
     - `noofpages`: the total number of pages (or page requests) the user will enter.
     - `capacity`: the number of frames available in memory.
     - `hit`: counts the number of "hits" (when a requested page is already in memory).
     - `fault`: counts the number of "faults" (when a requested page is not in memory and has to be loaded).
   - A `frame` array holds the pages currently in memory, and `table` is used to store the frame contents after each request.

2. **User Input**:
   - The program asks the user for the number of pages (`noofpages`) and their values (representing page requests).
   - It then asks for the `capacity` of the frame (how many pages can be in memory at once).

3. **Page Replacement Logic**:
   - Initially, all frame slots are set to `-1` to indicate they are empty.
   - For each page request, the program checks if the page is already in memory (a "hit") or not (a "fault").
     - **Hit**: If the page is already in the `frame`, it increments the `hit` count and displays "H" for "hit."
     - **Fault**: If the page is not in the frame, the algorithm must load it, replacing an existing page if necessary.
       - If the frame is full, the algorithm looks ahead to determine which page will not be used for the longest time in the future, replacing that page with the new one.
       - If the frame is not full, it simply loads the page in the next available slot.
     - After processing each page, the current state of `frame` is saved in the `table` for later display.

4. **Output**:
   - After all page requests are processed, the program prints the frame contents after each request.
   - It calculates and displays:
     - `hitRatio`: the percentage of requests that were hits.
     - `faultRatio`: the percentage of requests that were faults.
   - It also displays the total number of page faults and hits.

### Example Walkthrough

Imagine you have `noofpages = 5` with pages `[1, 2, 3, 4, 2]` and a frame capacity of `3`.

1. **Processing Page 1**:
   - Frame is empty, so page 1 is loaded. This is a fault, and "F" is displayed.
   - Frame now contains `[1, -1, -1]`.

2. **Processing Page 2**:
   - Page 2 is not in frame, so it's loaded into the next slot. This is also a fault, and "F" is displayed.
   - Frame now contains `[1, 2, -1]`.

3. **Processing Page 3**:
   - Page 3 is not in frame, so it's loaded. This is a fault, and "F" is displayed.
   - Frame now contains `[1, 2, 3]`.

4. **Processing Page 4**:
   - Frame is full, and page 4 is not present.
   - Using the optimal replacement strategy, the program checks which of the pages (1, 2, or 3) is not needed soonest. It finds page 1 won’t be needed again, so it replaces page 1 with page 4.
   - This is a fault, and "F" is displayed.
   - Frame now contains `[4, 2, 3]`.

5. **Processing Page 2**:
   - Page 2 is already in the frame, so this is a hit, and "H" is displayed.

The program will then print the frames after each page request, along with the final counts and ratios of hits and faults. 

This optimal page replacement minimizes page faults by always choosing to replace the page that won’t be used for the longest time, which reduces the overall number of faults.*/
