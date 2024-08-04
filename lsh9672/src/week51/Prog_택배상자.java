package week51;

import java.util.Stack;

/**
 * 아이디어
 * 자료구조(스택)을 이용한 단순 구현.
 * 스택 두개를 써서 해결 할 수 있다.
 * 1~n까지 상자를 순서대로 처리하는 메인 컨베이어와, 상자를 뺴둘 수 있는 보조 컨베이어.
 *
 * (다른풀이)
 * 내 풀이는 스택2개를 사용해서 진짜 문제 그대로 구현했지만,
 * 스택1개를 이용해서 풀이를 할수도 있음.
 * 서브 컨베이어를 스택으로 두고, order 길이 만큼 반복함.
 * 매 반복마다 서브 컨베이어(스택)에 값을 넣고, 해당 스택이 빌때까지 꺼내면서 꺼낸값을 다음 순번 택배와 맞는지 확인함.
 * 맞다면 계속 개수를 늘리고, 다음 택배를 확인하고, 아니면 반복문을 빠져나와서 다시 스택에 넣는 과정을 반복함.§1
 */
public class Prog_택배상자 {
	public int solution(int[] order) {
		int answer = 0;

		Stack<Integer> mainConveyor = new Stack<>();//메인 컨베이어
		Stack<Integer> subConveyor = new Stack<>();//보조 컨베이어.

		//메인 컨베이어에 택배 추가.
		for(int i = order.length; i > 0; i--){
			mainConveyor.push(i);
		}

		for(int currentOrder : order){

			//현재 두어야 하는 것과, 메인 컨베이어가 같다면 종료
			if(!mainConveyor.isEmpty() && currentOrder == mainConveyor.peek()){
				mainConveyor.pop();
				answer++;
				continue;
			}

			//다르다면 서브에 있는 것을 사용할 수 있는지 확인함.
			if(!subConveyor.isEmpty() && subConveyor.peek() == currentOrder) {

				subConveyor.pop();
				answer++;
				continue;
			}

			boolean flag = true; //반복문이 끝날때까지 돌았는데 같은 것이 없었다면, 그대로 종료하기 위한 플래그
			//다르다면, 같은 값이 나올때 까지 빼서 서브 컨베이어에 넣는다.
			while(!mainConveyor.isEmpty()){

				//다음 꺼낼 것이 두어야 하는 것과 같다면 빼고, 갯수증가 시키고 반복문 종료.
				if(currentOrder == mainConveyor.peek()){
					answer++;
					mainConveyor.pop();
					flag = false;
					break;
				}

				//메인에서 빼서 서브에 넣기.
				int main = mainConveyor.pop();
				subConveyor.push(main);
			}

			if(flag) break;
		}
		return answer;
	}

	public static void main(String[] args) {
		Prog_택배상자 s = new Prog_택배상자();

		int[] order1 = {4, 3, 1, 2, 5};
		System.out.println(s.solution(order1));

		int[] order2 = {5, 4, 3, 2, 1};
		System.out.println(s.solution(order2));
	}
}
