package week48;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 아이디어
 * 투포인터
 * 양끝을 각각 철수, 동생으로 잡고,
 * 가운데(자를 위치)값을 변경하면서 양쪽의 토핑이 동일한지 판단한다.
 * 이때 철수와 동생의 조각의 토핑가지수를 저장할 맵자료구조를 만든다
 * 개수는 상관없고 서로 다른 토핑 가지수만 동일하면 된다. -> 맵 자료구조에 담고, 길이를 측정하는 식으로 처리한다.
 *
 *
 */
public class Prog_롤케이크_자르기 {


	//맵에서 값 빼는 메서드 - 0이면 제거.
	private static void removeMapValue(Map<Integer, Integer> map, int targetValue){

		if(!map.containsKey(targetValue)) return;

		int getValue = map.get(targetValue);

		if(getValue == 1) {
			map.remove(targetValue);
			return;
		}

		map.put(targetValue, map.get(targetValue) - 1);

	}


	public int solution(int[] topping) {
		int answer = 0;

		if(topping.length == 1) return 0;//토핑수가 1개이면 나눌 수 없음.

		Map<Integer, Integer> firstMap = new HashMap<>();//철수
		Map<Integer, Integer> secondMap = new HashMap<>();//동생

		//초기에 동생이 전부 가지고 있음
		for(int i = 0; i < topping.length; i++){
			secondMap.put(topping[i], secondMap.getOrDefault(topping[i], 0) + 1);
		}


		for(int i = 1; i < topping.length; i++){

			//이전 값 뺴기
			removeMapValue(secondMap, topping[i - 1]);

			//신규 값 넣기.
			firstMap.put(topping[i - 1], firstMap.getOrDefault(topping[i - 1], 0) + 1);

			//가지수 세기
			if(firstMap.size() == secondMap.size()) answer++;



		}


		return answer;
	}

	public static void main(String[] args) {
		Prog_롤케이크_자르기 s = new Prog_롤케이크_자르기();

		int[] topping1 = {1, 2, 1, 3, 1, 4, 1, 2};
		System.out.println(s.solution(topping1));

		int[] topping2 = {1, 2, 3, 1, 4};
		System.out.println(s.solution(topping2));
	}
}
