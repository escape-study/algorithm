package week51;

/**
 * 아이디어
 * 백트래킹
 * 민수가 위치한 층이 시작점이고, 다음으로 이동할 노드를 0~8로 보면된다.
 * 최대 층이 10^8이므로 0부터 8승까지의 합을 계산해서 다음 노드를 구하면 되고, 음수도 가능하기 때문에 18개가 나온다.
 * 최소 마법의 돌 개수를 구하는 것 이므로, 가지치기를 이용해서 백트래킹으로 구현하게 되면 좀 더 효율적으로 구할 수 있게 된다.
 *
 * (추가)
 * 숫자가 너무 크기 떄문에 재귀로 하면 스택 오버플로우가 난다.
 * 10의 배수로 이동이 가능하기 떄문에, 각 자리별로 수를 +하는게 이득일지 -하는게 이득일지 판단하는 식으로 풀어나간다
 * 6,7,8,9이면 올리는게 이득이고, 1,2,3,4이면 내리는게 이득을 보게 된다.
 * 5인 경우는 다음자리수를 확인해야 하는데, 다음 자리수가 5이면 올리면 이득을 보게 되고(6이되므로)
 * 4이하의 경우에는 내리면 이득을 보게 된다.
 *
 * => 백트래킹 문제보다는 구현문제이다.
 *
 * (다른 사람 풀이)
 * 방식은 동일하지만, 숫자를 문자열로 변환하는 식으로 하는 것이 아닌, mod 연산을 이용해서 해결했다.
 * 올림 숫자의 경우에도, 별도로 만드는 것이 아닌, 10을 해서 처리하였다.
 * 즉 계산할떄마다 /10이 되어서, 내가 확인하고자 하는 현재 수는 항상 1의 자리에 있게 된다.
 *
 */
public class Prog_마법의_엘리베이터 {

	public int solution(int storey) {

		int answer = 0;

		String strNum = "0" + storey; //각 자리수 확인을 위해 문자열로 변경. - 앞에 0을 붙여서 맨 처음 자리 계산을 편하게 만듦

		int upperNum = 0;//올림 수.

		for(int i = strNum.length() - 1; i >= 0; i--){

			int currentNum = Character.getNumericValue(strNum.charAt(i)) + upperNum; //현재 숫자 가져오기

			upperNum = 0;//올림 수 초기화.
			//현재 값이 10이면 올림수 추가 하고 currentNum을 0으로 만듦
			if(currentNum == 10){
				currentNum = 0;
				upperNum = 1;
			}

			//0이면 볼 필요 없음
			if(currentNum == 0) continue;

			//0~4이면 숫자 내리기
			if(currentNum <= 4){
				answer += currentNum;
			}

			//6~9이면 숫자 올리기(올림수 1로 만들어야 함.)
			else if(currentNum >= 6 && currentNum <= 9){
				answer += 10 - currentNum;
				upperNum = 1;


			}
			//5일때는 다음 자리를 확인해야 함.
			else{
				int nextNum = Character.getNumericValue(strNum.charAt(i - 1));

				if(nextNum <= 4){
					answer += currentNum;
				}
				else{
					answer += 10 - currentNum;
					upperNum = 1;
				}
			}

		}


		return answer;
	}

	public static void main(String[] args) {
		Prog_마법의_엘리베이터 s = new Prog_마법의_엘리베이터();

		int storey1 = 16;
		System.out.println(s.solution(storey1));

		int storey2 = 2554;
		System.out.println(s.solution(storey2));
		int storey3 = 485;
		System.out.println(s.solution(storey3));

	}
}
