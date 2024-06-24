package week45;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * Map을 이용한 구현
 */

public class Prog_시소짝꿍 {

	public long solution(int[] weights) {
		long answer = 0;

		Arrays.sort(weights);

		Map<Double, Integer> weightMap = new HashMap<>();

		for(int weight : weights){

			if(weightMap.containsKey(weight * 1.0)) answer += weightMap.get(weight * 1.0);
			if(weightMap.containsKey((weight * 2.0) / 3.0)) answer += weightMap.get((weight * 2.0) / 3.0);
			if(weightMap.containsKey((weight * 2.0) / 4.0)) answer += weightMap.get((weight * 2.0) / 4.0);
			if(weightMap.containsKey((weight * 3.0) / 4.0)) answer += weightMap.get((weight * 3.0) / 4.0);

			weightMap.put(weight * 1.0, weightMap.getOrDefault(weight * 1.0, 0) + 1);
		}


		return answer;
	}
	public static void main(String[] args) {

		Prog_시소짝꿍 s = new Prog_시소짝꿍();

		int[] weight1 = {100,180,360,100,270};
		System.out.println(s.solution(weight1));
	}
}
