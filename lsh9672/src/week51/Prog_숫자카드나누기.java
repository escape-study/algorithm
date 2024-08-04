package week51;

/**
 * 아이디어
 * 수학 + 구현
 * 최대공약수를 구해서 해결하는 문제
 * A의 최대 공약수를 구하고, 최대 공약수로 B의 수들이 나눠떨어지지 않는지 계산한다
 * 반대의 경우도 계산해서 답이 없으면 0, 있으면 최대 공약수를 리턴한다.
 *
 * 최대공약수는 유클리드 호제법을 이용한다.
 */
public class Prog_숫자카드나누기 {


	//유클리드 호제법을 이용한 최대공약수 구하기
	private static int getGCD(int num1, int num2){

		if(num1 % num2 == 0) return num2;

		return getGCD(num2, num1 % num2);
	}

	public int solution(int[] arrayA, int[] arrayB) {
		int answer = 0;

		int gcdA = arrayA[0];
		int gcdB = arrayB[0];
		//arrayA의 최대공약수
		for(int i = 1; i < arrayA.length; i++){
			gcdA = getGCD(gcdA, arrayA[i]);
			gcdB = getGCD(gcdB, arrayB[i]);
		}
		//반복문 돌면서 gcdB로 arrayA수를 전부 나눌 수 없는지 확인.
		boolean flag = true;
		for(int a : arrayA){
			if(a % gcdB != 0) continue;

			flag = false;
			break;
		}

		if(flag) answer = Math.max(gcdB, answer);

		//반복문 돌면서 gcdA로 arrayB수를 전부 나눌 수 없는지 확인.
		flag = true;
		for(int b : arrayB){
			if(b % gcdA != 0) continue;

			flag = false;
			break;
		}

		if(flag) answer = Math.max(gcdA, answer);

		return answer;
	}
	public static void main(String[] args) {
		Prog_숫자카드나누기 s = new Prog_숫자카드나누기();

		int[] arrayA1 = {10, 17};
		int[] arrayB1 = {5, 20};
		System.out.println(s.solution(arrayA1, arrayB1));

		int[] arrayA2 = {10, 20};
		int[] arrayB2 = {5, 17};
		System.out.println(s.solution(arrayA2, arrayB2));

		int[] arrayA3 = {14, 35, 119};
		int[] arrayB3 = {18, 30, 102};
		System.out.println(s.solution(arrayA3, arrayB3));
	}
}
