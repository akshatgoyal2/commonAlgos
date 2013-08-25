import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class TwoSum {
	private static Long[] lines = null;
    private static Set<Long> values = new HashSet<Long>();
    private static Set<Long> initialSums = new HashSet<Long>();
    private static Set<Long> finalSums = new HashSet<Long>();
	 /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void solve(String args[]) throws IOException{
        String fileName = null;
        
        // get the temp file name
        for(String arg : args){
            if(arg.startsWith("-file=")){
                fileName = arg.substring(6);
            } 
        }
        if(fileName == null)
            return;

             // read the lines out of the file
        List<String> lines = new ArrayList<String>();
        //lines = new Long[100000];

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        
        try {
            String line = null;
            //int count = 0;
            while (( line = input.readLine()) != null){
                values.add(Long.parseLong(line.trim())); 
                //count++;
            }
        }
        finally {
            input.close();
        }

        //Initialize initialSums
        for (long i=-10000;i<=10000;i++) {
            initialSums.add(i);
        }
        
        //getMinPath(lines);
        System.out.println(initialSums.size());
        calculateSums();
        System.out.println(finalSums.size());
    }

    private static void calculateSums() {
        for (long i: values) {
            Set<Long> hs = new HashSet<Long>();
            hs.addAll(initialSums);
            for (long j: hs) {
                if (values.contains(j-i) && j-i != i) {
                    finalSums.add(j);
                    initialSums.remove(j);
                    System.out.println(j);
                }
            }
        }
    }  
    
}