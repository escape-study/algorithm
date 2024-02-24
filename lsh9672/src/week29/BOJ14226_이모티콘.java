package week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 아이디어
 * bfs
 * 최단거리를 구하는 문제이다
 * 숨바꼭질과 비슷하다.
 * 현재 위치는 이모티콘 1개이다.
 * S까지 이동해야 하는데, 다음 노드로 이동하는 방법은 -1, 클립보드에 저장하기, 클립보드에 있는 것 복사하기.
 * 즉, 다음 노드를 구하는데 두가지 방법이 있는거고, 이 방법을 통해서 S까지 갈수 있는 최단거리를 구하면 된다.
 * 현재 값과, 이동거리를 노드로 저장해서 pq를 사용해서 구하는 것이 좋다.
 */
public class BOJ14226_이모티콘 {



	//노드
	private static class Node implements Comparable<Node>{
		int value, distance, clipBoard;

		public Node(int value, int distance, int clipBoard){
			this.value = value;
			this.distance = distance;
			this.clipBoard = clipBoard;
		}

		@Override
		public int compareTo(Node node) {

			if(this.distance == node.distance) return this.value - node.value;

			return this.distance - node.distance;
		}
	}

	//다음 노드의 값 구하기. - 0번째 : 값, 1번쨰 : 클립보드
	private static int[] getNextValue(Node currentNode, int type){

		//클립보드 복사하기.
		if(type == 0) return new int[]{currentNode.value, currentNode.value};
		//클립보드 값 붙여넣기.
		else if(type == 1) return new int[]{currentNode.value + currentNode.clipBoard, currentNode.clipBoard};
		//하나 제거.
		else return new int[]{currentNode.value - 1, currentNode.clipBoard};

	}

	//bfs
	private static int bfs(Node startNode, int targetNum){
		boolean[][] visited = new boolean[1001][1001]; // 행은 수, 열은 클립보드
		visited[1][0] = true;

		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(startNode);

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.value == targetNum) return currentNode.distance;

			for(int i = 0; i < 3; i++){

				//클립보드가 0인데 붙여넣기 불가능.
				if(currentNode.clipBoard == 0 && i == 1) continue;

				//다음 상태 가져오기.
				int[] nextStatus = getNextValue(currentNode, i);

				//다음 노드방문했거나, 음수거나, 목표값을 넘어가면 패스
				if(nextStatus[0] < 0 || nextStatus[0] > targetNum || visited[nextStatus[0]][nextStatus[1]]) continue;

				visited[nextStatus[0]][nextStatus[1]] = true;
				pq.add(new Node(nextStatus[0], currentNode.distance + 1, nextStatus[1]));
			}
		}

		return -1;
	}



	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int S = Integer.parseInt(br.readLine());

		System.out.println(bfs(new Node(1, 0, 0), S));

	}
}
