import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
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
    
    public static void main (String[] args){
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
    
    public static void UI(){
        Scanner scanner = new Scanner (System.in);
        Timer timer = new Timer();
        boolean exit = false;
        String fileName;
        
        int[] results;
        ArrayList<Integer> sorted = new ArrayList<>();
        while(!exit){
            int choice = 0;
            System.out.println("1. Select a file");
            System.out.println("2. Select a sort method");
            System.out.println("3. Check sort time");
            System.out.println("4. Check number of operations required for sort");
            System.out.println("5. Check both");
            System.out.println("6. Exit");
            try{
                choice = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e){
                System.out.println("Please enter an integer");
            }
            switch(choice){
                case 0:
                   System.out.println("Invalid choice");
                    break;
                
                case 1:
                    System.out.println("Enter the file Name");
                    loadData(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("1. Recursion sort");
                    System.out.println("2. Bubble sort");
                    System.out.println("3. Quick sort");
                    int sortChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch(sortChoice){
                        case 0:
                            System.out.println("Invalid choice");
                            break;
                        case 1:
                            Method recursion =  SortManager.class.getDeclaredMethod("quickSort", Integer[] array, Integer num, Integer length);
                            break;
                    }
                    break;
                case 3:
                    System.out.println("time");
                    timer.startTimer();
                    results = quickSort(numbers.clone(),0,numbers.length-1);
                    timer.endTimer();
                    System.out.println("It took " + timer);
                    break;
                case 4:
                    System.out.println("operations");
                    results = quickSortOpCount(numbers.clone(),0,numbers.length-1);
                    System.out.println("It took " + ops+" operations");
                    break;
                case 5:
                    System.out.println("both");
                    timer.startTimer();
                    results = quickSortOpCount(numbers.clone(),0,numbers.length-1);
                    timer.endTimer();
                    System.out.println("It took " + timer + " and " + ops + " operations.");
                    break;
                case 6:
                    exit = true;
                    break;
            }
            System.out.println("End of the loop");
        }
    }
    
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
     * My own sort method (a very slow bubble sort)
     */
    public static void recursionSort(int [] array){
        for(int i = 0; i < array.length-1; i++){
            int currNum = array[i];
            int nextNum = array[i+1];
            if(currNum > nextNum){
                array[i] = nextNum;
                array[i+1] = currNum;
                recursionSort(array);
            }
        }
    }
    
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
    
    public static int[] quickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
    
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
        return arr;
    }
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