package week43;

/**
 * 아이디어
 * 수학
 * 원의 방정식을 이용한다
 * 4분면중 1분면만 구하고 * 4를 한 후. 중복되는 위치들만 제외해준다.
 */
public class Prog_두원사이의정수쌍 {

	public long solution(int r1, int r2) {
		long answer = 0;
		// r1^2 - y^2 <= x^2 < r2^2 - y^2
		for(int i = 1; i <= r2; i++){

			long tempStartY = (long) Math.ceil(Math.sqrt(Math.pow(r1, 2) - Math.pow(i, 2)));
			long tempEndY = (long) Math.sqrt(Math.pow(r2, 2) - Math.pow(i, 2));

			answer += tempEndY - tempStartY + 1;

		}

		return answer * 4;
	}

	public static void main(String[] args) {
		Prog_두원사이의정수쌍 s = new Prog_두원사이의정수쌍();

		int r1 = 2;
		int r2 = 3;
		System.out.println(s.solution(r1, r2));
	}

}
