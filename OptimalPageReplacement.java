import java.util.Scanner;

public class OptimalPageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int noofpages, capacity, ptr = 0, hit = 0, fault = 0;
        boolean isFull = false;
        double hitRatio, faultRatio;
        System.out.print("Enter the number of pages you want to enter: ");
        noofpages = sc.nextInt();
        int pages[] = new int[noofpages];
        for (int i = 0; i < noofpages; i++) {
            pages[i] = sc.nextInt();
        }
        System.out.print("Enter the capacity of frame: ");
        capacity = sc.nextInt();
        int frame[] = new int[capacity];
        int table[][] = new int[noofpages][capacity];
        for (int i = 0; i < capacity; i++) {
            frame[i] = -1;
        }
        System.out.println("----------------------------------------------------------------------");
        for (int i = 0; i < noofpages; i++) {
            int search = -1;
            for (int j = 0; j < capacity; j++) {
                if (frame[j] == pages[i]) {
                    search = j;
                    hit++;
                    System.out.printf("%4s", "H");
                    break;
                }
            }
            if (search == -1) {
                if (isFull) {
                    int[] index = new int[capacity];
                    boolean[] index_flag = new boolean[capacity];
                    for (int j = i + 1; j < noofpages; j++) {
                        for (int k = 0; k < capacity; k++) {
                            if ((pages[j] == frame[k]) &&
                                    (!index_flag[k])) {
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int max = index[0];
                    ptr = 0;
                    if (max == 0)
                        max = 200;
                    for (int j = 0; j < capacity; j++) {
                        if (index[j] == 0)
                            index[j] = 200;
                        if (index[j] > max) {
                            max = index[j];
                            ptr = j;
                        }
                    }
                }
                frame[ptr] = pages[i];
                fault++;
                System.out.printf("%4s", "F");
                if (!isFull) {
                    ptr++;
                    if (ptr == capacity) {
                        ptr = 0;
                        isFull = true;
                    }
                }
            }
            System.arraycopy(frame, 0, table[i], 0, capacity);
        }
        System.out.println("\n----------------------------------------------------------------------");
        for (int i = 0; i < capacity; i++) {
            for (int j = 0; j < noofpages; j++)
                System.out.printf("%3d ", table[j][i]);
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------");
        hitRatio = ((double) hit / noofpages) * 100;
        faultRatio = ((double) fault / noofpages) * 100;
        System.out.println("Page Fault: " + fault + "\nPage Hit: " + hit);
        System.out.printf("Hit Ratio:%.2f \nFault Ratio:%.2f ", hitRatio, faultRatio);
        sc.close();
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
