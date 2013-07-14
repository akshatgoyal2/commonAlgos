import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class InversionCalc {
	private static Long[] lines = null;

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
	
	public static void solve(String[] args) throws IOException {
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
        //List<Long> lines = new ArrayList<Long>();
		lines = new Long[100000];

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
		int i=0;
        try {
            String line = null;
			//int count = 0;
            while (( line = input.readLine()) != null){
                //lines.add(Long.parseLong(line));
				lines[i++] = Long.parseLong(line);
				//count++;
            }
        }
        finally {
            input.close();
        }
        
		long result = mergeSortInv(0,99999);
		
        /*for (int ii=0;ii<10;ii++){
			System.out.println(lines[ii]);
		}*/	
		System.out.println(result);	
    }
	
	public static void mergeSort(int startIndex, int endIndex){
		//System.out.println("starti: "+startIndex+" endi:"+ endIndex);
		if (startIndex >= endIndex){
			return;
		}
		int start = startIndex;
		int mid = (endIndex+startIndex)/2;
		int end = endIndex;
		//System.out.println("start: "+start+"mid "+mid+" end:"+ end);
		mergeSort(start, mid);
		mergeSort(mid+1, end);
		merge(start, end);
	}
	
	public static long mergeSortInv(int startIndex, int endIndex){
		//System.out.println("starti: "+startIndex+" endi:"+ endIndex);
		if (startIndex >= endIndex){
			return 0;
		}
		int start = startIndex;
		int mid = (endIndex+startIndex)/2;
		int end = endIndex;
		//System.out.println("start: "+start+"mid "+mid+" end:"+ end);
		long s1 = mergeSortInv(start, mid);
		long s2 = mergeSortInv(mid+1, end);
		long s3 = mergeInv(start, end);
		return s1+s2+s3;
	}
	
	public static void merge(int startIndex, int endIndex){
		System.out.println("startj: "+startIndex+" endj:"+ endIndex);
		Long[] arr = new Long[endIndex-startIndex+1];
		int i=startIndex;
		int j=((endIndex+startIndex)/2)+1;
		int k=0;
		for(k=0; k<arr.length; k++){
			if (i == ((endIndex+startIndex)/2)+1 || j> endIndex) break;
			if (lines[i] < lines[j]){
				arr[k] = lines[i];
				i++;
			} else {
				arr[k] = lines[j];
				j++;
			}
			
		}
		if (i == ((endIndex+startIndex)/2)+1){
				//Copy all of right array
				while(j<= endIndex){
					arr[k] = lines[j];
					k++;
					j++;
				}
		} else if (j> endIndex){
				while(i<= ((endIndex+startIndex)/2)){
					arr[k] = lines[i];
					k++;
					i++;
				}
		}
		for (int s=0;s<arr.length;s++){
			lines[startIndex] = arr[s];
			startIndex++;
		}
		System.out.println("sorted sub array"+Arrays.toString(arr));
	}	
	
	public static long mergeInv(int startIndex, int endIndex){
		//System.out.println("startj: "+startIndex+" endj:"+ endIndex);
		Long[] arr = new Long[endIndex-startIndex+1];
		int i=startIndex;
		int j=((endIndex+startIndex)/2)+1;
		int k=0;
		long remElements = (((endIndex+startIndex)/2) - startIndex) +1;  //Number of elements in first array
		//System.out.println(remElements);
		int numInv = 0;
		for(k=0; k<arr.length; k++){
			if (i == ((endIndex+startIndex)/2)+1 || j> endIndex) break;
			if (lines[i] < lines[j]){
				arr[k] = lines[i];
				i++;
				remElements = remElements == 0? 0:remElements-1;
			} else {
				arr[k] = lines[j];
				j++;
				numInv += remElements;
			}
			
		}
		if (i == ((endIndex+startIndex)/2)+1){
				//Copy all of right array
				while(j<= endIndex){
					arr[k] = lines[j];
					k++;
					j++;
				}
		} else if (j> endIndex){
				while(i<= ((endIndex+startIndex)/2)){
					arr[k] = lines[i];
					k++;
					i++;
				}
		}
		for (int s=0;s<arr.length;s++){
			lines[startIndex] = arr[s];
			startIndex++;
		}
		//System.out.println("sorted sub array"+Arrays.toString(arr));
		return numInv;
		
	}
}