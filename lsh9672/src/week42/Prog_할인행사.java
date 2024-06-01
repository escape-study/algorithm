package week42;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 슬라이딩 윈도우
 * 10연속이라는 항상 같은 크기로 윈도우를 이동하면서 비교를 한다.
 * 매번 다시 갯수를 세는 것은 비효율적이므로 윈도우를 사용한다.
 *
 */
public class Prog_할인행사 {

	//10일동안 할인한 품목과 원하는 물품의 개수 확인.
	private static boolean check(int[] windowArray, int[] number){

		for(int i = 0; i < windowArray.length; i++){

			if(windowArray[i] < number[i]) return false;
		}

		return true;
	}

	public int solution(String[] want, int[] number, String[] discount) {
		int answer = 0;

		Map<String, Integer> wantMap = new HashMap<>();
		int[] windowArray = new int[want.length];

		//제품 : 인덱스
		for(int i = 0; i < want.length; i++){
			wantMap.put(want[i], i);
		}

		//초기 윈도우 넣기
		for(int i = 0; i < 10; i++){
			if(!wantMap.containsKey(discount[i])) continue;
			windowArray[wantMap.get(discount[i])]++;
		}

		if(check(windowArray, number)) answer++;

		for(int i = 1; i + 9 < discount.length; i++){

			if(wantMap.containsKey(discount[i - 1])){
				windowArray[wantMap.get(discount[i - 1])]--;
			}

			if(wantMap.containsKey(discount[i + 9])){
				windowArray[wantMap.get(discount[i + 9])]++;
			}

			if(check(windowArray, number)) answer++;

		}

		return answer;
	}
	public static void main(String[] args) {

		Prog_할인행사 s = new Prog_할인행사();

		String[] want1 = {"banana", "apple", "rice", "pork", "pot"};
		int[] number1 = {3, 2, 2, 2, 1};
		String[] discount1 = {"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"};
		System.out.println(s.solution(want1, number1, discount1));

		String[] want2 = {"apple"};
		int[] number2 = {10};
		String[] discount2 = {"banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana"};
		System.out.println(s.solution(want2, number2, discount2));

	}
}
