package week41;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 아이디어
 * bfs
 * bfs이용한 최단거리 탐색이다.
 * 단, 이동을 한칸씩 하는 것이 아니라 한방향으로 가서 벽에 부딪히거나 격차판을 벗어나는 등,
 * 더이상 이동할 수 없을 때까지 이동하는 것이다.
 */
public class Prog_리코쳇로봇 {

	//4방향
	private final static int[] dx = {-1,1 ,0 ,0};
	private final static int[] dy = {0, 0, -1, 1};

	//이동을 위한 노드
	private static class Node{
		int x, y, distance;

		public Node(int x, int y, int distance){
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
	}

	private static boolean[][] visited;//방문 처리 배열

	//이동가능한지 체크하는 메서드 -
	private static boolean check(int nextX, int nextY, String[] board){
		return nextX >= 0 && nextX < board.length &&
			nextY >= 0 && nextY < board[0].length() &&
			board[nextX].charAt(nextY) != 'D';
	}

	//이동후 위치를 구하는 메서드
	private static Node move(Node currentNode, int dir, String[] board){

		int count = 1;
		int nextX = -1;
		int nextY = -1;
		while(true){

			nextX = currentNode.x + dx[dir] * count;
			nextY = currentNode.y + dy[dir] * count;

			if(!check(nextX, nextY, board)) break;

			count++;
		}


		return new Node(
			currentNode.x + dx[dir] * (count - 1),
			currentNode.y + dy[dir] * (count - 1),
			currentNode.distance + 1
		);
	}


	//bfs 탐색을 위한 메서드
	private static int bfs(Node startNode, String[] board){

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			if(board[currentNode.x].charAt(currentNode.y) == 'G') return currentNode.distance;

			for(int dir = 0; dir < 4; dir++){

				Node nextNode = move(currentNode, dir, board);

				if(!check(nextNode.x, nextNode.y, board) || visited[nextNode.x][nextNode.y]) continue;

				visited[nextNode.x][nextNode.y] = true;
				needVisited.add(nextNode);

			}
		}






		return -1;
	}


	public int solution(String[] board) {

		visited = new boolean[board.length][board[0].length()];

		Node startNode = null;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length(); j++){

				if(board[i].charAt(j) != 'R') continue;

				startNode = new Node(i,j,0);
				break;
			}
		}

		return bfs(startNode, board);
	}

	public static void main(String[] args) {
		Prog_리코쳇로봇 s = new Prog_리코쳇로봇();

		String[] board1 = {"...D..R", ".D.G...", "....D.D", "D....D.", "..D...."};
		System.out.println(s.solution(board1));

		String[] board2 = {".D.R", "....", ".G..", "...D"};
		System.out.println(s.solution(board2));


	}
}
