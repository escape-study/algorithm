package week47;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 그리디
 * k개의 귤을 뽑는데, 서로 다른 종류의 수를 최소화 하고 싶다면,
 * 갯수 순으로 내림차순하여, 개수가 많은 순으로 귤을 뽑으면 된다.
 */
public class Prog_귤고르기 {


	public int solution(int k, int[] tangerine) {
		int answer = 0;

		//각 귤별 갯수를 저장하는 맵
		Map<Integer, Integer> countMap = new HashMap<>();

		for(int i = 0; i < tangerine.length; i++){
			countMap.put(tangerine[i], countMap.getOrDefault(tangerine[i], 0) + 1);
		}

		//value 만 뽑아서 내림차순 정렬
		Integer[] valueArray = countMap.values().toArray(new Integer[0]);
		Arrays.sort(valueArray, (o1, o2) -> {
			return o2 - o1;
		});

		int tempCount = k; //메서드 인자로 받은 값을 직접 변경하는 것은 좋지 않기 때문에 별도의 변수를 할당해서 처리.
		for(int i = 0; i < valueArray.length; i++){

			answer++;

			//남은 귤의 개수보다 현재 선택한 귤이 크거나 같으면 해당 귤 다 선택하면 되기 떄문에 종료.
			if(tempCount <= valueArray[i]) break;

			tempCount -= valueArray[i]; //귤을 선택하면 해당 귤의 최대 개수만큼 빼주기.
		}

		return answer;
	}

	public static void main(String[] args) {

		Prog_귤고르기 s = new Prog_귤고르기();

		int k1 = 6;
		int[] tangerine1 = {1, 3, 2, 5, 4, 5, 2, 3};
		System.out.println(s.solution(k1, tangerine1));

		int k2 = 4;
		int[] tangerine2 = {1, 3, 2, 5, 4, 5, 2, 3};
		System.out.println(s.solution(k2, tangerine2));


		int k3 = 2;
		int[] tangerine3 = {1, 1, 1, 1, 2, 2, 2, 3};
		System.out.println(s.solution(k3, tangerine3));
	}
}
