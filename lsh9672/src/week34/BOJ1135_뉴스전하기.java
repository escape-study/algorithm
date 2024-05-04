package week34;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어 (참고함.)
 * 트리 dfs + 그리디
 * 트리를 탐색하는데 모든 가지수를 보는 것이 아닌, 그리디로 생각해서 탐색해야 한다.
 * 직속부하에게 한번에 한사람씩 전화를 한다.
 * 전화를 하는데에는 1분이 걸린다.
 * 자신의 직속부하중에, 가장 많은 간접 부하를 거느린 부하에게 먼저 전화를 해야 한다.
 * 그래야만 또 다른 직속부하에게 연락을 하는 중, 이전에 연락을 받은 부하는 또 자신의 부하에게 연락을 하게 된다.
 * 이러한 방식을 이용하면, 재귀 호출을 이용해서 하위노드를 탐색해 나갈 수 있다.
 * 현재 노드가 연락을 다 끝내는데 걸리는 최소시간은, 하위 노드의 최대길이가 된다.
 *
 * 하위 노드를 다 탐색하면서 최대 길이로 현재노드를 업데이트 하는 식으로 하면 최종적으로 루트노드에 쌓인 값이 연락하는데 걸리는 최소 시간이 된다.
 * (추가)
 * 노드의 깊이가 깊다고 해당 트리가 최대가 아닐수도 있다.
 */
public class BOJ1135_뉴스전하기 {

	//직원수
	private static int N;
	//각 노드별 연락 완료 시간 저장
	private static int[] timeArray;
	//트리를 나타낼 배열리스트
	private static List<Integer>[] tree;

	//dfs 탐색
	private static int dfs(int currentNode){


		//각 서브노드의 깊이를 저장
		for(int nextNode : tree[currentNode]){

			timeArray[nextNode] = 1 + dfs(nextNode);

		}

		//저장한 깊이를 내림차순으로 정렬하고, 깊이가 더 클수록 먼저 연락하는것이 좋음.
		tree[currentNode].sort((node1, node2) -> timeArray[node2] - timeArray[node1]);
		int maxTime = 0;
		for(int i = 0; i < tree[currentNode].size(); i++){
			int nextNode = tree[currentNode].get(i);

			timeArray[nextNode] += i;

			maxTime = Math.max(maxTime, timeArray[nextNode]);

		}

		return maxTime;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine());

		//트리리스트 초기화
		tree = new List[N];
		for(int i = 0; i < N; i++){
			tree[i] = new ArrayList<>();
		}

		//초기 트리 정보 저장
		int[] initTreeInfo = new int[N];
		for(int i = 0; i < N; i++){
			initTreeInfo[i] = Integer.parseInt(st.nextToken());

			if(initTreeInfo[i] == -1) continue;
			tree[initTreeInfo[i]].add(i);
		}
		//완료시간 저장할 배열
		timeArray = new int[N];

		System.out.println(dfs(0));

	}
}
