import java.io.*;

public class quicksort{
	
	private static Long[] lines = null;

	private static Long comparisons = 0L;
	private static String type="first";

	public static void main(String[] args){
		try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static void solve(String[] args) throws IOException{
		String fileName = null;
        
        // get the temp file name
        for(String arg : args){
            if(arg.startsWith("-file=")){
                fileName = arg.substring(6);
            } 
            if(arg.startsWith("-type=")){
            	type = arg.substring(6);
            }
        }
        if(fileName == null)
            return;
        
        // read the lines out of the file
        int count = 10000;
        lines = new Long[count];

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
		int i=0;
        try {
            String line = null;
            while (( line = input.readLine()) != null && i<count){
                lines[i++] = Long.parseLong(line);
            }
        }
        finally {
            input.close();
        }

        sort(0,count-1);
        /*for(int k=0;k<10000;k++){
        	System.out.println(lines[k]);
        }*/
        System.out.println(comparisons);
	}

	public static void sort(int startIndex, int endIndex){
		//System.out.println(startIndex);
		//	System.out.println(endIndex);
		if(startIndex>=endIndex) return;
		comparisons += (endIndex - startIndex);
		processPivot(startIndex, endIndex);
		int separator = doPartition(startIndex, endIndex);
		sort(startIndex,separator-1);
		sort(separator+1,endIndex);	
	}

	public static int doPartition(int startIndex,int endIndex){
		Long pivot = lines[startIndex];
		int i = startIndex+1;
		for (int j=startIndex+1;j<=endIndex;j++){
			if (lines[j]<pivot){
				Long tmp = lines[j];
				lines[j] = lines[i];
				lines[i] = tmp;
				i++;
			}
		}	
		//i represents the beginning of right half
		Long tmp = lines[startIndex];
		lines[startIndex] = lines[i-1];
		lines[i-1] = tmp;
		return i-1;
	}

	private static void processPivot(int startIndex,int endIndex){
		if (type.equals("last")){
			Long tmp = lines[startIndex];
			lines[startIndex] = lines[endIndex];
			lines[endIndex] = tmp;
		} else if(type.equals("median")){
			Long first = lines[startIndex];
			Long last = lines[endIndex];
			Long mid = lines[startIndex + ((endIndex-startIndex)/2)];
			if ((first-last)*(first-mid) <= 0){
				return;
			} else if((last-first)*(last-mid)<=0){
				//last is median
				Long tmp = lines[startIndex];
				lines[startIndex] = lines[endIndex];
				lines[endIndex] = tmp;
			} else {
				Long tmp = lines[startIndex];
				lines[startIndex] = lines[startIndex + ((endIndex-startIndex)/2)];
				lines[startIndex + ((endIndex-startIndex)/2)] = tmp;
			}
		}
	}
}