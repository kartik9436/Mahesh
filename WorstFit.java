import java.util.Scanner;

public class WorstFit {

	static void worstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
		int allocation[] = new int[n];
		for (int i = 0; i < allocation.length; i++) {
			allocation[i] = -1;
		}
		for (int i = 0; i < n; i++) {
			int wstIdx = -1;
			for (int j = 0; j < m; j++) {
				if (blockSize[j] >= processSize[i]) {
					if (wstIdx == -1)
						wstIdx = j;
					else if (blockSize[wstIdx] < blockSize[j])
						wstIdx = j;
				}
			}
			if (wstIdx != -1) {
				allocation[i] = wstIdx;
				blockSize[wstIdx] -= processSize[i];
				remblockSize[i] = blockSize[wstIdx];
			}
		}

		System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
		for (int i = 0; i < n; i++) {
			System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
			if (allocation[i] != -1)
				System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
			else
				System.out.print("Not Allocated" + "\t" + remblockSize[i]);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int m, n, num;
		Scanner in = new Scanner(System.in);
		System.out.print("Enter how many number of blocks you want to enter:");
		m = in.nextInt();
		int remblockSize[] = new int[m];
		int blockSize[] = new int[m];
		for (int i = 0; i < m; i++) {
			System.out.print("Enter Data " + (i + 1) + ":");
			num = in.nextInt();
			blockSize[i] = num;
		}
		System.out.print("Enter how many number of process you want to enter:");
		n = in.nextInt();
		int processSize[] = new int[n];
		for (int i = 0; i < n; i++) {
			System.out.print("Enter Data " + (i + 1) + ":");
			num = in.nextInt();
			processSize[i] = num;
		}
		worstFit(blockSize, m, processSize, n, remblockSize);
		in.close();
	}

}
/*
 * This Java program implements the Worst Fit memory allocation strategy, which
 * assigns processes to memory blocks based on the largest available block.
 * Let’s walk through each part of the code and how it works.
 * 
 * Key Concepts
 * Memory Blocks: Fixed-size chunks of memory where processes can be allocated.
 * Processes: Each process requires a certain amount of memory to be allocated
 * to a block.
 * How the Worst Fit Strategy Works
 * Input Phase:
 * 
 * The user enters the number and sizes of memory blocks.
 * Then, the user enters the number of processes and their respective sizes.
 * Allocation Process:
 * 
 * For each process, the program finds the largest memory block that can
 * accommodate the process (i.e., "worst fit").
 * This is achieved by:
 * Checking each block to see if it is large enough to hold the current process.
 * If multiple blocks can fit the process, it picks the block with the largest
 * size, ensuring that smaller blocks remain available for potentially smaller
 * processes.
 * Once the block is selected, the program:
 * Allocates it to the process.
 * Reduces the block size by the process size to reflect the remaining memory in
 * that block.
 * Stores this remaining size in the remblockSize array for output.
 * Output Phase:
 * 
 * The program displays the results for each process:
 * Process number
 * Process size
 * Block number assigned (if allocated)
 * Remaining size of the block after allocation
 * If a process cannot be allocated to any block, it is marked as
 * "Not Allocated."
 * Example Walkthrough
 * Let's say you have 3 memory blocks and 2 processes:
 * 
 * Input:
 * Memory Blocks: 100, 500, 200
 * Processes: 150, 250
 * Execution:
 * Process 1 (150): Fits in the largest block (500), leaving 350 remaining.
 * Process 2 (250): Now fits in the largest remaining block (350 from the
 * previous allocation), leaving 100 remaining.
 * Code Explanation
 * worstFit function: Implements the worst-fit algorithm by finding the largest
 * suitable block for each process.
 * allocation array: Stores the block index allocated to each process (or -1 if
 * no block can accommodate the process).
 * remblockSize: Keeps track of the remaining size in each block after
 * allocation.
 */