package week48;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 아이디어
 * 그리디 + 구현
 * 단순 구현같지만, 무적권을 언제 사용해야 되는지가 중요하다.
 * 최대한 많은 라운드를 진행하고 싶으면 최대한 보유한 병사를 많이 남겨야 하고,
 * 그러기 위해서는 적의 수가 많을때 무적권을 사용해야 한다.
 * 적의 수를 내림차순으로 정렬해두고, 앞에서부터 무적권의 수만큼 적의 수가 몇일때 몇개를 쓸수 있는지 체크해둔다
 * 가령 내림차순으로 정렬했을때, [5,4,4,4,3,2,1] 이고, 무적권의 수가 3개 주어지면, {5 : 1, 4: 3} 맵으로 저장해둔다
 * 그러면 라운드를 진행하면서 적의 수를 맵에서 조회해서 있으면 처리하면 된다.
 * 만약에 맵에 저장된 무적권에 해당하는 적의 수가 아니면 그냥 빼주면 된다.
 *
 * (수정)
 * 미리 내림차순으로 구해두고 거기에 만 맞추면 안된다.
 * 우선 무적권 없이 쭉 진행하다가, 더 이상 적군을 처리할 병력이 없고, 무적권이 남아있으면 이전에 처리한 적군의 수 중 가장 큰 수를 무적권으로 처리하고,
 * 병사수를 부활 시켜서 계속 진행한다.
 *
 */
public class Prog_디펜스_게임 {

	public int solution(int n, int k, int[] enemy) {
		int answer = 0;

		//처리한 적의 수 저장
		PriorityQueue<Integer> pq = new PriorityQueue<>((o1,o2) -> {

			return o2 - o1;
		});


		int soldierCount = n; //아군 수.
		int passCount = k; //무적권 개수
		for(int enemyCount : enemy){

			if(soldierCount - enemyCount >= 0){
				soldierCount -= enemyCount;
				pq.add(enemyCount);
			}
			//현재 아군으로 적 처리가 불가능하고 무적권이 있으면 이전에 처리한 것들중 가장 큰것과 현재 처리할 적군을 비교해서 더 큰 쪽을 무적권으로 처리.
			else if(passCount > 0){

				//pq가 비어있지 않으면 둘 중에 더 큰쪽을 사용.
				if(!pq.isEmpty()){

					int temp = pq.poll();
					//이전에 처리한 것들이 더 클때.
					if(temp > enemyCount){
						soldierCount += temp; //이전에 처리 한 값은 무적권으로 처리하도록 변경.
						pq.add(enemyCount); //현재 적을 무적권 없이 처리.
						soldierCount -= enemyCount;
					}

					//이번에 처리해야 할 것이 더 클때,
					else{
						pq.add(temp);//큐에 다시 넣기.
					}
				}

				passCount--; //무적권 소모.
			}
			else break;
			answer++;
		}

		return answer;
	}

	public static void main(String[] args) {
		Prog_디펜스_게임 s = new Prog_디펜스_게임();

		int n1 = 7;
		int k1 = 3;
		int[] enemy1 = {4, 2, 4, 5, 3, 3, 1};
		System.out.println(s.solution(n1, k1, enemy1));

		int n2 = 2;
		int k2 = 4;
		int[] enemy2 = {3, 3, 3, 3};
		System.out.println(s.solution(n2, k2, enemy2));
	}
}
