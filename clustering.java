package algorithm2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class clustering {
	static //did not build construction function. this piece of code is used exclusively here.
	Union_Find uf = new Union_Find();
	static quickSort qs;
	
	// the return type should be an array list of Link
	private static ArrayList<Link> readFile(String filename) throws IOException{
		
		BufferedReader bf = new BufferedReader(new FileReader(filename));
		String line = bf.readLine();
		
		ArrayList<Link> edges = new ArrayList<Link>();
		Map<Integer, Node> nodes = new HashMap<Integer, Node>();
		
		while((line = bf.readLine())!=null){ //read every line of the file, and build the map
			//line = bf.readLine();
			StringTokenizer st = new StringTokenizer(line, " ");
			int src = Integer.parseInt(st.nextToken());
			int des = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			Node srcNode;
			Node desNode;
			
			if(nodes.containsKey((Integer.valueOf(src)))){
				srcNode = nodes.get((Integer.valueOf(src)));
			}else{
				srcNode = new Node(src);
				nodes.put(Integer.valueOf(src), srcNode);
			}
			
			if(nodes.containsKey((Integer.valueOf(des)))){
				desNode = nodes.get((Integer.valueOf(des)));
			}else{
				desNode = new Node(des);
				nodes.put(Integer.valueOf(des), desNode);
			}
			
			Link edge = new Link(srcNode, desNode, cost);
			edges.add(edge);
		}
		return edges;
	}
	
	private static void print(List<Link> edges){
		for(Link edge: edges){
			System.out.println(edge);
		}
	}
	// the return type should be an array list of Link
	/*private void sort(ArrayList<Link> arrayOfLinks){
		
		
		
	}*/
	
	public static void main(String[] args) throws IOException{
		//read file into unsorted array list
		//sort array list in descending order
		//for each link, fuse its two end node
		ArrayList<Link> edges = readFile("asg_2_1.txt");
	//	print(edges);
		qs = new quickSort(edges);
	//	qs.sorting(0, edges.size()-1);
		edges = qs.getSortedArray();
		try{
			 FileWriter fstream = new FileWriter("out.txt");
			 BufferedWriter out = new BufferedWriter(fstream);
			 for(Link edge: edges)
				 out.write(/*uf.Union(edge)+"  "+uf.getDist()*/edge+ "\n");
			 out.close();
			 }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			 }
	//	print(edges);
		for(Link edge:edges){
			uf.Union(edge);
		}
		
		System.out.println(uf.getDist());
	}
}

class quickSort{
	
	static ArrayList<Link> edges; 
	int length;
	
	quickSort(ArrayList<Link> edges){
		this.edges = edges;
		length = edges.size();
	}
	
	public ArrayList<Link> getSortedArray(){
		sorting(0, length-1);
		return edges;
	}
	
	void sorting(int start, int end){
		
		if(end<=start||end<0) return ;
		
		Link pivot = edges.get(start);
		int i = start+1;
		int j = start+1;
		
		while (j <= end) {
			if (pivot.getCost() > edges.get(j).getCost()) {
				swap(i,j);
				i++;
			}
			j++;
		}
		int partition = i-1;
		swap(start, partition);
		sorting(start, partition-1);
		sorting(partition+1, end);
	}
	
	private void swap(int pre, int post){
		Link temp = edges.get(pre);
		edges.set(pre, edges.get(post));
		edges.set(post, temp);
	}
	
}

class Union_Find{
	HashMap <Node, HashSet<Node>> map;
	int clusterNum; //clusterNum is used to track number of groups
	static int totalNum;
	static int dist;
	
	Union_Find(){
		map = new HashMap<Node, HashSet<Node>>();
		clusterNum = -1;
		totalNum = 500;
		dist = 0;
	}
	
	public int getDist(){
		return dist;
	}
	
	// the return type should be boolean
	public boolean Union(Link edge){
		// Both nodes already exist in one cluster, should do nothing in this case
		Node start = edge.getStartNode();
		Node end = edge.getEndNode();
		
		
		if(start.find().equals(end.find()))
			return false;
		// None of the two nodes are considered so far
		else{ 

			if(totalNum == 4 ){
				dist = edge.getCost();
			}
			
			if ((!map.containsKey(start.find()) && !map.containsKey(end.find()))
				|| map == null) {
				if(!start.find().equals(start))
					System.err.println("the error is here");
					
			end.setLeader(start.find());
			HashSet<Node> links = new HashSet<Node>();
			links.add(start);
			links.add(end);
			map.put(start, links);
			totalNum--;
		//	dist++;
			return true;
		}else if (!map.containsKey(start.find()) && map.containsKey(end.find())){
			if(!start.find().equals(start))
				System.err.println("the error");
			
			Node leader = end.find();
			
			map.get(leader).add(start.find());
			start.setLeader(leader);
			totalNum--;
	//		dist++;
			return true;
		}else if (!map.containsKey(end.find()) && map.containsKey(start.find())){
			Node leader = start.find();
			map.get(leader).add(end.find());
			end.setLeader(leader);
			totalNum--;
	//		dist++;
			return true;
		}else {
			//compare size
			int a = map.get(start.find()).size();
			int b = map.get(end.find()).size();
			if(a>=b)
				fuse(start, end);
			else
				fuse(end, start);
			totalNum--;
	//		dist++;
			return true;
		}	
		}
	}
	// this is to merge all of b's links to a. a, b are not necessarily leaders. 
	private void fuse(Node a, Node b){
	//	Node leader = a.find();
		Node bLeader = b.find();
		for(Node node: map.get(b.find())){
			node.setLeader(a.find());
			map.get(a.find()).add(node);
		}
		//map.get(leader).add(b.find());
		map.remove(bLeader);
	}
}

class Link{
	private Node start;
	private Node end;
	private int cost;
	
	Link(Node a, Node b, int c){
		start = a;
		end = b;
		cost = c;
	}
	
	public Node getStartNode(){
		return start;
	}
	public Node getEndNode(){
		return end;
	}
	
	public int getCost(){
		return cost;
	}
	
	public String toString(){
		return "nodeSrc: "+start.getIndex()+"; nodeDes: "+end.getIndex()+"; Cost: "+cost;
	}
}


class Node{
	private int index;
	private Node leader;
	
	Node(int index){
		this.index = index;
		leader = this;
	}
	public int getIndex(){
		return index;
	}
	
	public void setLeader(Node n){
		leader = n;
	}
	
	public Node find(){
		return leader;
	}
}