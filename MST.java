//package algorithm2;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.StringTokenizer;
//
//public class MST {
//	private static Graph graph = new Graph();
//	private static Heap heap = new Heap();
//	private static long minCost = 0;
//	
//	public static void main(String[] args){
//		
//		try{
//	
//			graph.buildMap("assignment_3.txt");
//			
//			Map<Node, HashMap<Node, Integer>> map = graph.getMap();
//			
//			Node startPt = graph.getRegisteredNode(3); //this (376) is the starting vertex
//			startPt.setKey(0);
//			
//			for(Node neighbor: map.get(startPt).keySet()){
//				int cost = map.get(startPt).get(neighbor);
//				neighbor.setKey(cost);
//				heap.insert(neighbor);
//			}
//			while(graph.getSize()!=1){
//				Node next = heap.extractMin();
//				minCost += next.getKey();
//				mergeNode(startPt, next);
//				/*for(Node neighbor: map.get(startPt).keySet())
//					System.out.println(neighbor);*/
//			}	
//			
//			System.out.println(minCost);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//	}
//	
//	/*This function will merge all of node2's neighbor to node 1*/
//	public static void mergeNode(Node node1, Node node2){
//		
//		heap.delete(node2);
//		
//		Map<Node, HashMap<Node, Integer>> map = graph.getMap();
//		
//		HashMap<Node, Integer> localMap = map.get(node2);
//		for(Node neighbor: localMap.keySet()){
//			if(!neighbor.equals(node1)){
//				if(map.get(node1).containsKey(neighbor)){
//					int cost = map.get(node2).get(neighbor);
//					int key = Math.min(neighbor.getKey(), cost);
//					heap.delete(neighbor);
//					neighbor.setKey(key);
//					heap.insert(neighbor);
//					//map.get(node1).
//				}else{
//					int cost = map.get(node2).get(neighbor);
//					int key = Math.min(cost, neighbor.getKey());
//					neighbor.setKey(key);
//					map.get(node1).put(neighbor, key);
//					map.get(neighbor).put(node1, key);
//					heap.insert(neighbor);
//				}
//			}
//			map.get(neighbor).remove(node2);
//			//map.get(neighbor).put(node1, key);
//		}
//		map.remove(node2);
//	}
//}
//
//class Graph{
//	Map<Node, HashMap<Node, Integer>> map; //map is used to store all the linking info
//	Map<Integer, Node> indexMap; //indexMap is used to register Node, and check if a node already exists
//	
//	
//	Graph(){
//		map = new HashMap<Node, HashMap<Node, Integer>>();
//		indexMap = new HashMap<Integer, Node>();
//	}
//	
//	public int getSize(){
//		return map.size();
//	}
//	public Map<Node, HashMap<Node, Integer>> getMap(){
//		return map;
//	}
//	
//	public Node registerNode(int index){
//		Node newNode = new Node(index);
//		indexMap.put(Integer.valueOf(index), newNode);
//		return newNode;
//	}
//	
//	public Node getRegisteredNode(int index){
//		return indexMap.get(Integer.valueOf(index));
//	}
//
//	/*The map is built in such a way that end points of a link is recorded twice, that is
//	 * they are neighbors of each other;
//	 * So first read every line; for node has not registered, register it first and build up links.
//	 * To build the link, it should be bilateral.
//	 * */
//	public void buildMap(String name) throws IOException{
//		
//		BufferedReader bf = new BufferedReader(new FileReader(name));
//		String line = bf.readLine();
//	//	Set<Integer> pool = new HashSet<Integer>();
//		
//		while((line = bf.readLine())!=null){ //read every line of the file, and build the map
//			//line = bf.readLine();
//			StringTokenizer st = new StringTokenizer(line, " ");
//			int src = Integer.parseInt(st.nextToken());
//			int des = Integer.parseInt(st.nextToken());
//			int cost = Integer.parseInt(st.nextToken());
//			
//			Node srcNode;
//			Node desNode;
//			
//			if(!indexMap.containsKey(Integer.valueOf(src))){
//				
//				srcNode = registerNode(src);
//				
//				if (!indexMap.containsKey(Integer.valueOf(des))) {
//					desNode = registerNode(des);
//				} else {
//					desNode = getRegisteredNode(des);
//				}
//	
//				HashMap<Node, Integer> localMap = new HashMap<Node, Integer>();
//				localMap.put(desNode, Integer.valueOf(cost));
//				map.put(srcNode, localMap);
//				HashMap<Node, Integer> reverseMap = new HashMap<Node, Integer>();
//				reverseMap.put(srcNode, Integer.valueOf(cost));
//				map.put(desNode, reverseMap);
//			}else{
//				srcNode = getRegisteredNode(src);
//				
//				if (!indexMap.containsKey(Integer.valueOf(des))) {//des point has not been registered
//					desNode = registerNode(des);
//					map.get(srcNode).put(desNode, Integer.valueOf(cost));
//					
//					HashMap<Node, Integer> reverseMap = new HashMap<Node, Integer>();
//					reverseMap.put(srcNode, Integer.valueOf(cost));
//					map.put(desNode, reverseMap);
//				} else {
//					desNode = getRegisteredNode(des);
//					map.get(srcNode).put(desNode, Integer.valueOf(cost));
//					map.get(desNode).put(srcNode, Integer.valueOf(cost));
//				}
//			}
//		}
//		
//		bf.close();	
//	}
//}
//
//
//class Node{
//	private int index;
//	private int key;
//	
//	public Node(int index){
//		this.index = index;
//		key = Integer.MAX_VALUE;
//	}
//	public Node(int index, int key){
//		this.index = index;
//		this.key = key;
//	}
//	
//	
//	public int getIndex(){
//		return index;
//	}
//	public int getKey(){
//		return key;
//	}
//	public void setKey(int key){
//		this.key = key; 
//	}
//	public void setIndex(int index){
//		this.index = index;
//	}
//	
//	public String toString(){
//		String str = "Node: "+index+" Key: "+key;
//		return str;
//	}
//}
//
//class Heap{
//	private List<Node> heap;
//	private int lastIndex;
//
//	public Heap(){
//		
//		heap = new ArrayList<Node>();
//		lastIndex = -1;
//	}
//	
//	public boolean delete(Node node){
//		if(!heap.contains(node)) return false;
//		
//		int index = heap.indexOf(node);
//		Node lastNode = heap.get(lastIndex);
//		heap.set(index, lastNode);
//		heap.remove(lastIndex);
//		lastIndex--;
//		
//		moveDownwards(index);
//		
//		return true;
//	}
//	
//	public int getSize(){
//		return lastIndex;
//	}
//	
//	public int getMin(){
//		if(heap==null){ 
//			System.err.println("");
//			return Integer.MIN_VALUE;
//		}// throw an error}
//		return heap.get(0).getKey();
//	}
//	
//	public void insert(Node node){
//		heap.add(node);
//		lastIndex++;
//		
//		if(lastIndex == 0 ) return ;
//		
//		int last = lastIndex;
//		int mid = (last-1)/2;
//		
//		while(last>0){//what if they equal to each other
//			Node midNode = heap.get(mid);
//			Node lastNode = heap.get(last);
//			
//			if (midNode.getKey() > lastNode.getKey()) {
//				heap.set(mid, lastNode);
//				heap.set(last, midNode);
//			}else
//				break; //if a child's cost is larger than its parent's, cease here.
//			
//			last = mid;
//			mid = (last-1)/2;
//		} 
//	}
//	
//	public Node extractMin(){
//		if(heap==null) return null;
//		if(lastIndex==0) return heap.get(0); //two special cases
//		
//		Node temp = heap.get(0);
//		
//		Node lastNode = heap.get(lastIndex);
//		heap.set(0, lastNode);
//		heap.remove(lastIndex);  //move the last Node to ahead, then move it down
//		
//		lastIndex--; 
//		int root = 0; 
//		
//		moveDownwards(root);
//		
//		return temp;
//	}
//	
//	private void moveDownwards(int root){
//		while (root<lastIndex) {
//			// if root == last, then the pointer hits the end, the program ceases.
//			int l = root*2+1;
//			int r = root*2+2;
//			
//			Node rootNode = heap.get(root);
//			/*
//			 * consider the 3 following cases, for a root node:
//			 * 1. It doesn't have children;
//			 * 2. It has only one child, that is left child;
//			 * 3. It has both children.
//			 * */
//			if (l > lastIndex) //when the index went out of boundary
//				break;
//			else if (l == lastIndex) {
//				Node lNode = heap.get(l);
//				if(lNode.getKey()<rootNode.getKey()){
//					heap.set(root, lNode);
//					heap.set(l, rootNode);
//				}
//				break;
//			} else {
//				Node lNode = heap.get(l);
//				Node rNode = heap.get(r);
//				if (lNode.getKey() <= rNode.getKey() && lNode.getKey()<rootNode.getKey()) {
//					heap.set(root, lNode);
//					heap.set(l, rootNode);
//					root = l;
//				} else if(rNode.getKey() < lNode.getKey() && rNode.getKey()<rootNode.getKey()) {
//					heap.set(root, rNode);
//					heap.set(r, rootNode);
//					root = r;
//				} else
//					break;
//			}
//	}
//	}
//	
//}