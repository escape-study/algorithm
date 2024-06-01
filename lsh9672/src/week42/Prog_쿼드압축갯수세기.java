package week42;

import java.util.Arrays;

/**
 * 아이디어
 * 분할정복
 * 정사각형을 4조각으로 쪼개는 것을 반복한다.
 * 쪼갰을떄 각 조각안의 모든 수가 같다면, 분할중지하고, 갯수 누적.
 * 부
 */
public class Prog_쿼드압축갯수세기 {


	private static int[] answer;

	//분할한 정사각형의 모든 수가 같은지 확인하는 메서드 => 같지 않다면 -1반환, 같으면 0,1 반환
	//왼쪽 상단의 좌표와 한변의 길이만 알면됨.
	private static int check(int[][] arr, int x, int y, int length){

		for(int i = x; i < x + length; i++){
			for(int j = y; j < y + length; j++){

				if(arr[x][y] != arr[i][j]) return -1;
			}
		}

		return arr[x][y];
	}

	//재귀 호출
	private static void recursive(int[][] arr, int x, int y, int length){
		
		//길이가 1이면 그냥 반환
		if(length == 1){
			answer[arr[x][y]]++;
			return;
		}
		//모든칸의 수가 같다면 개수 추가하고 종료
		int value = check(arr, x, y, length);
		if(value != -1){
			answer[value]++;
			return;
		}


		
		//1사분면
		recursive(arr, x, y + (length / 2), length / 2);
		
		//2사분면
		recursive(arr, x, y, length / 2);
		
		//3사분면
		recursive(arr, x + (length / 2), y, length / 2);
		
		//4사분면
		recursive(arr, x + (length / 2), y + (length / 2), length / 2);
	}




	public int[] solution(int[][] arr) {
		answer = new int[2];

		recursive(arr, 0, 0, arr.length);

		return answer;
	}

	public static void main(String[] args) {
		Prog_쿼드압축갯수세기 s = new Prog_쿼드압축갯수세기();

		int[][] arr1 = {{1,1,0,0},{1,0,0,0},{1,0,0,1},{1,1,1,1}};
		System.out.println(Arrays.toString(s.solution(arr1)));

		int[][] arr2 = {{1,1,1,1,1,1,1,1},{0,1,1,1,1,1,1,1},{0,0,0,0,1,1,1,1},{0,1,0,0,1,1,1,1},{0,0,0,0,0,0,1,1},{0,0,0,0,0,0,0,1},{0,0,0,0,1,0,0,1},{0,0,0,0,1,1,1,1}};
		System.out.println(Arrays.toString(s.solution(arr2)));

	}
}
