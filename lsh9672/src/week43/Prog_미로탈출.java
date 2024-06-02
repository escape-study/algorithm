package week43;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 아이디어
 * bfs
 * 단순한 bfs를 이용한 최단거리문제
 * 시작 -> 레버를 구하고, 다시 레버 -> 탈출구까지의 거리를 구하면 되는 문제이다.
 */
public class Prog_미로탈출 {

	//4방향 탐색
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	//노드
	private static class Node{
		int x, y, distance;

		public Node(int x, int y, int distance){
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
	}

	//최초시간
	private static int minTime;

	//이동가능한 곳인지 체크
	private static boolean check(int nextX, int nextY, String[] maps){

		return nextX >= 0 && nextX < maps.length &&
			nextY >= 0 && nextY < maps[0].length() &&
			maps[nextX].charAt(nextY) != 'X';

	}

	//bfs탐색: 출발 -> 레버인지, 레버 -> 탈출구인지 파악 필요.
	//type : 0 => 출발 -> 레버, 1 => 레버 -> 탈출구.
	private static Node bfs(Node startNode, String[] maps, int type){

		boolean[][] visited = new boolean[maps.length][maps[0].length()];
		visited[startNode.x][startNode.y] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//type : 0 이라면 레버일떄 멈춤, type : 1이라면 탈출구일때 멈춤
			if(type == 0 && maps[currentNode.x].charAt(currentNode.y) == 'L') {
				minTime += currentNode.distance;
				return new Node(currentNode.x, currentNode.y, 0);
			}
			else if(type == 1 && maps[currentNode.x].charAt(currentNode.y) == 'E') {
				minTime += currentNode.distance;
				return new Node(currentNode.x, currentNode.y, 0);
			}

			for(int i = 0; i < 4; i++){

				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY, maps) || visited[nextX][nextY]) continue;

				visited[nextX][nextY] = true;
				needVisited.add(new Node(nextX, nextY, currentNode.distance + 1));

			}
		}

		return null;

	}

	//시작점 찾기
	private static Node getStartNode(String[] maps){

		for(int i = 0; i < maps.length; i++){
			for(int j = 0; j < maps[0].length(); j++){

				if(maps[i].charAt(j) == 'S') return new Node(i, j, 0);

			}
		}

		return null;
	}

	public int solution(String[] maps) {
		minTime = 0;
		//출발 -> 레버 -> 탈출
		Node switchNode = bfs(getStartNode(maps), maps, 0);

		if(switchNode == null) return -1;

		Node endNode = bfs(switchNode, maps, 1);

		if(endNode == null) return -1;

		return minTime;
	}

	public static void main(String[] args) {
		Prog_미로탈출 s = new Prog_미로탈출();

		String[] maps1 = {"SOOOL","XXXXO","OOOOO","OXXXX","OOOOE"};
		System.out.println(s.solution(maps1));

		String[] maps2 = {"LOOXS","OOOOX","OOOOO","OOOOO","EOOOO"};
		System.out.println(s.solution(maps2));

	}
}
