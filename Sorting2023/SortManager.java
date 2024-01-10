import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * Write a description of class SortManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SortManager
{
    private static int size;
    private static Scanner scan;
    private static Integer[] fancyNumbers;
    private static int[] numbers;
    private static long ops;
    private static ArrayList<Integer> nums = new ArrayList<>();
    private static ArrayList<Integer> clonedNums = new ArrayList<>();
    
    public static void oldTester (){
        String fileName = "250k_source.txt";
        Timer timer = new Timer();
        size = 0;

        // start the stopwatch 
        timer.startTimer();

        // Load the data
        loadData (fileName);
        
        // run our sort
        int[] results = bubbleSortOpCount(numbers.clone());

        // stop the timer
        timer.endTimer();

        // Check results, and output report to the screen
        checkResults(results, true);
        
        // Report on timer
        System.out.println("It took " + timer + " and " + ops + " operations.");

    }
    /**
     *  Main loop to test out different sorting algorithms
     *  
     *  @param args             Filled in by java 
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Timer timer = new Timer();
        boolean exit = false;
        String fileName;

        int[] results;
        ArrayList<Integer> sorted = new ArrayList<>();
        
        int choice = 0;
        int sortChoice = 0;
        int outputChoice = 0;
        int a = 1;
        
        // Loop keeps running as long as user does not select exit
        while(!exit){
            System.out.println("1. Select a file");
            System.out.println("2. Select a sort method");
            System.out.println("3. Exit");
            System.out.println("");

            choice = sc.nextInt();
            sc.nextLine();
            
            //Ask user what they want to do
            switch(choice){
                case 0:
                    System.out.println("Invalid choice");
                    break;
                case 1:
                    System.out.println("Enter the file name:");
                    loadData(sc.nextLine());
                    System.out.println("");
                    break;
                    
                // If sort, ask the user which sort they want to use
                case 2:
                    System.out.println("1. Recursion sort");
                    System.out.println("2. Bubble sort");
                    System.out.println("3. Quick sort");
                    System.out.println("");

                    sortChoice = sc.nextInt();
                    sc.nextLine();

                    switch(sortChoice){
                        case 0:
                            System.out.println("Invalid choice");
                            break;
                            
                        
                        case 1:
                             System.out.println("1. Check sort time");
                             System.out.println("2. Check number of operations required for sort");
                             System.out.println("3. Check both");
                             System.out.println("");
                            
                             // Ask the user if they want to count sort time, number of operations or both for recursion sort
                             outputChoice = sc.nextInt();
                             sc.nextLine();

                             switch(outputChoice){
                                case 0:
                                    System.out.println("Invalid choice");
                                    break;
                                case 1:
                                    timer.startTimer();
                                    results = recursionSort(numbers.clone());
                                    timer.endTimer();
                                    System.out.println("It took " + timer);
                                    System.out.println("");
                                    break;
                                case 2:
                                    results = recursionSortOpCount(numbers.clone());
                                    System.out.println("It took " + ops+" operations");
                                    System.out.println("");
                                    break;
                                case 3:
                                    timer.startTimer();
                                    results = recursionSortOpCount(numbers.clone());
                                    timer.endTimer();
                                    System.out.println("It took " + timer + " and " + ops + " operations.");
                                    System.out.println("");
                                    break;
                             }
                             break;
                        case 2:
                             System.out.println("1. Check sort time");
                             System.out.println("2. Check number of operations required for sort");
                             System.out.println("3. Check both");
                             System.out.println("");
                             
                             // Ask the user if they want to count sort time, number of operations or both for bubble sort
                             outputChoice = sc.nextInt();
                             sc.nextLine();
                             
                             switch(outputChoice){
                                case 0:
                                    System.out.println("Invalid choice");
                                    break;
                                case 1:
                                    timer.startTimer();
                                    results = bubbleSort(numbers.clone());
                                    timer.endTimer();
                                    System.out.println("It took " + timer);
                                    System.out.println("");
                                    break;
                                case 2:
                                    results = bubbleSortOpCount(numbers.clone());
                                    System.out.println("It took " + ops+" operations");
                                    System.out.println("");
                                    break;
                                case 3:
                                    timer.startTimer();
                                    results = bubbleSortOpCount(numbers.clone());
                                    timer.endTimer();
                                    System.out.println("It took " + timer + " and " + ops + " operations.");
                                    System.out.println("");
                                    break;
                             }
                             break;
                        case 3:
                             System.out.println("1. Check sort time");
                             System.out.println("2. Check number of operations required for sort");
                             System.out.println("3. Check both");
                             System.out.println("");
                             
                             // Ask the user if they want to count sort time, number of operations or both for quick sort
                             outputChoice = sc.nextInt();
                             sc.nextLine();
                             switch(outputChoice){
                                case 0:
                                    System.out.println("Invalid choice");
                                    break;
                                case 1:
                                    timer.startTimer();
                                    results = quickSort(numbers.clone(),0,numbers.length-1);
                                    timer.endTimer();
                                    System.out.println("It took " + timer);
                                    System.out.println("");
                                    break;
                                case 2:
                                    results = quickSortOpCount(numbers.clone(),0,numbers.length-1);
                                    System.out.println("It took " + ops+" operations");
                                    System.out.println("");
                                    break;
                                case 3:
                                    timer.startTimer();
                                    results = quickSortOpCount(numbers.clone(),0,numbers.length-1);
                                    timer.endTimer();
                                    System.out.println("It took " + timer + " and " + ops + " operations.");
                                    System.out.println("");
                                    break;
                             }
                             break;
                    }
                    break;
                
                //Completes the condition for while loop, allowing the user to exit
                case 3:
                    exit = true;
                    break;
            }
        }

    }
    
    /**
     * Method to load file containing a list of numbers and input them into an array
     * 
     * @param fileName          String of the file
     */
    private static void loadData (String fileName){
        ArrayList<String> lines = new ArrayList<String>();
        System.out.println("Attempting to read integers from: " + fileName);

        try{
            scan = new Scanner (new File (fileName));            
        } catch (FileNotFoundException e){
            System.out.println("File not found.");

        }
        while (scan.hasNext()){
            lines.add(scan.nextLine());
        }  

        // populate our array
        
        numbers = new int[lines.size()];
        for (int i = 0; i < numbers.length; i++){
            Integer value = Integer.parseInt(lines.get(i));
            numbers[i] = value;
            nums.add(value);
            clonedNums.add(value);
        }
    }
    
    /**
     * This is an example of how to turn a sort algorithm
     * into an "opCount" method - it uses a rough count
     * of overall operations to provide some interesting
     * information - it's non-scientific, but when you compare
     * the results you can get proportionate and interesting
     * results.
     */
    public static int[] bubbleSortOpCount (int[] num)
    {
        ops = 0;
        ops++; // int i = 0;
        for (int i = 0; i < num.length; i++) {
            ops++; // i < num.length comparison
            ops++; // int x = 1
            for (int x = 1; x < num.length - i; x++) {
                ops++; // x < num.length - i
                ops++; // if statmement
                if (num[x - 1] > num[x]) {
                    int temp = num[x - 1];
                    num[x - 1] = num[x];
                    num[x] = temp;
                    ops += 5;
                }
                ops++; // x++
            }
            
            if (i % 10000 == 0 && i > 0){
                System.out.println ((((double)i / (double)num.length)*100) + " percent complete."); 
            }
            ops++; // i++
        }

        return num;
    }   

    public static int[] bubbleSort (int[] num)
    {

        for (int i = 0; i < num.length; i++) {
            for (int x = 1; x < num.length - i; x++) {
                if (num[x - 1] > num[x]) {
                    int temp = num[x - 1];
                    num[x - 1] = num[x];
                    num[x] = temp;

                }
            }
            if (i % 10000 == 0 && i > 0){
                System.out.println ((((double)i / (double)num.length)*100) + " percent complete."); 
            }
        }

        return num;
    }   
    
    /**
     * My own sort method, runs through a list, each time it detects the element ahead to be smaller than current element
     * it will switch places and call on itself again.
     * 
     * Warning, large data sets (1000+ values) will cause the recursive method to stack overflow
     * 
     * @param array                 The unsorted array of numbers
     * @param int []
     */
    public static int [] recursionSort(int [] array){
        for(int i = 0; i < array.length-1; i++){
            int currNum = array[i];
            int nextNum = array[i+1];
            if(currNum > nextNum){
                array[i] = nextNum;
                array[i+1] = currNum;
                recursionSort(array);
            }
        }
        return array;
    }
    
    /**
     * My own sort method with an operation counter.
     * 
     * Warning, large data sets (1000+ values) will cause the recursive method to stack overflow
     * 
     * @param array                 The unsorted array of numbers
     * @return int []
     */
    public static int[] recursionSortOpCount(int [] array){
        ops = 0;
        ops++; // set i = 0;
        for(int i = 0; i < array.length-1; i++){
            ops++; // check i < array length
            int currNum = array[i];
            int nextNum = array[i+1];
            
            ops++; // check if currNum > next Num
            if(currNum > nextNum){
                array[i] = nextNum;
                array[i+1] = currNum;
                recursionSortOpCount(array);
            }
        }
        return array;
    }
    
    /**
     * Quick sort algorithm
     * 
     * @param arr           Unsorted array of numbers
     * @param begin         Front element
     * @param end           Last element
     * @return int[]
     * 
     */
    public static int[] quickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
    
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
        return arr;
    }
    /**
     * A helper method for quick sort to break apart the array in order to more efficently swap values
     * 
     * @param begin         Front element
     * @param end           Last element
     * @return int
     */
    private static int partition(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);
    
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
    
                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
    
        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
    
        return i+1;
    }
    
    /**
     * Quick sort with an operation counter
     * 
     * @param arr           Unsorted array of numbers
     * @param begin         Front element
     * @param end           Last element
     * @return int[]
     */
    public static int[] quickSortOpCount(int arr[], int begin, int end) {
        ops++;
        if (begin < end) {
            int partitionIndex = partitionOpCount(arr, begin, end);
            
            ops+=2;
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
        return arr;
    }
    
    /**
     * A helper method for quick sort with an operation counter
     * 
     * @param begin         Front element
     * @param end           Last element
     * @return int
     */
    private static int partitionOpCount(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);
        
        ops++;
        for (int j = begin; j < end; j++) {
            ops++;
            if (arr[j] <= pivot) {
                i++;
    
                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        
        ops+=3;
        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
        
        ops++;
        return i+1;
    }
    
    /**
     * Method to check that the list is correctly sorted in ascending order
     * 
     * @param theArray          The array being tested
     * @boolean report          Check if results should be printed
     * @return boolean
     */
    public static boolean checkResults (int[] theArray, boolean report)
    {
        System.out.println("Checking Validity");
        boolean stillValid = true;
        int counter = 0;
        while (stillValid && counter < theArray.length - 1)
        {
            if (theArray[counter] > theArray[counter + 1])
            {
                stillValid = false;
            }
            counter++;
        }
        if (report)
        {
            if (stillValid)
            {
                System.out.println("Checked " + theArray.length + " values. Sort is valid");
            }
            else
            {
                System.out.println("Checked " + theArray.length + " values. Found error at " + counter);
            }
        }

        return stillValid;
    }
}