package week50;

/**
 * 아이디어
 * 수학 + 구현
 * 단순하게 반복문 돌리면서 나온 좌표의 원점으로부터의 거리가 d를 넘지 않는지 확인 하는 방법은,
 * 데이터가 최대 100만이므로 N^2이 나올수 있다.
 * 좌표간의 거리를 잘 계산해보면 한쪽의 좌표값을 알면 나머지 좌표의 경우에는 수식으로, d까지의 거리를 알 수 있다.
 * 즉 x를 알면 나올수 있는 최대 y를 구할 수 있고, 그 안에 k배수가 몇개 들어있는지는 단순 나눗셈으로 계산이 가능하다
 * 이때, 최대 y를 구할때, 거리공식에 의해서 소수점이 나올 수 있지만, k배이므로 좌표는 정수만 나오기 때문에 소수점은 버림해서 처리해도 된다.
 */

public class Prog_점찍기 {

	public long solution(int k, int d) {
		long answer = 0;


		for(int x = 0; x <= d; x+=k){

			long y = (long) Math.sqrt(Math.pow(d, 2) - Math.pow(x, 2));

			answer += (y / k) + 1;

		}


		return answer;
	}

	public static void main(String[] args) {
		Prog_점찍기 s = new Prog_점찍기();

		int k1 = 2;
		int d1 = 4;
		System.out.println(s.solution(k1, d1));

		int k2 = 1;
		int d2 = 5;
		System.out.println(s.solution(k2, d2));
	}
}
