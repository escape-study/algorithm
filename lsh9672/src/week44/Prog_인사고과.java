package week44;

import java.util.Arrays;

/**
 * 아이디어
 * 정렬을 이용한 풀이
 */
public class Prog_인사고과 {


	public int solution(int[][] scores) {
		int answer = 0;

		//근무태도 내림차순, 같을경우는 동료평가로 오름차순
		//동료평가 최대값을 초기에 첫번째 값으로 잡고, 계속 업데이트한다.
		//반복문을 돌면서 저장된 동료평가 최대값보다 현재 값이 작으면, -1,-1로 만들어서 제외(근무태도로 내림차순 했기 떄문에 동료평가가 낮으면 인센제외)
		int[] wonho = new int[]{scores[0][0], scores[0][1]};

		Arrays.sort(scores, (o1, o2) -> {

			if(o1[0] == o2[0]) return o1[1] - o2[1];

			return o2[0] - o1[0];
		});

		int maxAttitude = scores[0][1];
		for(int i = 0; i < scores.length; i++){

			//현재 사람의 동료 평가 점수가 더 크다면 패스
			if(maxAttitude <= scores[i][1]){

				maxAttitude = scores[i][1];
				continue;
			}

			//원호라면 종료
			if(scores[i][0] == wonho[0] && scores[i][1] == wonho[1]) return -1;

			//원호가 아니고, 이전에 저장된 동료평가 점수보다 현재 점수가 더 작다면 근태, 동료평가 점수 둘다 현재 인원보다 큰 사람이 존재하기 떄문에 -1,-1 로 바꿈
			scores[i][0] = -1;
			scores[i][1] = -1;

		}
		//두 점수를 합산하여 정렬한다. - 원호의 점수에 해당하면 멈춤
		Arrays.sort(scores, (o1, o2) -> {
			return (o2[0] + o2[1]) - (o1[0] + o1[1]);
		});

		for(int i = 0; i < scores.length; i++){
			answer++;

			if((scores[i][0] + scores[i][1]) == (wonho[0] + wonho[1])) break;
		}


		return answer;
	}

	public static void main(String[] args) {

		Prog_인사고과 s = new Prog_인사고과();

		int[][] scores = {{2,2},{1,4},{3,2},{3,2},{2,1}};
		System.out.println(s.solution(scores));
	}
}
