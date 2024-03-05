package week30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * 아이디어
 * dfs
 * 단순 dfs이지만 주의할 점은 스택을 이용해서 구현하면 탐색결과를 저장할때, 알파벳 역순서대로 정렬되어있도록 해야 한다는 점이다.
 * 주어진 공항을 노드, 항공권을 간선으로 생각하면 그래프로 구성할수 있다
 * 노드보다 간선이 더많이 주어질 수도 있고, 간선보다 노드가 더 많이 주어질수도 있기 때문에 인접리스트 방식으로 구현한다.
 * 주의할 점은 항상 ICN에서 출발한다.
 */
public class Prog_여행경로 {

	//최종적으로 출력할 경로
	private static List<String> pathList;

	//그래프를 저장할 리스트
	private static Map<String, PriorityQueue<String>> graph;

	//방문처리
	private static boolean[] visited;

	//dfs 메서드
	private static void dfs(int count, String current, String result, String[][] tickets){

		//모든 티켓을 다 썼으면 후보 리스트에 추가
		if(count == tickets.length){
			pathList.add(result);
			return;
		}

		for(int i = 0; i < tickets.length; i++) {

			//티켓의 시작이 현재노드이고, 사용하지 않은 티켓이라면 탐색
			if (tickets[i][0].equals(current) && !visited[i]) {
				visited[i] = true;
				dfs(count + 1, tickets[i][1], result + " " + tickets[i][1], tickets);
				visited[i] = false;
			}

		}

	}



	public String[] solution(String[][] tickets) {

		pathList = new ArrayList<>();
		visited = new boolean[tickets.length];

		dfs(0, "ICN", "ICN", tickets);

		pathList.sort(Comparator.naturalOrder());

		return pathList.get(0).split(" ");
	}

	public static void main(String[] args) {
		Prog_여행경로 s = new Prog_여행경로();

		String[][] tickets1 = {{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}};
		System.out.println(Arrays.toString(s.solution(tickets1)));

		String[][] tickets2 = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}};
		System.out.println(Arrays.toString(s.solution(tickets2)));

		String[][] tickets3 = {{"ICN","AAA"},{"ICN","BBB"},{"BBB","ICN"}};
		System.out.println(Arrays.toString(s.solution(tickets3)));
	}
}
