package algorithm2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class knapsack {
	
	private static int total_Weight;
	private static int total_Items;
	private static ArrayList<Item> items = new ArrayList<Item>();
	private static ArrayList<Integer> preColumnValue = new ArrayList<Integer>();
	private static ArrayList<Integer> currentColValue = new ArrayList<Integer>();
	
	
	private static void readFile(String filename) throws IOException{
		BufferedReader bf = new BufferedReader(new FileReader(filename));
		String line = bf.readLine();
		StringTokenizer st = new StringTokenizer(line, " ");
		total_Weight = Integer.parseInt(st.nextToken());
		total_Items = Integer.parseInt(st.nextToken());;
		
		//ArrayList<Item> items = new ArrayList<Item>();
		int index = 0;
		while((line = bf.readLine())!=null){ //read every line of the file, and build the map
			st = new StringTokenizer(line, " ");
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			Item item = new Item(index, v, w);
			index++;
			items.add(item);
		}
	
	}
	
	private static int selectItems(){
	//	ArrayList<Integer> preColumnValue = new ArrayList<Integer>();
	//	ArrayList<Integer> currentColValue = new ArrayList<Integer>();
		
		
		
		for(Item item : items){
			for(int cW = 0; cW<total_Weight; cW++){ //cW = current weight
		//		int lW = total_Weight - cW; //lW = left column weight
				int iW = item.getWeight(); //iW = item's weight
				int iV = item.getValue();
				
				int v1 = 0;
				int v2 = preColumnValue.get(cW).intValue();;
				
				if(iW <= cW){
					v1 = iV + preColumnValue.get(cW-iW).intValue();
				}
				
				if (v1 > v2) 
					currentColValue.add(Integer.valueOf(v1));
				else
					currentColValue.add(Integer.valueOf(v2)) ; 
			}
			preColumnValue = currentColValue;
			currentColValue = new ArrayList<Integer>(); 
		}
		
		return preColumnValue.get(total_Weight-1).intValue();
	}
	
	private static int selectItems2(int index, int lW){
		//first index ought to be (total_Items-1)
		//lW = left weight, first lW should be (total_Weight)
		
		if(index < 0) return 0;
		if(lW <= 0 ) return 0;
			
		int cV = items.get(index).getValue();
		int cW = items.get(index).getWeight();
		
		int v1 = 0;
		if(lW>cW)
			v1 = cV + selectItems2(index-1, lW - cW);
		int v2 = selectItems2(index-1, lW);
		if(v1>v2)
			return v1;
		else 
			return v2;
	}
	
	public static void main(String[] args){
		try {
			readFile("test.txt");
		
	/*			for(Item i:items)
				System.out.println(i);*/
			
			//System.out.println(total_Weight);
			for(int i=0; i<total_Weight; i++){
				preColumnValue.add(0);
			}
			
			int maxWeight = selectItems();
			
			System.out.println(maxWeight);
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Item{
	private int index;
	private int value;
	private int weight;
	
	public Item(int index, int value, int weight){
		this.index = index;
		this.value = value;
		this.weight = weight;
	}
	
	/*public Item(){
		Item(-1, 0, 0);
	}*/
	
	public void setValue(int v){
		value = v;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setWeight(int w){
		weight = w;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public String toString(){
		return "Item's value: "+value+"; Item's weight: "+weight;
	}
}
