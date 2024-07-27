package week49;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어(참고함)
 * 그리디 + 유니온 파인드
 * 우선 비행기는 자신이 갈 수 있는 게이트로 오고, 이미 비행기가 있다면 차선책을 선택한다.
 * 이때, 차선책이란 Gi 번째 게이트보다 작은 값들이고, 이를 사전에 저장해두어야 한다.
 * 저장시에는 유니온 파인드를 이용해서, 매번 한칸씩 줄여나가며 판단하여 시간이 약 n^2이 나오는 것을 주의해야 한다.
 * 즉 처음으로 2번에 오면 2로 가고 유니온 파인드를 이용해서 부모 노드에 차선책인 1을 저장해둔다.
 * 그러면 동일하게 2로 또 오게 되면, 1로 가면 된다.
 * 만약에 차선책으로 저장된 부모노드가 0이라면 더 이상 비행기를 착륙시킬 수 없다는 뜻이므로 종료하며 된다.
 */
public class BOJ10775_공항 {

	private static int[] parents;
	private static int find(int node){

		if(parents[node] == node) return node;


		return parents[node] = find(parents[node]);
	}

	private static void union(int a, int b){

		int nodeA = find(a);
		int nodeB = find(b);

		if(nodeA != nodeB){
			parents[nodeB] = nodeA;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int G = Integer.parseInt(br.readLine());
		int P = Integer.parseInt(br.readLine());
		parents = new int[G + 1]; //게이트의 차선책을 저장하는 방식.
		for(int i = 1; i <= G; i++){
			parents[i] = i;
		}

		int result = 0;

		for(int i = 0; i < P; i++){
			int airplane = Integer.parseInt(br.readLine());

			//비어있는 게이트 찾기.
			int emptyGate = find(airplane);

			//빈 게이트가 0번이면 둘 곳이 없다는 뜻
			if(emptyGate == 0) break;

			result++;//비행기수 증가
			union(emptyGate - 1, emptyGate);//현재 위치에 두었으면 -1한 위치를 차선책으로 해서 union 수행

		}

		System.out.println(result);
	}
}
