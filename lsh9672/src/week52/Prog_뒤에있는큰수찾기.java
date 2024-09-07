package week52;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 아이디어
 * 구현
 *
 * (주의)
 * 문제를 잘 읽어야 한다.
 * 배열 전체에서 나보다 큰것을 찾는게 아니라, 주어진 배열에서, 인덱스상 뒤에 있는 것들 중, 나보다 크고 가장 가까운 수를 찾는것이다.
 */
public class Prog_뒤에있는큰수찾기 {


	public int[] solution(int[] numbers) {
		int[] answer = new int[numbers.length];

		answer[numbers.length - 1] = -1; //마지막 수는 뒤에 수가 없음.

		for(int i = numbers.length - 2; i >= 0; i--){

			int currentValue = numbers[i];
			int nextValue = numbers[i + 1];

			//현재수가 뒤에 있는 수보다 작으면, 뒤에 있는 수 저장.
			if(currentValue < nextValue){
				answer[i] = nextValue;
			}

			//현재 수가 뒤에 있는 수와 같으면,  뒤에 있는 수가 저장한 값 저장.
			else if(currentValue == nextValue){
				answer[i] = answer[i + 1];
			}
			//현재 수가 뒤에 있는 수보다 크면, 거슬러 올라가면서 확인해야 함.
			else{
				int index = i;
				while(index++ < numbers.length){

					if(answer[index] == -1 || currentValue < answer[index]){
						answer[i] = answer[index];
						break;
					}
				}
			}
		}

		return answer;
	}

	public static void main(String[] args) {

		Prog_뒤에있는큰수찾기 s = new Prog_뒤에있는큰수찾기();

		int[] numbers1 = {2, 3, 3, 5};
		System.out.println(Arrays.toString(s.solution(numbers1)));

		int[] numbers2 = {9, 1, 5, 3, 6, 2};
		System.out.println(Arrays.toString(s.solution(numbers2)));

	}
}
