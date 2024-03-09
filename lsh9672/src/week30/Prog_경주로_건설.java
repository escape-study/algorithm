package week30;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 아이디어
 * 완전탐색 문제
 * 직선으로 가는것과 커브를 만드는 것중에 뭐가 더 좋을지는 모른다.
 * 커브의 경우, 비용이 더 들지만, 커브를 두었을때 비용을 더 많이 절감할 수도 있다.
 * 따라서 모든 경우를 다해봐야 한다.
 * 현재 노드에서 다음 노드로 가는 경우는 4방향이지만, 기존 방향으로 가는 것이 아니면 100원이 아닌 500원으로 계산해야 한다.
 * 여기서 주의할 점은, 만약 모든 도로를 까는 가중치가 동일했다면 먼저 방문하는 것이 최소값이 될 수 있지만,
 * 해당 경우의 경우, 도로 까는 비용이 다르기 때문에, 먼저 방문했다고 해당 칸을 방문처리 하면 안된다.
 * 진입 하는 방향에 따라 비용이 달라지기 때문에 3차원 배열로 방문 배열을 만들어야 한다.
 * bfs로 탐색이 가능한 이유는, 해당 노드에 가장 먼저 도착하는 경우가 최소비용이다.
 * 탐색 칸수가 늘면, 가격이 늘기 때문에 최소한의 칸으로 이동해야 최소비용이 된다.
 */
public class Prog_경주로_건설 {

	//4방향 탐색. - 상하좌우
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	//탐색을 위한 노드.
	private static class Node{
		int x, y, dir;

		public Node(int x, int y, int dir){
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		@Override
		public String toString() {
			return "Node{" +
				"x=" + x +
				", y=" + y +
				", dir=" + dir +
				'}';
		}
	}

	//이동가능한 위치인지 탐색.
	private static boolean check(int nextX, int nextY, int[][] board){
		int N = board.length;
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < N &&
			board[nextX][nextY] == 0;
	}
	//bfs탐색.
	private static int bfs(int[][] board){

		int[][][] costArray = new int[board.length][board[0].length][4];

		Queue<Node> needVisited = new ArrayDeque<>();
		//초기 값은 아래, 오른쪽 두가지만 존재.
		needVisited.add(new Node(0, 0, 1));
		needVisited.add(new Node(0, 0, 3));


		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();


			for(int i = 0; i < 4; i++){
				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];


				if(!check(nextX, nextY, board)) continue;

				//이전과 같은 방향이면 100, 다른 방향이면 코너이므로 600
				int nextCost = 100;
				if(currentNode.dir != i) nextCost = 600;

				int nextTotalCost = costArray[currentNode.x][currentNode.y][currentNode.dir] + nextCost;
				if(costArray[nextX][nextY][i] != 0 && costArray[nextX][nextY][i] <=  nextTotalCost) continue;

				costArray[nextX][nextY][i] = nextTotalCost;

				needVisited.add(new Node(nextX, nextY, i));
			}
		}

		//마지막 위치의 최소비용 반환
		int minValue = Integer.MAX_VALUE;
		for(int i = 0; i < 4; i++){
			if(costArray[board.length - 1][board[0].length - 1][i] == 0) continue;
			minValue = Math.min(minValue, costArray[board.length - 1][board[0].length - 1][i]);
		}

		return minValue;
	}

	public int solution(int[][] board) {


		return bfs(board);
	}

	public static void main(String[] args) {
		Prog_경주로_건설 s = new Prog_경주로_건설();

		int[][] board1 = {{0,0,0},{0,0,0},{0,0,0}};
		System.out.println(s.solution(board1));

		int[][] board2 = {{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0},{0,0,0,0,0,1,0,0},{0,0,0,0,1,0,0,0},{0,0,0,1,0,0,0,1},{0,0,1,0,0,0,1,0},{0,1,0,0,0,1,0,0},{1,0,0,0,0,0,0,0}};
		System.out.println(s.solution(board2));

		int[][] board3 = {{0,0,1,0},{0,0,0,0},{0,1,0,1},{1,0,0,0}};

		System.out.println(s.solution(board3));

		int[][] board4 = {{0,0,0,0,0,0},{0,1,1,1,1,0},{0,0,1,0,0,0},{1,0,0,1,0,1},{0,1,0,0,0,1},{0,0,0,0,0,0}};
		System.out.println(s.solution(board4));
	}
}
