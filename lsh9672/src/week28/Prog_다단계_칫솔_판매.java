package week28;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 구현
 * 트리를 구성하고, seller가 번 이익의 10프로씩 루트노드로 전달하는 식으로 구한다.
 * 최종적으로 enroll에 있는 사람들이 번 돈이 필요하기 때문에 Map에 사용자 이름으로 노드를 매핑해놓는다.
 * 조직 구성원들의 수가 10000이고, seller가 100,000이라, 한쪽으로 치우쳐진 트리가 만들어지면 리프노드부터 최대 루트까지 n의 시간이 걸리기 떄문에
 * 시간초과가 날것이라고 생각할수도 있다.
 * 하지만, 문제를 보면, 이익의 10프로를 상위 노드에 전달하고, 해당 금액이 1원 미만이면, 전달하지 않고 본인이 가지게 된다.
 * 최대 이익금은 100 * 100으로 10000이다
 * 10프로씩 줄여나가면, 10000 -> 1000 -> 100 -> 10 -> 1, 이므로 많아야 4단계만 거쳐갈 수 있다.
 * 따라서 최악의 경우에도 4 * 100,000 = 40만의 반복만 하면 처리가 가능하다.
 */
public class Prog_다단계_칫솔_판매 {

	//각 사용자별 노드 - 위로 올라가는 루트 노드 정보만 알고 있으면 됨.
	private static class Node{
		int profit;

		Node root;

		public Node(){
			this.profit = 0;
			this.root = null;
		}

		public void addProfit(int profit){
			this.profit += profit;
		}
		public void addRoot(Node root){
			this.root = root;
		}
	}

	//노드 정보를 저장하고 있을 Map
	private static Map<String, Node> memberMap;

	//초기 트리와 맵 구성
	private static void init(String[] enroll, String[] referral){
		memberMap.put("-", new Node());
		for(String member : enroll){
			memberMap.put(member, new Node());
		}

		//루트노드 설정.
		for(int i = 0; i < enroll.length; i++){
			memberMap.get(enroll[i]).addRoot(memberMap.get(referral[i]));
		}

	}

	//이익금의 10프로를 계산하는 메서드
	private static int calculateProfit(int profit){

		return profit / 10;
	}

	//seller의 정보를 받아서 상위노드로 이동하면서 수익금을 추가하는 로직
	private static void logic(String[] seller, int[] amount){

		for(int i = 0; i < seller.length; i++){
			String name = seller[i];
			int money = amount[i] * 100;//번 돈

			int profit = calculateProfit(money); //수익금의 10프로 계산.
			Node currentNode = memberMap.get(name);//판매자 정보노드
			//수익금의 10프로가 0이 나오거나, 루트노드가 null일때까지 반복.
			while(profit > 0){
				//본인 수익에서 10프로 제외하고 합치기.
				currentNode.addProfit(money - profit);

				//루트가 없으면, 본인이 남은거 다먹음.
				if(currentNode.root == null){
					currentNode.addProfit(profit);
					break;
				}

				//루트가 가져갈 수익금
				money = profit;
				profit = calculateProfit(money);
				currentNode = currentNode.root;

			}

			//마지막 금액. 위의 로직에서는 10프로를 계산한 금액이 0이면 종료되기 때문에 마지막으로 남은 금액 더해줌.
			currentNode.addProfit(money);
		}
	}

	public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
		int[] answer = new int[enroll.length];

		memberMap = new HashMap<>();
		init(enroll, referral);

		logic(seller, amount);

		for(int i = 0; i < enroll.length; i++){
			answer[i] = memberMap.get(enroll[i]).profit;
		}

		return answer;

	}

	public static void main(String[] args) {
		Prog_다단계_칫솔_판매 s = new Prog_다단계_칫솔_판매();

		String[] enroll1 = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
		String[] referral1 = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
		String[] seller1 = {"young", "john", "tod", "emily", "mary"};
		int[] amount1 = {12, 4, 2, 5, 10};
		System.out.println(Arrays.toString(s.solution(enroll1,referral1, seller1, amount1)));


		String[] enroll2 = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
		String[] referral2 = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
		String[] seller2 = {"sam", "emily", "jaimie", "edward"};
		int[] amount2 = {2, 3, 5, 4};
		System.out.println(Arrays.toString(s.solution(enroll2,referral2, seller2, amount2)));

	}
}
