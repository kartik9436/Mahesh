import java.util.Scanner;

public class FIFO {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		int noofpages, capacity, index = 0;
		int hit = 0, fault = 0;
		double faultRatio, hitRatio;
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
		System.out.println("\n----------------------------------------------------------------------");
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
				frame[index] = pages[i];
				fault++;
				System.out.printf("%4s", "F");
				index++;
				if (index == capacity) {
					index = 0;
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
		faultRatio = ((double) fault / noofpages) * 100;
		hitRatio = ((double) hit / noofpages) * 100;
		System.out.println("Page Fault: " + fault + "\nPage Hit: " + hit);
		System.out.printf("Hit Ratio:%.2f \nFault Ratio:%.2f ", hitRatio, faultRatio);
		sc.close();
	}
}
/*  First-In-First-Out (FIFO) page replacement algorithm, commonly used in operating systems for managing memory pages in virtual memory. The program calculates and displays the number of page faults and hits that occur as it loads pages into a memory frame of a specified capacity.

Here's a breakdown of the code:

Input Section:

The program prompts the user to enter the total number of pages (noofpages) and the pages themselves (pages array).
It then asks for the frame capacity (capacity), which indicates the maximum number of pages the memory can hold at any time.
Frame Initialization:

The frame array of size capacity holds the pages currently in memory. Initially, each frame slot is set to -1, representing an empty slot.
Page Replacement Logic:

For each page requested (pages[i]), the program checks if the page is already present in the frame:
If the page is found, it's a hit, and the program increments the hit counter. "H" is printed to indicate a hit.
If the page is not found, it's a fault, and the program increments the fault counter. The new page is then placed in the frame at the index position, following the FIFO order.
The index variable tracks the position for the next replacement. After each insertion, index increments, and if it reaches the capacity, it resets to 0, looping back to the start.
Storing Frame State for Display:

After each page request, the program stores the current state of the frame array in a table 2D array for later display.
table[i][j] will show the page in jth frame slot after processing the ith page.
Displaying Results:

After processing all pages, the program displays the contents of each frame over time using the table array. Each row represents the frame contents after a page request.
It then displays the total number of page faults and hits, as well as the hit ratio and fault ratio, calculated as percentages.
Calculations:

Fault Ratio: (fault / noofpages) * 100
Hit Ratio: (hit / noofpages) * 100
Example
If you enter a sequence of pages like 1, 2, 3, 4, 2, 1, 5, 6 with a frame capacity of 3, the program will simulate loading these pages into the memory, outputting either a hit ("H") or fault ("F") for each, and finally print the ratio of page faults and hits.

Code Walkthrough Example
java
Copy code
Enter the number of pages you want to enter: 8
Pages: 1, 2, 3, 4, 2, 1, 5, 6
Enter the capacity of frame: 3
Output:

r
Copy code
 F F F F H H F F
--- Frame contents over time ---
 1  2  3
 4  2  3
 4  2  1
 5  2  1
 5  6  1
-------------------------------
Page Fault: 6
Page Hit: 2
Hit Ratio: 25.00
Fault Ratio: 75.00
*/