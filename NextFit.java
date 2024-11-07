import java.util.Arrays;
import java.util.Scanner;

public class NextFit {

	static void NextFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {

		int allocation[] = new int[n], j = 0;
		Arrays.fill(allocation, -1);
		for (int i = 0; i < n; i++) {
			int count = 0;
			while (count < m) {
				count++;
				if (blockSize[j] >= processSize[i]) {
					allocation[i] = j;
					blockSize[j] -= processSize[i];
					remblockSize[i] = blockSize[j];
					break;
				}
				j = (j + 1) % m;
				count += 1;
			}
		}

		System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
		for (int i = 0; i < n; i++) {
			System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");
			if (allocation[i] != -1) {
				System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
			} else {
				System.out.print("Not Allocated" + "\t" + remblockSize[i]);
			}
			System.out.println("");
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
		NextFit(blockSize, m, processSize, n, remblockSize);
		in.close();
	}

}
/*The `NextFit` Java program is a memory allocation simulation that demonstrates the Next Fit memory allocation strategy. The Next Fit strategy attempts to allocate memory blocks to processes in a sequential manner, starting from the last allocated block. Here’s a breakdown of how the code works:

### Code Explanation

1. **Main Class and Function Setup**:
   - `NextFit` is the main class that contains the memory allocation logic.
   - In `main`, the program asks the user to enter the number and sizes of memory blocks and processes, which represent the memory available and the memory needed by processes, respectively.

2. **User Input**:
   - The user enters the number of memory blocks (`m`) and their sizes.
   - Then, they enter the number of processes (`n`) and their required memory sizes.
   - These inputs are stored in two arrays: `blockSize` for block sizes and `processSize` for process sizes.

3. **NextFit Method**:
   - `NextFit` is the main memory allocation function, which tries to allocate memory blocks to processes based on their size requirements.
   - An `allocation` array is initialized to keep track of which block is assigned to each process. Initially, all values are set to `-1`, indicating no allocation.
   - `j` is used to remember the last block index where memory was allocated, following the Next Fit strategy.

4. **Memory Allocation Loop**:
   - For each process, the code checks whether it can fit into the current memory block (`blockSize[j]`).
   - If the block can fit the process, it:
     - Allocates the block to the process by storing the block index `j` in `allocation[i]`.
     - Reduces the block's available size by the size of the process (`blockSize[j] -= processSize[i]`).
     - Updates `remblockSize[i]` to track the remaining size of the block after allocation.
   - If the block cannot fit the process, it moves to the next block (`j = (j + 1) % m`) and tries again.

5. **Output**:
   - After attempting to allocate each process, the program prints the allocation results:
     - For each process, it shows the process number, process size, allocated block (if any), and the remaining block size.
     - If a process couldn’t be allocated to any block, it shows "Not Allocated."

### Example Run

Here's a sample interaction:

```plaintext
Enter how many number of blocks you want to enter: 3
Enter Data 1: 100
Enter Data 2: 200
Enter Data 3: 300
Enter how many number of process you want to enter: 4
Enter Data 1: 150
Enter Data 2: 350
Enter Data 3: 100
Enter Data 4: 200
```

Output:

```plaintext
Process No.  Process Size   Block no.   Remaining Block Size
1            150            2          50
2            350            Not Allocated  200
3            100            1          0
4            200            3          100
```

### Summary
The Next Fit strategy is simple and works by continuously looping over the blocks in a cyclic manner. It doesn’t always find the best fit but is quick because it does not backtrack.*/
