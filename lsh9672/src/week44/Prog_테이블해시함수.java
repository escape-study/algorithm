package week44;

import java.util.Arrays;

/**
 * 아이디어
 * 단순 구현
 * 문제에서 제시된 대로 따라가면 된다.
 */
public class Prog_테이블해시함수 {
	public int solution(int[][] data, int col, int row_begin, int row_end) {
		int answer = 0;

		//col로 정렬 수행
		Arrays.sort(data , (o1, o2) -> {

			if(o1[col - 1] == o2[col - 1]) return o2[0] - o1[0];

			return o1[col - 1] - o2[col - 1];
		});

		//row_begin부터 row_end까지 i로 나눈 나머지의 합으로 계산, 각로우별 계산된 결과를XOR 연산해서 누적.
		for(int i = row_begin; i <= row_end; i++){

			//인덱스이므로 -1한 값을 사용해야 함.
			int S_i = 0;
			for(int tmp : data[i - 1]){
				S_i += tmp % i;
			}

			//처음이면 S_i을 넣기.
			if(i == row_begin){
				answer = S_i;
				continue;
			}

			//이전에 값이 있으면
			answer ^= S_i;
		}

		return answer;
	}

	public static void main(String[] args) {
		Prog_테이블해시함수 s = new Prog_테이블해시함수();

		int[][] data1 = {{2,2,6},{1,5,10},{4,2,9},{3,8,3}};
		int col1 = 2;
		int row_begin1 = 2;
		int row_end1 = 3;
		System.out.println(s.solution(data1, col1, row_begin1, row_end1));
	}
}
