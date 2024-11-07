import java.util.Scanner;

public class BestFit {
	static void bestFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
		int allocation[] = new int[n];
		for (int i = 0; i < allocation.length; i++) {
			allocation[i] = -1;
		}
		for (int i = 0; i < n; i++) {
			int bestIdx = -1;
			for (int j = 0; j < m; j++) {
				if (blockSize[j] >= processSize[i]) {
					if (bestIdx == -1)
						bestIdx = j;
					else if (blockSize[bestIdx] > blockSize[j])
						bestIdx = j;
				}
			}
			if (bestIdx != -1) {
				allocation[i] = bestIdx;
				blockSize[bestIdx] -= processSize[i];
				remblockSize[i] = blockSize[bestIdx];
			}
		}
		System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
		for (int i = 0; i < n; i++) {
			System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
			if (allocation[i] != -1) {
				System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
			} else {
				System.out.print("Not Allocated" + "\t" + remblockSize[i]);
			}
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
		bestFit(blockSize, m, processSize, n, remblockSize);
		in.close();
	}
}
/*
 * This Java program implements the Best Fit memory allocation strategy, which
 * is used to assign processes to memory blocks efficiently. Here’s how it
 * works, step-by-step:
 * 
 * Key Concepts
 * Memory Blocks: These are fixed-sized memory units where processes can be
 * stored.
 * Processes: Each process has a certain memory requirement (size) that needs to
 * be allocated to a block that fits it best.
 * How the Best Fit Strategy Works
 * Input Phase:
 * 
 * First, the user is asked to enter the number and sizes of memory blocks
 * (e.g., 100, 500, 200, etc.).
 * Then, the user enters the number of processes and their sizes (e.g., 150,
 * 350, etc.).
 * Allocation Process:
 * 
 * For each process, the program looks for the smallest block that can fit the
 * process (best fit). This is achieved by:
 * Checking each block to see if it can accommodate the current process.
 * If a block can fit the process, it then checks if it's the smallest suitable
 * block found so far. This ensures that each process is placed in the block
 * that wastes the least memory.
 * Once a suitable block is found, the program allocates it to the process,
 * reduces the block size by the size of the process (to reflect the remaining
 * space), and saves this remaining space.
 * Output Phase:
 * 
 * The program prints the allocation result for each process, including:
 * The process number.
 * The size of the process.
 * The block number assigned to the process (if any).
 * The remaining size of the block after allocation.
 * If a process cannot be allocated to any block, it is marked as
 * "Not Allocated."
 * Example Walkthrough
 * Let's say you have 4 memory blocks and 3 processes:
 * 
 * Input:
 * Memory Blocks: 100, 500, 200, 300
 * Processes: 212, 417, 112
 * Output:
 * The program will attempt to allocate each process to the smallest possible
 * block:
 * 
 * Process 1 (212): Fits in the 300 block (leaving 88 remaining).
 * Process 2 (417): Fits in the 500 block (leaving 83 remaining).
 * Process 3 (112): Fits in the 200 block (leaving 88 remaining).
 * The program would display each process's allocation, including remaining
 * space in each block.
 * 
 * Code Explanation
 * bestFit function: Implements the best-fit allocation by finding the smallest
 * suitable block for each process.
 * allocation array: Stores the block number assigned to each process (or -1 if
 * no block is suitable).
 * remblockSize: Keeps track of the remaining size in each block after
 * allocation.
 */