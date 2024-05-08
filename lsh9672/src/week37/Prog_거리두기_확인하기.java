package week37;

import java.util.Arrays;

/**
 * 아이디어
 * 배치된 상태를 확인한다.
 * 배치된 사람에서 dfs 탐색을 해서, 거리가 2 이하이면 잘못된 케이스이다.
 */

public class Prog_거리두기_확인하기 {

	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};


	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//방문체크
	private static boolean[][] visited;

	//배열 크기를 넘어가는지 확인
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < 5 &&
			nextY >= 0 && nextY < 5;
	}

	//dfs
	private static boolean dfs(Node currentNode,int distance, String[] place){

		//해당 위치에 사람이 있는지 확인.
		if(distance > 0 && place[currentNode.x].charAt(currentNode.y) == 'P') return false;

		//위에서 안걸리고 2이상이면 패스.
		if(distance >= 2) return true;

		for(int i = 0; i < 4; i++){

			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			if(!check(nextX, nextY) || visited[nextX][nextY] || place[nextX].charAt(nextY) == 'X') continue;

			visited[nextX][nextY] = true;

			if(!dfs(new Node(nextX, nextY), distance + 1, place)) return false;

			visited[nextX][nextY] = false;

		}


		return true;
	}

	public int[] solution(String[][] places) {
		int[] answer = new int[places.length];

		int index = 0;
		for(String[] place : places){

			boolean flag = true;
			loop:
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){

					if(place[i].charAt(j) != 'P') continue;

					visited = new boolean[5][5];
					visited[i][j] = true;

					if(!dfs(new Node(i,j), 0, place)){
						flag = false;
						break loop;
					}
				}
			}

			if(flag) answer[index] = 1;
			index++;
		}

		return answer;
	}

	public static void main(String[] args) {
		Prog_거리두기_확인하기 s = new Prog_거리두기_확인하기();
		String[][] places = {{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}, {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"}, {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"}, {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"}, {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}};
		System.out.println(Arrays.toString(s.solution(places)));
	}
}
