import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

//Create a map of finishing times, read the file twice for revered and original graph for memory.

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

             // read the lines out of the file
        //List<String> lines = new ArrayList<String>();
        //lines = new Long[100000];

        //Map<Long,Long> finish_time = getFinishTimeMap(fileName);
        //Map<Long,Long> leaders = new HashMap<Long,Long>();
        List<Long> timeMap = getFinishTimeMap(fileName);
        getSCCs(fileName, timeMap);
        //System.out.println(leaderMap);

        /*BufferedReader input =  new BufferedReader(new FileReader(fileName));
        int i=0;
        try {
            String line = null;
            //int count = 0;
            while (( line = input.readLine()) != null){
                lines.add(line);
                //lines[i++] = Long.parseLong(line);
                //count++;
            }
        }
        finally {
            input.close();
        }*/
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
        //System.out.println(sc2.leaderMap);
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

    /*private static void executeRandomContraction(List<String> lines){
        int count = 1000;

        int mincut = 200;//Putting an upper limit

        for(int i=count; count >0; count--){
            int n = performRandomContraction(lines);
            if(n<mincut) mincut = n;
        }
        
        System.out.println("Mincut over 1000  iterations is: "+mincut);
    }*/

    /*private static int performRandomContraction(List<String> lines){
        Map<Integer, List<Integer>> adjacencyMap = new HashMap<Integer, List<Integer>>();
        GraphContraction gc = new GraphContraction();
        for(String line :lines){
            String[] parts = line.split("\\s");
            List<Integer> adjacents = new ArrayList<Integer>();
            for(int i=1; i<parts.length; i++){
                adjacents.add(Integer.parseInt(parts[i].trim()));
            }
            adjacencyMap.put(Integer.parseInt(parts[0].trim()), adjacents);
        }        

        while(adjacencyMap.size() > 2){
            int[] edge = getRandomEdge(adjacencyMap);
            adjacencyMap = removeEdgeAndContractGraph(adjacencyMap, edge[0], edge[1]);
        }


        List<Integer> vertices = new ArrayList<Integer>(adjacencyMap.keySet());
        //System.out.println("size of random cut is: " + adjacencyMap.get((Integer)vertices.get(0)).size());
        return adjacencyMap.get((Integer)vertices.get(0)).size();
    }*/

    /*
    / This method returns a random edge from an adjacency list
    */
    /*private static int[] getRandomEdge(Map<Integer, List<Integer>> adjacencyMap){
        //2 element array , first is first vertex, 2nd is 2nd vertex
        int[] edge = new int[2];
        int edge0index = (int)Math.round(Math.random() * (adjacencyMap.size()-1));
        List<Integer> vertices = new ArrayList<Integer>(adjacencyMap.keySet());
        int randomVertex = vertices.get(edge0index);
        edge[0] = randomVertex;
        List<Integer> adjacents = adjacencyMap.get(randomVertex);
        int edge1index = (int)Math.round(Math.random() * (adjacents.size()-1));
        edge[1] = adjacents.get(edge1index);
        return edge;
    }*/

    /*private static Map<Integer, List<Integer>> removeEdgeAndContractGraph(Map<Integer, List<Integer>> adjacencyMap, int vertex1, int vertex2){
        //Pick the smaller vertex of two. Also replace the vertex no in all other vertices.

        int base1 = vertex1;  //contracting into the smaller vertex
        int base2= vertex2;
        if(vertex2 < vertex1) {
            base1= vertex2;
            base2= vertex1;
        } 

        for (int i: adjacencyMap.get(base2)){
            //Check self loops just in case
            if (i== base1 || i==base2) continue;
            adjacencyMap.get(base1).add(i);
            while(adjacencyMap.get(i).contains(base2)){
                adjacencyMap.get(i).remove((Integer)base2);
                adjacencyMap.get(i).add((Integer)base1);
            }
        }

        while (adjacencyMap.get(base1).contains(base2)){
            adjacencyMap.get(base1).remove((Integer)base2);
        }
        
        adjacencyMap.remove(base2);

        return adjacencyMap;
    }*/
}