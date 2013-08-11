import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Djikstra {
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
        }

        
        getMinPath(lines);
    }


    private static Map<Integer,Integer> getMinPath(List<String> lines){
        Map<Integer, List<Integer>> adjacencyMap = new HashMap<Integer, List<Integer>>();
        List<Edge> edgeList = new ArrayList<Edge>();
        Map<Integer,Integer> processedVertices = new HashMap<Integer,Integer>();
        Map<Integer,Integer> unprocessedVertices = new HashMap<Integer,Integer>();
        Djikstra dj = new Djikstra();
        for(String line :lines){
            String[] parts = line.split("\\s");
            List<Integer> adjacents = new ArrayList<Integer>();
            //Creating the list of edges
            for(int i=1; i<parts.length; i++){
                String[] nnn = parts[i].split(",");
                edgeList.add(dj.new Edge(Integer.parseInt(parts[0].trim()),Integer.parseInt(nnn[0].trim()),Integer.parseInt(nnn[1].trim())));
            }
            //adjacencyMap.put(Integer.parseInt(parts[0].trim()), adjacents);
            unprocessedVertices.put(Integer.parseInt(parts[0].trim()), 1000000);
        }     
        processedVertices.put(Integer.parseInt((lines.get(0).split("\\s"))[0].trim()),0);
        unprocessedVertices.remove(1);
        //System.out.println(unprocessedVertices);
        //System.out.println(edgeList.get(0));
        for (int i=0;i<lines.size();i++){
            //System.out.println(i);
            int[] arr = new int[2];
            arr[0] = 300;
            arr[1] = 1000000;
            for(Edge edge: edgeList) {
                if (processedVertices.containsKey(edge.n1) && unprocessedVertices.containsKey(edge.n2)) {
                    //System.out.println(edge);
                    if ((edge.length + processedVertices.get(edge.n1)) < arr[1]) {
                        arr[1] = edge.length + processedVertices.get(edge.n1);
                        arr[0] = edge.n2;
                    }
                }        
            }
            //System.out.println("here");
            processedVertices.put(arr[0],arr[1]);
            unprocessedVertices.remove(arr[0]);
        }
        System.out.println(processedVertices);

        /*while(adjacencyMap.size() > 2){
            int[] edge = getRandomEdge(adjacencyMap);
            adjacencyMap = removeEdgeAndContractGraph(adjacencyMap, edge[0], edge[1]);
        }

        List<Integer> vertices = new ArrayList<Integer>(adjacencyMap.keySet());*/
        //System.out.println("size of random cut is: " + adjacencyMap.get((Integer)vertices.get(0)).size());
        //return adjacencyMap.get((Integer)vertices.get(0)).size();
        System.out.print(processedVertices.get(7));
        System.out.print(",");
        System.out.print(processedVertices.get(37));
        System.out.print(",");
        System.out.print(processedVertices.get(59));
        System.out.print(",");
        System.out.print(processedVertices.get(82));
        System.out.print(",");
        System.out.print(processedVertices.get(99));
        System.out.print(",");
        System.out.print(processedVertices.get(115));
        System.out.print(",");
        System.out.print(processedVertices.get(133));
        System.out.print(",");
        System.out.print(processedVertices.get(165));
        System.out.print(",");
        System.out.print(processedVertices.get(188));
        System.out.print(",");
        System.out.print(processedVertices.get(197));
        return processedVertices;
    }

    private class Edge {
        public int n1;
        public int n2;
        public int length;
        public Edge(int n1, int n2, int length) {
            this.n1 = n1;
            this.n2 = n2;
            this.length = length;
        }

        public String toString(){
            return "V1: "+n1 + ", V2: "+n2+", length: "+length;
        }
    }

    
}