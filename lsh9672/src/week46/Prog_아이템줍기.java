package week46;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * 아이디어
 * https://taehoung0102.tistory.com/95 참고함.
 *
 * bfs인데 그래프를 구성해줘야 한다.
 * 주어진 사각형 좌표를 기준으로 테두리와 그 안에 값을 true로 채워준다.
 * 채워진 그래프에서 테두리를 제외한 부분을 전부 false로 변경해서 외부 테두리만 남긴다
 * 이때 주의할 점이. a점과 b점이 길이가 1이라면, 두 점이 연결되어있지 않아도 bfs탐색할때는 탐색할 수도 있다.
 * 좌표 탐색을 할때 1단위로 움직이기 때문에 1차이나는 점은 연결되어있는지 확인이 불가능하다
 * 따라서 좌표를 두배로 늘려서 처리하면 이 문제를 해결 할 수 있다.
 *
 * (주의할점)
 * 좌표 평면을 두배로 늘렸기때문에 출발점과 끝점도 두배로 늘려야 한다.
 * 또한 좌표평면으로 줬기 때문에 좌표 x,y가 배열로 나타내면 반대라는 것을 주의해야 한다.
 */
public class Prog_아이템줍기 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static class Node{
		int x, y, count;
		public Node(int x, int y, int count){
			this.x = x;
			this.y = y;
			this.count = count;
		}
	}

	//그래프
	private static boolean[][] graph;

	//테두리 포함 좌표평면 전부 채우기 - true
	private static void fillRectangle(int[][] rectangle){

		for(int[] rec : rectangle){

			for(int i = rec[0] * 2; i <= rec[2] * 2; i++){
				for(int j = rec[1] * 2; j <= rec[3] * 2; j++){
					graph[j][i] = true;
				}
			}
		}
	}

	//테두리 제외 좌표 평면 전부 지우기 - false
	private static void eraseRectangle(int[][] rectangle){
		for(int[] rec : rectangle){

			for(int i = rec[0] * 2 + 1; i < rec[2] * 2; i++){
				for(int j = rec[1] * 2 + 1; j < rec[3] * 2; j++){
					graph[j][i] = false;
				}
			}
		}
	}

	private static boolean check(int nextX, int nextY, boolean[][] visited){
		return nextX >= 0 && nextX < 102 &&
			nextY >= 0 && nextY < 102 &&
			!visited[nextY][nextX] &&
			graph[nextY][nextX];
	}

	//bfs 탐색하기.
	private static int bfs(Node startNode, int itemX, int itemY){

		boolean[][] visited = new boolean[102][102];
		visited[startNode.y][startNode.x] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);


		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(currentNode.x == itemX && currentNode.y == itemY) {

				// System.out.println(currentNode.count / 2);
				return currentNode.count;
			}

			for(int i = 0; i < 4; i++){
				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY, visited)) continue;

				visited[nextY][nextX] = true;
				needVisited.add(new Node(nextX, nextY, currentNode.count + 1));
			}
		}


		return -1;
	}

	public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {

		graph = new boolean[102][102];

		fillRectangle(rectangle);
		eraseRectangle(rectangle);

		// for(int i = 0; i < 102; i++){
		// 	System.out.println(Arrays.toString(graph[i]));
		// }

		return bfs(new Node(characterX * 2, characterY * 2, 0), itemX * 2, itemY * 2) / 2;
	}

	public static void main(String[] args) {

		Prog_아이템줍기 s = new Prog_아이템줍기();
		int[][] rectangle1 = {{1,1,7,4},{3,2,5,5},{4,3,6,9},{2,6,8,8}};
		int characterX1 = 1;
		int characterY1 = 3;
		int itemX1 = 7;
		int itemY1 = 8;
		System.out.println(s.solution(rectangle1, characterX1, characterY1, itemX1,itemY1));

		int[][] rectangle2 = {{1,1,8,4},{2,2,4,9},{3,6,9,8},{6,3,7,7}};
		int characterX2 = 9;
		int characterY2 = 7;
		int itemX2 = 6;
		int itemY2 = 1;
		System.out.println(s.solution(rectangle2, characterX2, characterY2, itemX2,itemY2));

		int[][] rectangle3 = {{1,1,5,7}};
		int characterX3 = 1;
		int characterY3 = 1;
		int itemX3 = 4;
		int itemY3 = 7;
		System.out.println(s.solution(rectangle3, characterX3, characterY3, itemX3,itemY3));

		int[][] rectangle4 = {{2,1,7,5},{6,4,10,10}};
		int characterX4 = 3;
		int characterY4 = 1;
		int itemX4 = 7;
		int itemY4 = 10;
		System.out.println(s.solution(rectangle4, characterX4, characterY4, itemX4,itemY4));

		int[][] rectangle5 = {{2,2,5,5},{1,3,6,4},{3,1,4,6}};
		int characterX5 = 1;
		int characterY5 = 4;
		int itemX5 = 6;
		int itemY5 = 3;
		System.out.println(s.solution(rectangle5, characterX5, characterY5, itemX5,itemY5));


	}
}
