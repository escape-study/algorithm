package week51;

/**
 * 아이디어
 * 수학 구현
 * 문제를 보면 124로 표현하고 있다.
 * 이는 3진법으로 나타내는 것과 동일한데,
 * 3진법에서 0,1,2를 각각 1,2,4로 치환해주면, 3진법구하는 것과 다르지 않다.
 *
 */
public class Prog_124_나라의_숫자 {

	private static StringBuilder result;

	private static void recursive(int num){

		if(num == 1) result.append('1');
		else if(num == 2) result.append('2');
		else if(num == 3 || num == 0) result.append('4');
		else{
			recursive((num - 1) / 3);
			recursive(num % 3);
		}
	}

	public String solution(int n) {
		result = new StringBuilder();

		recursive(n);

		return result.toString();
	}

	public static void main(String[] args) {

		Prog_124_나라의_숫자 s = new Prog_124_나라의_숫자();

		int n1 = 1;
		System.out.println(s.solution(n1));

		int n2 = 2;
		System.out.println(s.solution(n2));

		int n3 = 3;
		System.out.println(s.solution(n3));

		int n4 = 4;
		System.out.println(s.solution(n4));

	}
}
