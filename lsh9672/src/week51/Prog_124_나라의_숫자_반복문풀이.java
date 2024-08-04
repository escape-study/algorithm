package week51;

/**
 * 아이디어
 * 수학 구현
 * 재귀가 아닌 반복문을 이용해서 풀이한다.
 * 초기에 1,2,3은 각각 1,2,4에 대응되고, 4,5,6은 11,12,14 즉, 앞에오는 수는 N - 1을 3으로 나눈 몫, 뒤에 붙는 수는 나눈 나머지가 된다.
 */
public class Prog_124_나라의_숫자_반복문풀이 {

	public String solution(int n) {
		StringBuilder result = new StringBuilder();
		int[] remain = {4,1,2};

		while(n > 0){
			result.insert(0,remain[n % 3]);
			n = (n - 1) / 3;
		}

		return result.toString();
	}

	public static void main(String[] args) {

		Prog_124_나라의_숫자_반복문풀이 s = new Prog_124_나라의_숫자_반복문풀이();

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
