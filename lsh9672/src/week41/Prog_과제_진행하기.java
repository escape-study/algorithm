package week41;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * 아이디어
 * 구현
 * 단순하게 구현하면 된다.
 * 시작 시간 순으로 정렬하고 하나씩 보면된다.
 * 다음 과제의 시작시간과 현재 과제의 종료시간을 비교해서 다음 과제의 시작시간이 더 빠르면, 현재 과제를 스택에 넣는다.
 * 만약 현재 과제의 종료시간보다 다음 과제의 시작시간이 같거나 느리면 현재 과제를 저장하고, 스택을 확인한다.
 * 스택에 과제가 있으면 꺼내서 진행한다
 *
 * 위와 같은 방법으로 풀기 위해서 시간을 전부 분으로 변경한다.
 *
 * (주의사항)
 * 종료시간으로 처리하면 안된다.
 * 시작시작에서 얼마나 걸리는지를 나타내는 것이지
 * 시작시간 + 걸리는 시간 = 종료시간으로 두고, 중간에 멈춘것이라도 종료시간보다 다음 시작시간이 더 크니까 무조건 된다라고 해버리면 틀린다.
 */
public class Prog_과제_진행하기 {

	//과제 정보를 저장할 객체
	private static class Node implements Comparable<Node>{

		String name;
		int startTime, remainTime;

		public Node(String name, int startTime, int remainTime){
			this.name = name;
			this.startTime = startTime;
			this.remainTime = remainTime;
		}


		//파라미터로 얼마나 흘렀는지 확인.
		public void updateRemain(int time){
			this.remainTime -= time;
		}

		@Override
		public int compareTo(Node node) {

			//모든 과제의 시작시간이 다르기 때문에 해당 조건으로만 정렬해도 됨.
			return this.startTime - node.startTime;
		}

	}


	//시간을 분으로 변환.
	private static int timeToMin(String time){
		String[] tempTimeSplit = time.split(":");

		return Integer.parseInt(tempTimeSplit[0]) * 60 + Integer.parseInt(tempTimeSplit[1]);
	}

	public String[] solution(String[][] plans) {
		String[] answer = new String[plans.length];

		//시작 시간 순으로 정렬된 과제객체
		PriorityQueue<Node> homeWork = new PriorityQueue<>();
		for(String[] plan : plans){
			homeWork.add(
				new Node(
					plan[0],
					timeToMin(plan[1]),
					Integer.parseInt(plan[2])
				)
			);
		}


		//멈춰둔 과제 저장 -> 스택
		Stack<Node> stopHomeWork = new Stack<>();

		int index = 0;
		while(!homeWork.isEmpty()){

			Node currentNode = homeWork.poll();

			//큐가 비어있으면 현재 노드 넣고 종료
			if(homeWork.isEmpty()){
				answer[index] = currentNode.name;
				index++;
				break;
			}

			//큐가 비어있지 않으면 다음 노드 확인
			Node nextNode = homeWork.peek();

			//(다음 노드의 시작시간) == (현재 노드 종료시간) 이라면 현재노드를 종료과제에 넣고, 새로 시작하는 과제부터 처리함.
			if(nextNode.startTime == (currentNode.startTime + currentNode.remainTime)){
				answer[index] = currentNode.name;
				index++;
			}

			//(다음 노드의 시작시간) < (현재 노드 종료시간) 이라면 현재과제를 멈추고 스택에 넣음.
			else if(nextNode.startTime < (currentNode.startTime + currentNode.remainTime)){

				//남은시간 업데이트
				currentNode.updateRemain(nextNode.startTime - currentNode.startTime);
				stopHomeWork.add(currentNode);
			}

			//(다음 노드의 시작시간) > (현재 노드 종료시간)
			else {

				//현재과제를 넣음.
				answer[index] = currentNode.name;
				index++;

				int tempRemainTime = nextNode.startTime - (currentNode.startTime + currentNode.remainTime);


				//멈춘 과제들을 하나씩 꺼내면서 비교
				while(!stopHomeWork.isEmpty()){

					Node stopNode = stopHomeWork.pop();

					//멈춘 노드의 남은 시간 <= 다음 노드와 현재노드의 시간차이.
					if(stopNode.remainTime <= tempRemainTime){
						answer[index] = stopNode.name;
						tempRemainTime -= stopNode.remainTime;
						index++;
					}

					//멈춘 노드의 남은 시간 > 다음 노드와 현재노드의 시간차이 이면  멈춘 노드의 남은 시간을 제외하고 다시 스택에 넣음.
					else{
						stopNode.updateRemain(nextNode.startTime - (currentNode.startTime + currentNode.remainTime));
						stopHomeWork.add(stopNode);
						break;
					}
				}
			}
		}

		//멈춘 과제를 하나씩 꺼내서 처리함
		while(!stopHomeWork.isEmpty()){
			answer[index] = stopHomeWork.pop().name;
			index++;
		}

		return answer;
	}
	public static void main(String[] args) {

		Prog_과제_진행하기 s = new Prog_과제_진행하기();
		// String[][] plans1 = {{"korean", "11:40", "30"}, {"english", "12:10", "20"}, {"math", "12:30", "40"}};
		// System.out.println(Arrays.toString(s.solution(plans1)));
		//
		// String[][] plans2 = {{"science", "12:40", "50"}, {"music", "12:20", "40"}, {"history", "14:00", "30"}, {"computer", "12:30", "100"}};
		// System.out.println(Arrays.toString(s.solution(plans2)));
		//
		// String[][] plans3 = {{"aaa", "12:00", "20"}, {"bbb", "12:10", "30"}, {"ccc", "12:40", "10"}};
		// System.out.println(Arrays.toString(s.solution(plans3)));

		String[][] plans4 = {{"a", "09:00", "30"}, {"b", "09:10", "20"}, {"c", "09:15", "20"}, {"d", "09:55", "10"}, {"e", "10:50", "5"}};
		System.out.println(Arrays.toString(s.solution(plans4)));
	}
}
