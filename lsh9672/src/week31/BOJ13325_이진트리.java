package week31;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs
 * 후위연산으로 해결한다.
 * 리프노드에서 상위 루트 노드로 값을 넘겨주고. 두 리프노드의 차이를 루트에 저장하면, 그 값이 더해야 하는 값이 된다.
 * 해당 문제를 해결하기 위해서 dp를 이용한다.
 */
public class BOJ13325_이진트리 {

	//트리의 높이
	private static int k;

	//트리 가중치
	private static int[] tree;

	//최종적으로 더해져야 할 값.
	private static int result;
	//후위연산으로 재귀호출하면서 하위 노드의 가중치 차의 절대값을 반환
	private static int dfs(int currentNode){

		//리프노드면 가중치 반환 - 리프노드는 2^k번째부터(인덱스를 0부터 사용하기 때문에 -1)
		if(currentNode >= (int)Math.pow(2, k) - 1){
			result += tree[currentNode];
			return tree[currentNode];
		}

		int leftNodeWeight = dfs(currentNode * 2 + 1);//왼쪽 노드 값
		int rightNodeWeight = dfs(currentNode * 2 + 2);//오른쪽 노드 값

		//증가시킨 만큼 누적.
		result += tree[currentNode] + Math.abs(leftNodeWeight - rightNodeWeight);

		return tree[currentNode] + Math.max(leftNodeWeight, rightNodeWeight);//해당 노드의 가중치 업데이트.

	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		k = Integer.parseInt(br.readLine());
		tree = new int[(int)Math.pow(2, k + 1) - 1]; //트리 가중치 저장

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i < tree.length; i++){
			tree[i] = Integer.parseInt(st.nextToken());
		}

		result = 0;
		dfs(0);

		System.out.println(result);

	}
}
