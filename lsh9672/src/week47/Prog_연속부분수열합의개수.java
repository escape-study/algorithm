package week47;

import java.util.HashSet;
import java.util.Set;

/**
 * 아이디어
 * 누적합
 * 해당 문제를 응용한 문제가 BOJ2632 피자판매
 * 원형태로 된 배열에서의 누적합을 구해서 나올 수 있는 경우의 수를 구하는 문제.
 *
 * (추가)
 * 다른사람의 풀이에서 확인한 내용으로는, %를 사용한 방식을 이용할 수도 있음.
 * 이렇게 되면, 배열의 공간을 두배로 키워서 잡아먹는 메모리를 줄일수도 있음.
 */
public class Prog_연속부분수열합의개수 {

	public int solution(int[] elements) {

		//만들어진 숫자 중복제거를 위한 집합
		Set<Integer> numSet = new HashSet<>();

		//원형 누적합을 위한 배열
		int[] sumArray = new int[elements.length * 2 + 1];
		for(int i = 1; i <= elements.length; i++){
			sumArray[i] = elements[i - 1];
			sumArray[i + elements.length] = elements[i - 1];
		}

		//누적합 계산
		for(int i = 1; i < sumArray.length; i++){
			sumArray[i] += sumArray[i - 1];
		}

		//아래와 같이 j조건을 잡으면 원형배열의 전체 원소를 더한 값이 두번 반복되지만, 해당 문제는 경우의 수가 아닌 더했을떄 나온 수를 중복없이 나타내기
		//떄문에 문제없음.
		for(int i = 1; i <= elements.length; i++){
			for(int j = i; j < i + elements.length; j++){

				int tempSum = sumArray[j] - sumArray[i - 1];

				if(numSet.contains(tempSum)) continue;

				numSet.add(tempSum);
			}
		}

		return numSet.size();
	}

	public static void main(String[] args) {

		Prog_연속부분수열합의개수 s = new Prog_연속부분수열합의개수();

		int[] elements1 = {7,9,1,1,4};
		System.out.println(s.solution(elements1));

	}

}
