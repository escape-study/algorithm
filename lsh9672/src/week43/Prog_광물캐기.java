package week43;

import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 완탐
 * 광물은 정해진 순서대로 캐야 하기 때문에 그리디라고 볼 수 없음
 * 다이아부터 캐려고해도 순서상 앞에 철이나 돌이 나오면 그것부터 캐야 함.
 * 즉 모든 경우를 다 해야 봐야 됨.
 * 곡괭이 중에 하나를 선택해서 5개를 연속으로 캐고, 그 다음 곡괭이를 선택하는 식으로 진행함.
 * 미네랄의 수가 최대 50개지만, 한번에 5개씩 건너뛰기 떄문에 10개정도로 반복에는 많은 갯수가 필요하지 않음.
 */

public class Prog_광물캐기 {

	//최종 피로도.
	private static int minFatigue;

	//곡갱이 명 : 인덱스
	private static Map<String, Integer> pickaxMap;

	//내구도 정보
	private static int[][] fatigueArray;

	private static void init(){
		pickaxMap = new HashMap<>();
		fatigueArray = new int[][]{{1,1,1},{5,1,1},{25,5,1}};

		pickaxMap.put("diamond", 0);
		pickaxMap.put("iron", 1);
		pickaxMap.put("stone", 2);

	}
	//소모 내구도 계산
	private static int useFatigue(int offset, String[] minerals, int pick){

		int result = 0;

		for(int i = offset; i < offset + 5; i++){

			if(minerals.length <= i) break;

			result += fatigueArray[pick][pickaxMap.get(minerals[i])];

		}

		return result;
	}
	//곡갱이를 다 썼는지 확인
	private static boolean check(int[] picks){

		for(int i = 0; i < picks.length; i++){
			if(picks[i] > 0) return false;
		}

		return true;
	}

	//완탐돌릴 재귀함수
	//param =>  totalFatigue : 총 피로도, mineralOffset : 다음 탐색할 미네랄 위치
	private static void dfs(int totalFatigue, int mineralOffset, int[] picks, String[] minerals){

		//저장된 피로도보다, 현재 피로도가 더 크면 볼필요 없음
		if(minFatigue <= totalFatigue) return;

		//미네랄을 다 캤으면 더 볼필요 없음
		if(minerals.length <= mineralOffset || check(picks)) {
			minFatigue = Math.min(minFatigue, totalFatigue);
			return;
		}



		//곡괭이 선택
		for(int i = 0; i < picks.length; i++){

			//다 쓴 곡괭이는 패스
			if(picks[i] == 0) continue;

			picks[i]--;
			dfs(totalFatigue + useFatigue(mineralOffset, minerals, i), mineralOffset + 5, picks, minerals);
			picks[i]++;
		}
	}
	public int solution(int[] picks, String[] minerals) {
		minFatigue = Integer.MAX_VALUE;

		init();
		dfs(0, 0, picks, minerals);

		return minFatigue;
	}

	public static void main(String[] args) {
		Prog_광물캐기 s = new Prog_광물캐기();

		int[] picks1 = {1, 3, 2};
		String[] minerals1 = {"diamond", "diamond", "diamond", "iron", "iron", "diamond", "iron", "stone"};
		System.out.println(s.solution(picks1, minerals1));

		int[] picks2 = {0, 1, 1};
		String[] minerals2 = {"diamond", "diamond", "diamond", "diamond", "diamond", "iron", "iron", "iron", "iron", "iron", "diamond"};
		System.out.println(s.solution(picks2, minerals2));
	}
}
