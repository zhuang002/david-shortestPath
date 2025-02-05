import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Graph map = new Graph();
		map.load(sc);
		
		
		int start = sc.nextInt();
		int end = sc.nextInt();
		int distance = map.getShortestPath(start, end);
		
		sc.close();
		
		System.out.println("the shortest path from "+start+" to "+end+" is "+distance);

	}

}



class Graph {
	int nNodes=0;
	int nPaths=0;
	int[][] paths = null;
	
	public void load(Scanner sc) {
		
		this.nNodes = sc.nextInt();
		this.nPaths = sc.nextInt();
		
		this.paths = new int[this.nNodes][this.nNodes];
		for (int i=0;i<this.nNodes;i++) {
			for (int j=0;j<this.nNodes;j++) {
				this.paths[i][j] = 0;
			}
		}
				
		
		for (int i=0;i<this.nPaths;i++) {
			int n1 = sc.nextInt();
			int n2 = sc.nextInt();
			int length = sc.nextInt();
			
			this.addPath(n1,n2,length);
		}
		
	}

	private void addPath(int n1, int n2, int length) {
		this.paths[n1][n2] = length;
		this.paths[n2][n1] = length;
	}

	public int getShortestPath(int start, int end) {
		ArrayList<Integer> tobeProcessed = new ArrayList<>();
		int[] distanceToStart = new int[this.nNodes];
		for (int i=0;i<distanceToStart.length;i++) {
			distanceToStart[i] = Integer.MAX_VALUE;
		}
		
		tobeProcessed.add(start); 
		distanceToStart[start] = 0;
		
		while (!tobeProcessed.isEmpty()) {
			int currentNodeIndex = findClosestNodeIndex(tobeProcessed, distanceToStart);
			int currentNode = tobeProcessed.get(currentNodeIndex);
			ArrayList<Integer> neighbours = findNeighbours(currentNode);
			
			for (Integer neighbour:neighbours) {
				int pathLength = getPathLength(currentNode, neighbour);
				int neighbourDistance = distanceToStart[currentNode] + pathLength;
				if (neighbourDistance<distanceToStart[neighbour]) {
					distanceToStart[neighbour] = neighbourDistance;
					tobeProcessed.add(neighbour);
				}
			}
			tobeProcessed.remove(currentNodeIndex);
		}
		
		
		return distanceToStart[end];
	}

	private int getPathLength(int currentNode, Integer neighbour) {
		return this.paths[currentNode][neighbour];
	}

	private ArrayList<Integer> findNeighbours(int currentNode) {
		ArrayList<Integer> neighbours = new ArrayList<>();
		for (int i=0;i<this.paths.length;i++) {
			if (this.paths[currentNode][i]>0) {
				neighbours.add(i);
			}
		}
		return neighbours;
	}

	private int findClosestNodeIndex(ArrayList<Integer> tobeProcessed, int[] distanceToStart) {
		int index=-1;
		int minDist = Integer.MAX_VALUE;
		for (int i=0;i<tobeProcessed.size();i++) {
			int node = tobeProcessed.get(i);
			if (minDist > distanceToStart[node]) {
				minDist = distanceToStart[node];
				index = i;
			}
		}
		return index;
	}
}