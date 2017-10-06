/*
 * Ryan Flageolle Computer Science 2  
 *
 * September 29, 2017
 * 
 * This program allows a user to enter the file path of a plain text file that 
 * will be read into the program and individual words will be saved into an array
 * of strings, these strings will contain only letters of varying cases. Once the
 * array is populated it will be duplicated and sorted, one by the built-in Java
 * sort and the other will be sorted using Bubble sort. The program will print to
 * screen the time each sort takes to complete. 
 * 
 */
package sorttester;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author FlageMac
 */
public class HW4 {

    /**
     * @param args the command line arguments
     */
    
    String[] words;
    
    HW4(String path){
        words = populateArray(path);
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the file pathway: ");
        String local = in.next();
        HW4 current = new HW4(local);
        current.timeSorts();
    }
    
    // remove punctuation from a string
    public static String parseString(String x){
    
        Pattern p = Pattern.compile("[\\p{Punct}]");
        Matcher remove = p.matcher(x);
        return remove.replaceAll("");
    }
    
    // singly add every word from a txt file into an array of Strings
    public static String[] populateArray(String path) {
        ArrayList<String> hold = new ArrayList();
        Pattern p = Pattern.compile("$\\s*^");
        
        try {
            File doc = new File(path);
            
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            
            while ((line = in.readLine()) != null){
                Scanner o = new Scanner(line);
                
                while (o.hasNext()){
                    String str = o.next();
                    if (!str.equals("[\\s]") || (!str.equals("[\\d]"))){
                        String t = parseString(str);
                        if (!t.equals("")) {
                            hold.add(t);
                        }
                    }
                }
            }
            
        } catch(IOException e){
            System.out.print(e);
        }
        
        String[] rtn = new String[hold.size()];
        int counter = 0;
        
        for (String s : hold){
            rtn[counter] = s;
            counter++;
        }
        
        return rtn;
    }
    
    // Taking an Array of Strings this method times the duration of the standard java sort method
    public static String[] javaSort(String[] data) {
        long startClock = System.currentTimeMillis();
        long startCPU = System.nanoTime();
        
        Arrays.sort(data, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        
        long stopClock = System.currentTimeMillis();
        long stopCPU = System.nanoTime();
        
        String[] times = new String[2];
        
        long clockTime = stopClock - startClock;
        long timeCpu = stopCPU - startCPU;      
        
        times[0] = convert(clockTime);
        times[1] = String.format("%,d nano seconds", timeCpu);
        
        return times;
    }
    
    // Taking an Array of Strings this method times the duration of the my interpretation of Bubble Sort
    public static String[] bubbleSort(String[] data) {
        long startClock = System.currentTimeMillis();
        long startCPU = System.nanoTime();
        
        for (int i = 0; i < data.length - 1; i ++){
            for (int j = 0; j < data.length - 1 - i; j++) {
                int check = data[j].compareTo(data[j + 1]);
                if ( check > 0) {
                    String temp = data[j];
                    data[j] = data[j + 1];
                    data[j+1] = temp;
                }
            }
        }
        
        long stopClock = System.currentTimeMillis();
        long stopCPU = System.nanoTime();
        
        String[] times = new String[2];
        
        long clockTime = stopClock - startClock;
        long timeCpu = stopCPU - startCPU;      
        
        times[0] = convert(clockTime);
        times[1] = String.format("%,d nano seconds", timeCpu);
        
//        for (String s : data){
//            System.out.println(s);
//        }
        
        return times;
        
    }
    
    // The guts of the user interface prints to screen the durations found in the 
    // bubbleSort() method and the javaSort() methods
    public void timeSorts(){
        
        String[] bubbleTimes = bubbleSort(this.words);
        
        String[] javaTimes = javaSort(this.words);
        
        String rtn = String.format("Filename: %s \nNumber of words: %d \nWall Clock: \n     Bubble Sort: %s"
                                    + "\n     Internal Sort: %s \n\nCPU time: \n     Bubble Sort: %s\n     Internal Sort %s", 
                                    "/Users/FlageMac/NetBeansProjects/SortTester/test/HW4.txt", this.words.length, bubbleTimes[0], javaTimes[0], 
                                    bubbleTimes[1], javaTimes[1]);
        System.out.println(rtn);
        
        try {
            File HW4Output = new File("HW4Output.txt");
            FileWriter out = new FileWriter(HW4Output);
            out.append(rtn);
        } catch (IOException e) {
            System.out.print("Something went wrong Ke-mo sah-bee");
        }
    }
    
    // turns the milliseconds into a more readable format... me no good at read big numbers
    public static String convert(long x) {
                
        int mins = (int)x / 60000;
        int secs = (int)(x - (mins * 60000)) / 1000;
        int millisecs = (int)(x - (mins * 60000) - (secs * 1000));
        
        String str = mins + " mins " + secs + " secondss " + millisecs + " milliseconds";
        
        return str;
    }
    
}
