package week41;

import java.util.Arrays;

/**
 * 아이디어
 * 수학
 * 두 점 사이의 거리를 구하는 공식을 이용한다.
 * 벽을 맞춰야 하기 때문에 한점은 0이 되어서 따져야 하는 경우가 적다.
 * 단 벽 4면을 다 따져봐야 한다.
 * 모든 경우를 다 따질 필요가 없이 수학을 이용한다.
 * 입사각과 반사각이 동일한 케이스는 단 하나
 * 주의할 점은 각 벽마다 공이 평행하게 있는 경우를 패스해야 한다.(평행하면 평행한 방향으로 쿠션불가.)
 */
public class Prog_당구연습 {

	//두 점 사이 거리구하기
	private static int distance(int startX, int startY, int targetX, int targetY){
		return (int) (Math.pow(targetX - startX, 2) + Math.pow(targetY - startY, 2));
	}
	public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
		int[] answer = new int[balls.length];

		for(int i = 0; i < balls.length; i++){
			int minDistance = Integer.MAX_VALUE;
			int targetX = balls[i][0];
			int targetY = balls[i][1];

			//왼쪽 벽 -
			if(!(startY == targetY && startX > targetX)) {

				minDistance = Math.min(minDistance, distance(startX, startY, targetX * (-1), targetY));
			}


			//오른쪽 벽
			if(!(startY == targetY && startX < targetX)) {

				minDistance = Math.min(minDistance, distance(startX, startY, m + (m - targetX), targetY));
			}

			//상단 벽
			if(!(startX == targetX && startY < targetY)) {

				minDistance = Math.min(minDistance, distance(startX, startY, targetX, n + (n - targetY)));
			}

			//하단 벽
			if(!(startX == targetX && startY > targetY)) {

				minDistance = Math.min(minDistance, distance(startX, startY, targetX, targetY * (-1)));
			}

			answer[i] = minDistance;
		}

		return answer;
	}

	public static void main(String[] args) {
		Prog_당구연습 s = new Prog_당구연습();


		int m1 = 10;
		int n1 = 10;
		int startX1 = 3;
		int startY1 = 7;
		// int[][] balls1 = {{7, 7}, {2, 7}, {7, 3}};
		int[][] balls1 = {{7, 7}};

		System.out.println(Arrays.toString(s.solution(m1, n1, startX1, startY1, balls1)));
	}

}
