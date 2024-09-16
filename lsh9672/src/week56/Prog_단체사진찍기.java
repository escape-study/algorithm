package week56;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 완탐
 * 단순하게 모든 친구들의 줄을 세우는 경우를 재귀탐색을 이용해서 처리한다.
 * 줄을 모두 세웠으면, 문제에서 주어진 조건을 모두 만족하는지 반복문을 돌면서 처리한다.
 * 하나의 조건이라도 만족하지 않으면 종료 처리한다.
 */
public class Prog_단체사진찍기 {

	private final static char[] friends = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};

	private static int result;//총 경우의 수.
	private static boolean[] visited;

	//반복문 돌면서 줄세우기.
	//map으로 친구들의 위치를 저장하면, 조건에 부합하는지 탐색할때, 친구의 위치를 O(1)로 찾을 수 있음.
	private static void dfs(Map<Character, Integer> lineMap, String[] data, int index){

		//줄다세웠으면 조건 확인
		if(index == 8){
			if(check(lineMap, data)) result++;
			return;
		}
		for(int i = 0; i < 8; i++){

			//해당 친구를 놓았다면 패스.
			if(visited[i]) continue;
			visited[i] = true;
			lineMap.put(friends[i], index);

			dfs(lineMap, data, index + 1);

			lineMap.put(friends[i], -1);
			visited[i] = false;
		}

	}

	//줄 서있는게 주어진 조건에 부합하는지 확인.
	private static boolean check(Map<Character, Integer> lineMap, String[] data){


		for(String tmp : data){

			char friend1 = tmp.charAt(0);
			char friend2 = tmp.charAt(2);
			int checkDistance = Character.getNumericValue(tmp.charAt(4));
			char condition = tmp.charAt(3);

			//거리를 이용해서 조건에 부합하는 지 체크
			int friend1Loc = lineMap.get(friend1);
			int friend2Loc = lineMap.get(friend2);
			int distance = Math.abs(friend1Loc - friend2Loc) - 1;

			boolean conditionCheck = false;
			switch(condition){
				case '=':
					conditionCheck = (checkDistance == distance);
					break;
				case '<':
					conditionCheck = (checkDistance > distance);
					break;
				default:
					conditionCheck = (checkDistance < distance);
					break;
			}

			if(!conditionCheck){
				return false;
			}

		}


		return true;
	}

	public int solution(int n, String[] data) {

		//초기 맵은 -1로 처리, 재귀 돌때 삭제 처리 하지 않고 -1로 표기하도록 함.
		Map<Character, Integer> lineMap = new HashMap<>();
		for(int i = 0; i < 8; i++){
			lineMap.put(friends[i], -1);
		}
		result = 0;

		visited = new boolean[8];

		dfs(lineMap,data, 0);


		return result;
	}

	public static void main(String[] args) {

		Prog_단체사진찍기 s = new Prog_단체사진찍기();

		int n1 = 2;
		String[] data1 = {"N~F=0", "R~T>2"};
		System.out.println(s.solution(n1, data1));

		int n2 = 2;
		String[] data2 = {"M~C<2", "C~M>1"};
		System.out.println(s.solution(n2, data2));

	}
}
