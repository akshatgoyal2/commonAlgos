import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

//Create a map of finishing times, read the file twice for revered and original graph for memory.
//java -Xmx400m -Xss8m -XX:+UseSerialGC Strongly_Connected -file=SCC.txt > out.txt

public class Strongly_Connected {
	private static Long[] lines = null;
    private Map<Long,List<Long>> graph = new HashMap<Long,List<Long>>();
    //private Map<Long,Long> finish_time = new HashMap<Long,Long>();
    private List<Long> finish_time = new ArrayList<Long>();//contains the node for each finish time
    private Map<Long,Boolean> exploredMap = new HashMap<Long,Boolean>();
    private Map<Long,List<Long>> leaderMap = new HashMap<Long,List<Long>>();
    private Long time = 0L;
    private static long globalCount = 100000000;
    //private static long globalCount = 50;

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

        List<Long> timeMap = getFinishTimeMap(fileName);
        getSCCs(fileName, timeMap);
    }

    private static List<Long> getFinishTimeMap(String fileName) throws IOException{
        //Get finish time map from the reverse graph
        Map<Long,List<Long>> reversedGraph = new HashMap<Long,List<Long>>();
        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        long count = globalCount;
        try {
            String line = null;
            while (( line = input.readLine()) != null && count>0){
                String[] parts = line.split("\\s");
                if(! reversedGraph.containsKey(Long.parseLong(parts[1]))) {
                    reversedGraph.put(Long.parseLong(parts[1]), new ArrayList<Long>());
                }    
                reversedGraph.get(Long.parseLong(parts[1])).add(Long.parseLong(parts[0]));
                count--;
            }
        }
        finally {
            input.close();
        }
        Strongly_Connected sc = new Strongly_Connected();
        sc.graph = reversedGraph;
        sc.DFSLoop();
        //System.out.println(sc.leaderMap);
        return sc.finish_time;
    }

    private void DFSLoop() {
        Long i=0L;
        Iterator iter = this.graph.keySet().iterator();
        while(iter.hasNext()) {
            Long key = (Long)iter.next();
            if (! this.exploredMap.containsKey(key)){
                this.DFS(key,key);
            } 
        }
    }

    private void DFS(Long i, Long leader) {
        this.exploredMap.put(i,true);
        if(! this.leaderMap.containsKey(leader)){
            this.leaderMap.put(leader, new ArrayList<Long>());
        }
        this.leaderMap.get(leader).add(i);
        if (! this.graph.containsKey(i)) {
            return;
        }
        for(Long j: this.graph.get(i)){
            if (! this.exploredMap.containsKey(j)){
                this.DFS(j,leader);
            }
        }
        this.time++;
        this.finish_time.add(i);
    }

    private static void getSCCs(String fileName, List<Long> finish_order) throws IOException{
        Map<Long,List<Long>> graph2 = new HashMap<Long,List<Long>>();
        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        long count = globalCount;
        try {
            String line = null;
            while (( line = input.readLine()) != null && count>0){
                String[] parts = line.split("\\s");
                if(! graph2.containsKey(Long.parseLong(parts[0]))) {
                    graph2.put(Long.parseLong(parts[0]), new ArrayList<Long>());
                }    
                graph2.get(Long.parseLong(parts[0])).add(Long.parseLong(parts[1]));
                count--;
            }
        }
        finally {
            input.close();
        }
        Strongly_Connected sc2 = new Strongly_Connected();
        sc2.finish_time = finish_order;
        sc2.graph = graph2;
        sc2.OrderedDFSLoop();
        System.out.println("Size  is : " + sc2.leaderMap.size());
        for (List<Long> leader : sc2.leaderMap.values()){
            if (leader.size() > 100)
            System.out.println(leader.size());
        }
    }

    private void OrderedDFSLoop() {
        int n = this.finish_time.size()-1;        
        while(n >= 0) {
            Long key = this.finish_time.get(n);
            if (! this.exploredMap.containsKey(key)){
                this.DFS(key,key);
            } 
            n--;
        }
    }
    
}