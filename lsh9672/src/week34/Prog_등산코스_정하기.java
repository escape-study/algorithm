package week34;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 아이디어
 * 다익스트라
 * 다익스트라로 구현할때 알아야 할 점은, 시작점 -> 봉우리, 봉우리 -> 시작점 은 두번 구할 필요가 없는 것이다.
 * 시작점 -> 봉우리로 이동하는 최소의 Intensity를 구했으면, 그 값이  봉우리 -> 시작점 으로 이동하는 최소값이 된다.
 * 여러 경로를 거쳐야 하고, 갔던 경로를 반복해야 하며, 이전에 구한 intensity 보다 크다면 볼 필요 없기 떄문에 다익스트라를 이용할 수 있다.
 *
 * (추가)
 * 모든 출발점에서 다익스트라를 돌려서 찾으려고 하면, 시간초과가 날수 있다.
 * 따라서 한번의 다익스트라만 돌릴수 있도록 초기에, 모든 출발점을 pq에 넣어서 처리한다.
 */

public class Prog_등산코스_정하기 {

	//노드
	private static class Node implements Comparable<Node>{

		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}

		//
		@Override
		public int compareTo(Node node) {
			return 0;
		}
	}

	//그래프
	private static List<Node>[] graph;


	//다익스트라 메서드 - 산봉우리와, 해당 출발점의 최소 Intensity를 구함.
	private static int[] dijkstra(int n, int[] startNode, int[] summits){
		//특정 출발점에서 i번째까지 도착하는데 걸리는 최소의 intensity(등산로 중에 가장 긴 가중치 저장.)
		int[] intensityArrays = new int[n];
		for(int i = 0; i < n; i++){
			intensityArrays[i] = Integer.MAX_VALUE;
		}

		Queue<Node> needVisited = new ArrayDeque<>();
		for(int i = 0; i < startNode.length; i++){
			needVisited.add(new Node(startNode[i] - 1, 0));
			intensityArrays[startNode[i] - 1] = 0;
		}

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//현재 노드의 가중치가, 이전에 해당 위치에 도달한 가중치보다 크면 더 탐색할 필요 없음
			//즉 현재 노드의 가중치가 5인데, 이미 해당 노드를 탐색해서 저장한 값을 확인했더니 4라면, 4로도 해당 위치를 갈수 있다는 뜻이므로,
			//4가 최소 intensity가 됨.(구하고자 하는 것은, intensity의 최소값이므로, 더작은것을 선택하도록 함.)
			if(intensityArrays[currentNode.node] < currentNode.weight) continue;

			//다음 노드 확인
			for(Node nextNode : graph[currentNode.node]){

				//현재 노드의 가중치와 다음으로 이동할 노드의 가중치중에 더 큰쪽을 선택
				//휴식없이 이동해야 하는 시간중 가장 긴 시간을 나타내기 떄문에, 현재 가중치가 더 크다면, 다음위치까지 도달하는데 필요한 가중치는 현재 가중치가 됨.
				int tempWeight = Math.max(intensityArrays[currentNode.node], nextNode.weight);
				//다음위치에 저장된 가중치가 더 크면 새로운 값으로 업데이트.(방금구한 tempWeight가 더 작다는 뜻은, 해당 위치로 가는 더 작은 가중치가 존재한다는 뜻.)
				if(tempWeight < intensityArrays[nextNode.node]){
					intensityArrays[nextNode.node] = tempWeight;
					needVisited.add(new Node(nextNode.node, tempWeight));
				}
			}
		}

		int topNum = Integer.MAX_VALUE;//산봉우리번호
		int minIntensity = Integer.MAX_VALUE; //최소 intensity값

		for(int summit : summits){

			if(minIntensity > intensityArrays[summit - 1]){
				minIntensity = intensityArrays[summit - 1];
				topNum = summit;
			}
			//intensity최소값이 여러개면 산봉우리의 번호가 낮은것 선택
			else if(minIntensity == intensityArrays[summit - 1]){
				if(topNum > summit){
					topNum = summit;
				}
			}
		}

		return new int[]{topNum, minIntensity};
	}



	public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {

		//매번 노드가 출발지인지 산봉우리인지 판단시간을 O(1)로 떨어뜨리기 위해서 Set에 넣음.
		Set<Integer> gatesSet = new HashSet<Integer>();
		for(int gate : gates){
			if(gatesSet.contains(gate)) continue;
			gatesSet.add(gate);
		}
		Set<Integer> summitsSet = new HashSet<>();
		for(int summit : summits){
			if(summitsSet.contains(summit)) continue;
			summitsSet.add(summit);
		}

		//초기 그래프 구성
		graph = new List[n];
		for(int i = 0; i < n; i++){
			graph[i] = new ArrayList<>();
		}
		//그래프 배열 채우기 - 구성시 주의할 점은 양방향이라는 것이다.
		//이때 출발점은 단방향으로 구성해서 출발점을 중복해서 거치는 경우가 없도록 만들어준다.
		//산봉우리의 경우에도 중복해서 방문하지 안도록 산봉우리로 들어오는 노드만 고려하도록 한다.
		for(int[] path : paths){
			int a = path[0];
			int b = path[1];
			int weight = path[2];

			//a가 출발지이거나 b가 봉우리라면.
			if(gatesSet.contains(a) || summitsSet.contains(b)){
				graph[a - 1].add(new Node(b - 1, weight));
			}
			//b가 출발지이거나, a가 봉우리인 경우.
			else if(gatesSet.contains(b) || summitsSet.contains(a)){
				graph[b - 1].add(new Node(a - 1, weight));
			}
			//일반노드일 경우 - 양쪽 다 필요.
			else{
				graph[a - 1].add(new Node(b - 1, weight));
				graph[b - 1].add(new Node(a - 1, weight));
			}
		}



		return dijkstra(n, gates, summits);
	}

	public static void main(String[] args) {

		Prog_등산코스_정하기 s = new Prog_등산코스_정하기();

		int n1 = 6;
		int[][] paths1 = {{1, 2, 3}, {2, 3, 5}, {2, 4, 2}, {2, 5, 4}, {3, 4, 4}, {4, 5, 3}, {4, 6, 1},{5, 6, 1}};
		int[] gates1 = {1,3};
		int[] summits1 = {5};
		System.out.println(Arrays.toString(s.solution(n1,paths1, gates1, summits1)));

		int n2 = 7;
		int[][] paths2 = {{1, 4, 4}, {1, 6, 1}, {1, 7, 3}, {2, 5, 2}, {3, 7, 4}, {5, 6, 6}};
		int[] gates2 = {1};
		int[] summits2 = {2,3,4};
		System.out.println(Arrays.toString(s.solution(n2,paths2, gates2, summits2)));

		int n3 = 7;
		int[][] paths3 = {{1, 2, 5}, {1, 4, 1}, {2, 3, 1}, {2, 6, 7}, {4, 5, 1}, {5, 6, 1}, {6, 7, 1}};
		int[] gates3 = {3,7};
		int[] summits3 = {1,5};
		System.out.println(Arrays.toString(s.solution(n3, paths3, gates3, summits3)));

		int n4 = 5;
		int[][] paths4 = {{1, 3, 10}, {1, 4, 20}, {2, 3, 4}, {2, 4, 6}, {3, 5, 20}, {4, 5, 6}};
		int[] gates4 = {1,2};
		int[] summits4 = {5};
		System.out.println(Arrays.toString(s.solution(n4,paths4, gates4, summits4)));


	}
}
