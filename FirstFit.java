import java.util.Scanner;

public class FirstFit {
	static void firstFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {
		int allocation[] = new int[n];
		for (int i = 0; i < allocation.length; i++) {
			allocation[i] = -1;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (blockSize[j] >= processSize[i]) {
					allocation[i] = j;
					blockSize[j] -= processSize[i];
					remblockSize[i] = blockSize[j];
					break;
				}
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
		int blockSize[] = new int[m];
		int remblockSize[] = new int[m];
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
		firstFit(blockSize, m, processSize, n, remblockSize);
		in.close();
	}
}
/*The provided Java program implements the **First-Fit Memory Allocation** algorithm. This algorithm assigns memory blocks to processes in a way that the first suitable block is allocated to each process. It attempts to find the smallest block that can fit each process, thereby helping to reduce memory fragmentation.

Here’s a breakdown of how the program works:

### Key Parts of the Program

1. **Getting Input**:
   - The program first asks for the number of memory blocks (available spaces in memory) and their sizes.
   - It then asks for the number of processes (tasks needing memory) and the sizes of memory each process requires.

2. **The First-Fit Allocation Logic**:
   - A `firstFit` method is defined to handle the memory allocation for each process.
   - The `allocation[]` array keeps track of which memory block each process has been allocated, initializing each element to `-1` (meaning "not allocated" initially).
   - The algorithm tries to allocate each process to the **first block** that is large enough to hold it:
     - If a block is found that can hold the process, the block size is reduced by the size of the process, and the process is marked as allocated to that block.
     - If a block is too small, it moves on to check the next block.

3. **Displaying the Allocation Result**:
   - After attempting to allocate memory for each process, the program displays:
     - **Process Number**: The ID of the process.
     - **Process Size**: The memory required by that process.
     - **Block Number**: The memory block assigned to this process.
     - **Remaining Block Size**: The size remaining in the allocated block after this process has taken up its memory.

4. **Main Method**:
   - The main method sets up the input and calls `firstFit` with the provided memory blocks and process sizes.
   - It collects the number of blocks, their sizes, the number of processes, and their memory needs from the user.

### Example of How It Works

Imagine you have:
- **3 memory blocks** with sizes `[100, 500, 200]`.
- **3 processes** with sizes `[90, 420, 200]`.

1. **Process 1** (size 90) checks blocks in order:
   - Block 1 (size 100) is large enough, so it's allocated to Process 1. Block 1's remaining size becomes 100 - 90 = 10.

2. **Process 2** (size 420) checks blocks in order:
   - Block 1 now has only 10 left, so it moves to Block 2.
   - Block 2 (size 500) is large enough, so it's allocated to Process 2. Block 2’s remaining size becomes 500 - 420 = 80.

3. **Process 3** (size 200) checks blocks in order:
   - Block 1 (size 10 left) is too small, so it moves to Block 2.
   - Block 2 now has only 80 left, so it moves to Block 3.
   - Block 3 (size 200) is exactly enough, so it’s allocated to Process 3. Block 3’s remaining size becomes 200 - 200 = 0.

The program would display the allocation table, showing which block each process got, along with any remaining block sizes.

### Output Example

```
Process No.	Process Size	Block No.	Remaining Block Size
1		90		1		10
2		420		2		80
3		200		3		0
```

This program helps in visualizing how memory is allocated using the First-Fit approach, showing memory usage and remaining space in each block after allocation.*/
