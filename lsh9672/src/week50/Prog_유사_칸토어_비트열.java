package week50;

/**
 * 아이디어
 *  수학 + 구현
 *  해당 칸토어 비트열을 나열해보면 규칙이 생긴다
 *  1 : 1/1/0/1/1
 *  2 : 4/4/0/4/4
 *  3 : 16/16/0/16/16
 *  각 구간별 1의 수를 표시하면 위의 나열과 같아진다.
 *  각 구간별 1의 수는 4 ^ (n - 1)로 표시되기 때문에,
 *  주어진 구간의 1의 수를 구하기 위해서,위의 구간에 포함이 되는지 확인하면 된다.
 *  가령 2구간 ~ 4구간 이라면, 3구간의 경우에는 1이 존재하지 않기 때문에 패스하고,
 *  2구간, 4구간에 대해서 정확한 위치를 파악한다.
 *  이 위치를 파악하기 위해서는 n - 1번째의 구간을 확인해야 한다.
 *  재귀 호출을 이용해서 반복적으로 구간을 줄여나가며 1의 개수를 카운트 해준다.
 */
public class Prog_유사_칸토어_비트열 {

	//재귀 호출 - n번째에서 각 구간은 n - 1번의 구간으로 표현이 가능하다.
	private static int recursive(int n, long l, long r, long index){

		//n이 0이면 더 작은 구간은 없음.
		if(n == 0) return 1;

		//구간별 수 : 5^(n - 1)
		long countOfPart = (long) Math.pow(5, n - 1);
		int oneCount = 0;//n번째에서의 1의 개수.

		//해당 문제에서 구간은 5개임.
		for(int part = 0; part < 5; part++){

			//가운데 구간, 즉 2번구간이면 0이므로 계산 필요 없기 때문에 패스(구간은 0부터 함.)
			if(part == 2) continue;
			//[l,r] 구간을 벗어나는 구간이면 볼 필요 없음
			if(l > index + ((part + 1) * countOfPart) - 1 || r < index + (part * countOfPart)) continue;

			//해당 구간을 구하기 위해, n - 1를 확인(해당 구간을 다시 5개의 구간으로 나눌 수 있음.)
			//index + (part * countOfPart) 해당 값을 넘겨주는 것은, 작은 구간으로 나눴을때도 l,r의 인덱스와 비교하기 위함.
			oneCount += recursive(n - 1, l, r, index + (part * countOfPart));

		}

		return oneCount;
	}
	public int solution(int n, long l, long r) {

		//구간의 경우, 0이 아닌 1부터 시작하기 때문에 index를 1부터 시작하도록 함.
		return recursive(n, l, r, 1);
	}

	public static void main(String[] args) {
		Prog_유사_칸토어_비트열 s = new Prog_유사_칸토어_비트열();

		int n1 = 2;
		int l1 = 4;
		int r1 = 17;

		System.out.println(s.solution(n1, l1, r1));
	}


}
