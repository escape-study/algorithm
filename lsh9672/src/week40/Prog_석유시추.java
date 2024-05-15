package week40;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 아이디어
 * bfs
 * 각 영역별로 번호를 매기고, 번호별로 몇칸인지 저장해둔다.
 */
public class Prog_석유시추 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//각 번호별로 몇칸인지 저장 - 2번부터 시작
	private static Map<Integer, Integer> oilMap;

	private static boolean check(int[][] land, int nextX, int nextY){
		return nextX >= 0 && nextX < land.length &&
			nextY >= 0 && nextY <land[0].length;
	}

	//bfs 돌면서 카운팅.
	private static int bfs(Node startNode, int[][] land, int num){

		int count = 0;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startNode.x, startNode.y));


		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			count++;
			land[currentNode.x][currentNode.y] = num;

			for(int i = 0; i < 4; i++){
				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(land, nextX, nextY) || land[nextX][nextY] != 1) continue;

				land[nextX][nextY] = num;
				needVisited.add(new Node(nextX, nextY));
			}
		}

		return count;
	}

	public int solution(int[][] land) {

		int answer = 0;

		oilMap = new HashMap<>();

		int num = 2;
		for(int i = 0; i < land.length; i++){
			for(int j = 0; j < land[0].length; j++){

				if(land[i][j] != 1) continue;

				oilMap.put(num,bfs(new Node(i, j), land, num));
				num++;

			}
		}


		Set<Integer> checkSet;
		for(int col = 0; col < land[0].length; col++){
			int total = 0;
			checkSet = new HashSet<>();
			for(int row = 0; row < land.length; row++){

				if(land[row][col] == 0 || checkSet.contains(land[row][col])) continue;


				checkSet.add(land[row][col]);
				total += oilMap.get(land[row][col]);

			}

			answer = Math.max(answer, total);
		}

		return answer;
	}

	public static void main(String[] args) {

		Prog_석유시추 s = new Prog_석유시추();

		int[][] land1 = {{0, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 0, 0, 0, 1, 1, 0}, {1, 1, 1, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0, 1, 1}};
		System.out.println(s.solution(land1));

		int[][] land2 = {{1, 0, 1, 0, 1, 1}, {1, 0, 1, 0, 0, 0}, {1, 0, 1, 0, 0, 1}, {1, 0, 0, 1, 0, 0}, {1, 0, 0, 1, 0, 1}, {1, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1}};
		System.out.println(s.solution(land2));
	}
}
