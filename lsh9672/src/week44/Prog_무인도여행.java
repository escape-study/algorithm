package week44;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

/**
 * 아이디어
 * bfs를 이용하여 영역의 크기를 구하는 문제 응용.
 */
public class Prog_무인도여행 {

	//4방탐색
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1 ,1};

	private static class Node{
		int x, y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
 	//이동가능한지 체크
	private static boolean check(int nextX, int nextY, char[][] mapChar){
		return nextX >= 0 && nextX < mapChar.length &&
			nextY >= 0 && nextY < mapChar[0].length &&
			mapChar[nextX][nextY] != 'X';
	}

	//bfs -> 영역안 숫자를 더한 값을 반환.
	private static int bfs(Node startNode, char[][] mapChar){

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		int count = 0;
		count += Character.getNumericValue(mapChar[startNode.x][startNode.y]);
		mapChar[startNode.x][startNode.y] = 'X';


		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			for(int i = 0; i < 4; i++){
				int nextX = currentNode.x + dx[i];
				int nextY = currentNode.y + dy[i];

				if(!check(nextX, nextY, mapChar)) continue;

				needVisited.add(new Node(nextX, nextY));
				count += Character.getNumericValue(mapChar[nextX][nextY]);
				mapChar[nextX][nextY] = 'X';

			}
		}

		return count;

	}


	public int[] solution(String[] maps) {

		List<Integer> countList = new ArrayList<>();

		char[][] mapChar = new char[maps.length][maps[0].length()];
		for(int i = 0; i < maps.length; i++){
			mapChar[i] = maps[i].toCharArray();

		}


		for(int i = 0; i < maps.length; i++){
			for(int j = 0; j < maps[0].length(); j++){

				if(mapChar[i][j] == 'X') continue;

				countList.add(bfs(new Node(i,j), mapChar));
			}
		}

		if(countList.isEmpty()) return new int[]{-1};


		countList.sort(Comparator.naturalOrder());
		int[] answer = new int[countList.size()];
		for(int i = 0; i < answer.length; i++){
			answer[i] = countList.get(i);
		}

		return answer;
	}

	public static void main(String[] args) {
		Prog_무인도여행 s = new Prog_무인도여행();

		String[] maps1 = {"X591X","X1X5X","X231X", "1XXX1"};
		System.out.println(Arrays.toString(s.solution(maps1)));

		String[] maps2 = {"XXX","XXX","XXX"};
		System.out.println(Arrays.toString(s.solution(maps2)));
	}
}
