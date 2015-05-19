package algorithm_asg6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SAT {
	static int vertexNum;
	static int[][] matrix;
	
	
	private static void read(String filename) throws NumberFormatException, IOException{
		
		BufferedReader bf = new BufferedReader(new FileReader(filename));
		String line;
		
		int i=0;
		int count = 0;
		vertexNum = Integer.parseInt(bf.readLine());
		
		while(bf.readLine()!=null){
			count ++;
		}
		bf.close();
		
		bf = new BufferedReader(new FileReader(filename));
		
		matrix = new int[count*2][2];
		bf.readLine();
		int[] temp = new int[2];
		int j;
		
		while((line = bf.readLine())!=null){
			String[] chars = line.split("\\s+");
			j=0;
			for(String integer: chars){
				temp[j] = Integer.parseInt(integer);
				j++;
			}
			
			matrix[2*i][0] = -temp[0];
			matrix[2*i][1] = temp[1];
			matrix[2*i+1][0] = -temp[1];
			matrix[2*i+1][1] = temp[0];
			
			i++;
		}
		
		bf.close();	
	}
	
	public static void main(String[] args) {
		
		Graph graph = new Graph();
		findScc scc = new findScc();
		
	//	int[][] matrix = {{1,2},{2,3},{2,4},{3,1}};
				
	//	int[][] matrix = { { 1, 4 }, { 2, 8 }, { 3, 6 }, { 4, 7 }, { 5, 2 },
	//			{ 6, 9 }, { 7, 1 }, { 8, 5 }, { 8, 6 }, { 9, 7 }, { 9, 3 } };
		
	//	int[][] matrix = {{ 1, 2 }, { 2, 6 }, { 2,3 },{2,4},{3,1},{3,4},{4,5},{5,4},{6,5},{6,7},{7,6},{7,8},{8,5},{8,7}};
	//	int[][] matrix = {{1,2},{2,3},{3,1},{3,4},{5,4},{6,4},{8,6},{6,7},{7,8},{4,3},{4,6}};
		
		//int[][] matrix = {{1,2},{1,3},{2,3},{2,4},{3,4}};
		try{
		read("asg_6_test.txt");
		System.out.println("....................0.");
		graph.buildMap(matrix);
//		System.out.println("....................1.");
		scc.DSFLoop(graph,false, vertexNum);
//		System.out.println("....................2.");
		Graph newG = graph.reverseMap();
	//	System.out.println("....................3.");
		scc.DSFLoop(newG,true, vertexNum);
	//	System.out.println("....................4.");
		
		List<ArrayList<Integer>> groups = scc.getSCC();
		Set<Integer> newList;
		for(ArrayList<Integer> list: groups){
			int val1 = list.size();
			newList = new HashSet<Integer>();
			for(Integer n:list)
			{
				newList.add(Math.abs(n));
			}
			int val2 = newList.size();
			if(val1==val2)
				continue;
			else
				System.out.println("wrong");	
		}
		
		
		/*Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream("filename.txt"), "utf-8"));
			for(Integer number: scc.sizeList){
				writer.write(number + "\r\n");
			}			
		writer.close();
*/
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		/*for(Node node:newG.getAllNodes()){
			System.out.println(node);
			System.out.println("neighbor:");
			
			for(Node neighbor:newG.getNeighbor(node))
				System.out.println(neighbor);
		}*/
		
		
		
		/*for(Integer number: scc.sizeList)
			System.out.println(number);*/
		/*BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
		for(Integer number: scc.sizeList){
			out.write(number + "\n\n");
		}			
		out.close();*/
		
		/*
		 *  writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream("filename.txt"), "utf-8"));
    writer.write("Something");*/
		
	}


}

class findScc {
//	Graph graph = new Graph();
	int count;
	
	ArrayList<Integer> nodes = new ArrayList<Integer>();
	List<ArrayList<Integer>> nodesList = new ArrayList<ArrayList<Integer>>();
	Set<Integer> pool;
	
	List<ArrayList<Integer>> getSCC(){
		return nodesList;
	}
	
	void BFS(Graph g,boolean getSize, Node startV){
		
		if(!startV.isVisited()){ 
			
			startV.setVisited();
			pool.add(Integer.valueOf(startV.vertexIndex));
			if(getSize)
				nodes.add(startV.originalIndex);
			for(Node node:g.getNeighbor(startV)){
				BFS(g,getSize, node);
			}
			startV.setFinishTime(count);
			count++;
		
		}
	}
	
	void DSFLoop(Graph g, boolean getSize, int vertexNum){
		count = 1;
		pool = new HashSet<Integer>();
		int start;
		int end;
		
		if(getSize)
		{
			start = 2*vertexNum;
			end = 0;
		}else{
			start = vertexNum;
			end = -vertexNum;
		}
		
		for(int n = start; n>=end; n--){
			
			if(!g.getAllIndex().contains(n))
				continue;
			
			Node node = g.getNode(n);
			if(!pool.contains(Integer.valueOf(n))){
				BFS(g, getSize, node);
				if(getSize){
					nodesList.add(nodes);
					nodes = new ArrayList<Integer>();
				}
			}
		}
	}
	}


class Graph{
	Map<Node, ArrayList<Node>> map;
	Map<Integer, Node> indexMap;
	
	Graph(){
		map = new HashMap<Node, ArrayList<Node>>();
		indexMap = new HashMap<Integer, Node>();
	}
	
	void addNode(Node n){
		map.put(n, new ArrayList<Node>());
		indexMap.put(n.vertexIndex, n);
	}
	
	Set<Integer> getAllIndex(){
		return Collections.unmodifiableSet(indexMap.keySet());
	}
	
	/**
	 * binary operation 
	 * @param neighbor
	 */
	void addDLink(Node des, Node neighbor){
		map.get(des).add(neighbor);
	}
	
	void addNeighbors(Node des, ArrayList<Node> neighbors){
		map.get(des).addAll(neighbors);
	}
	
	Set<Node> getAllNodes(){
		return Collections.unmodifiableSet(map.keySet());
	}
	
	ArrayList<Node> getNeighbor(Node n){
		return map.get(n);
	}
	
	Node getNode(int x){
		/*for(Node node:Collections.unmodifiableCollection(map.keySet())){
			if(node.vertexIndex==x)
				return node;
		}*/
		Integer index = Integer.valueOf(x);
		return indexMap.get(index); 
		
		//return null;
	}
	
	void buildMap(int[][] matrix){
		Set<Integer> pool = new HashSet<Integer>();
		Node des = null;
		Node src;
		
		for(int i=0; i<matrix.length; i++){
		
			int vertex = matrix[i][0];
			if(!pool.contains(Integer.valueOf(vertex))){
				pool.add(Integer.valueOf(vertex));
				des = new Node(vertex);
				addNode(des);
			}else{
				des = getNode(vertex);
			}
		
			int neighbor = matrix[i][1];
			if(!pool.contains(Integer.valueOf(neighbor))){
				pool.add(Integer.valueOf(neighbor));
				src = new Node(neighbor);
				addNode(src);
				addDLink(des,src);
			}else{
				src = getNode(neighbor);
				addDLink(des,src);
			}
		}
	}
	
	Graph reverseMap(){
		Graph g = new Graph();
		
		for(Node node:getAllNodes()){
			node.reset();
		}
		for(Node src:getAllNodes()){
			g.addNode(src);
		}
		
		for(Node src:getAllNodes()){
			for(Node des:getNeighbor(src))
				g.addDLink(des, src);
		}
		return g;
	}
}
	
class Node{
	int vertexIndex;
	int finishTime;
	int originalIndex;
	private boolean visited;
	
	Node(int vt){
		vertexIndex = vt;
		visited = false;
		finishTime = -1;
		originalIndex = vertexIndex;
	}
	
	void reset(){
		visited = false;
		vertexIndex = finishTime;
		finishTime = -1;
	}
	
	void setFinishTime(int ft){
		finishTime = ft;
	}
	
	boolean isVisited(){
		return visited;
	}
	
	void setVisited(){
		visited = true;
	}
	
	public String toString(){
		return "Node:"+ vertexIndex +" "+"FinishTime"+finishTime;
	}
}
