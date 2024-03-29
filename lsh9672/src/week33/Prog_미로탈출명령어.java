package week33;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * 아이디어
 * bfs
 * bfs로 탐색하는 로직이다.
 * 거리가 K가 될때까지 큐에서 다음노드를 꺼내면서 탐색을 반복한다
 * 이미 방문한 격자도 방문이 가능하기 때문에 일반적인 방법으로 방문처리 할수 없고, lrud로 만들어진 문자열로 해야한다.
 * 여러번 방문 할수 있어도, 방문 히스토리가 동일하게 동일한 위치를 방문할 필요는 없다
 * 가령, 이전에 1,1 위치레 lr로 방문했는데, 다음 방문할때도 lr이면 방문할 필요가 없다
 * 이를 가르기 위해서 set을 이용해서 각 격자판마다 집합자료구조로 방문처리를 한다.
 * 주의할 점은 큐에서 꺼낸 값이 k를 넘으면 더이상 방문할 필요가 없어진다.
 *
 * (추가)
 * 메모리 밑 시간초과가 난다.
 * 백트래킹으로 해결해야 한다.
 * dfs방식으로 탐색하면서 거리가 K를 넘어가면 가지치기 한다.
 * 탐색순서는 사전순으로 했을때, 제일 먼저 나오는 것이 사전순으로 제일 앞서게 된다.
 * 또한 impossible이 나오는 케이스를 탐색없이 구해볼수 있다.
 * 1. 최단거리와, 목표거리는 둘다 짝수거나 홀수여야 한다 => 그림그려서 해보면 답이 나온다.(벽이 있으면 달라졌겠지만 벽이없다.)
 * 2. k가 최단거리보다 짧다면
 * 위의 두가지 경우는 탐색없이 처리하면된다 -> 맨해튼거리를 사용,
 * 반복중간에도 맨해튼 거리를 이용해서 k를 넘어서면 더이상 볼 필요가 없기 때문에 패스하면 된다.
 * 굳이 방문 처리를 하지 않아도 거리로 체크를 하면 무한하게 반복하는 경우를 방지할 수 있다.
 *
 */
public class Prog_미로탈출명령어 {

	//4방 탐색 - 문자열의 사전순으로.
	private final static int[] dx = {1, 0, 0, -1};
	private final static int[] dy = {0, -1, 1, 0};
	//4방향에 맞는 방향 문자열
	private final static String[] dirStr = {"d", "l", "r", "u"};

	//탐색할 노드
	private static class Node{
		int x, y, distance;

		public Node(int x, int y, int distance){
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
	}

	//목표위치
	private static int targetX;
	private static int targetY;

	//경로 저장
	private static StringBuilder result;

	//맨해튼 거리
	private static int manhattan(int currentX, int currentY, int nextX, int nextY){

		return Math.abs(currentX - nextX) + Math.abs(currentY - nextY);
	}

	//격자판을 벗어났는지 확인.
	private static boolean check(int nextX, int nextY, int n, int m){
		return nextX >= 0 && nextX < n &&
			nextY >= 0 && nextY < m;
	}

	//bfs 메서드
	private static boolean dfs(int n, int m, int k, Node currentNode){

		//현재 위치에서 목표까지의 거리 + 누적거리가 k보다 크면 종료
		if(manhattan(currentNode.x, currentNode.y, targetX, targetY) + currentNode.distance > k) return false;

		//목표위치에 도달했다면,
		if(currentNode.x == targetX && currentNode.y == targetY && currentNode.distance == k) return true;

		for(int i = 0; i < 4; i++){

			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			//갈수 있는곳인지 판단.
			if(check(nextX, nextY, n, m)){
				result.append(dirStr[i]);

				if(dfs(n, m, k, new Node(nextX, nextY, currentNode.distance + 1))) return true;

				result.setLength(result.length() - 1);
			}
		}

		return false;
	}


	public String solution(int n, int m, int x, int y, int r, int c, int k) {
		result = new StringBuilder();

		targetX = r - 1;
		targetY = c - 1;

		int minDistance = manhattan(x, y, r, c);
		//둘다 홀수거나 짝수여야 한다.
		if((minDistance % 2 == 0 && k % 2 != 0) || (minDistance % 2 != 0 && k % 2 == 0)) return "impossible";

		//최단거리보다 K가 작다면
		if(minDistance > k) return "impossible";

		//도달하지 못하면 impossible
		if(!dfs(n, m, k, new Node(x - 1, y - 1, 0))) return "impossible";

		return result.toString();
	}

	public static void main(String[] args) {

		Prog_미로탈출명령어 s = new Prog_미로탈출명령어();

		int n1 = 3;
		int m1 = 4;
		int x1 = 2;
		int y1 = 3;
		int r1 = 3;
		int c1 = 1;
		int k1 = 5;
		System.out.println(s.solution(n1,m1,x1,y1,r1,c1,k1));

		int n2 = 2;
		int m2 = 2;
		int x2 = 1;
		int y2 = 1;
		int r2 = 2;
		int c2 = 2;
		int k2 = 2;
		System.out.println(s.solution(n2,m2,x2,y2,r2,c2,k2));

		int n3 = 3;
		int m3 = 3;
		int x3 = 1;
		int y3 = 2;
		int r3 = 3;
		int c3 = 3;
		int k3 = 4;
		System.out.println(s.solution(n3,m3,x3,y3,r3,c3,k3));
	}
}
