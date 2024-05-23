package week41;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 아이디어
 * 그리디
 * pq를 만든다.
 * 큐 크기가 0이면 현재 룸이 없는것이므로 add해서 추가한다.
 * 큐 크기가 1이상이면, 새로운 예약을 처리할때, 앞에서 부터 확인한다.
 * 큐에는 종료시간(종료시간은 예약 시간 + 10 분이다)을 담는다.
 * 새로운 예약을 확인할때, peek한 값이 새로운 예약의 시작시간보다 크다면, 방이 새로 필요한 것이다.
 * 방이 새로 필요하면 add를 하고, peek한 값이 새로운 예약의 시작시간보다 작다면, 해당 값을 새로운 예약의 종료시간 + 10분으로 변경해서 다시 큐에 넣어준다.
 * 최종적으로 모든 예약을 처리한 후에 pq의 크기가 필요한 방의 수이다.
 */
public class Prog_호텔대실 {

	//예약 객체
	private static class Node implements Comparable<Node>{
		int startTime, endTime;

		public Node(int startTime, int endTime){
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@Override
		public int compareTo(Node node) {

			if(this.startTime == node.endTime) return this.endTime - node.endTime;

			return this.startTime - node.startTime;
		}
	}

	//시간 -> 분으로 변경
	private static int timeToMin(String time){
		String[] timeSplit = time.split(":");

		return Integer.parseInt(timeSplit[0]) * 60 + Integer.parseInt(timeSplit[1]);
	}

	public int solution(String[][] book_time) {
		int answer = 0;

		Node[] bookMin = new Node[book_time.length];
		for(int i = 0; i < book_time.length;i++){
			bookMin[i] = new Node(
				timeToMin(book_time[i][0]),
				timeToMin(book_time[i][1])
			);
		}

		Arrays.sort(bookMin); //시작시간이 빠른 순으로.

		PriorityQueue<Integer> hotelQueue = new PriorityQueue<>(); //각 객실의 종료시간이 들어감.

		//시작시간이 빠른것부터 하나씩 꺼내서 확인.
		for(Node currentNode : bookMin){


			//pq가 비어있거나, peek 값이 현재 시작시간보다 크면 새로운 객실이 필요함.
			if(hotelQueue.isEmpty() || hotelQueue.peek() > currentNode.startTime){
				hotelQueue.add(currentNode.endTime + 10);
			}

			//비어있지 않고, peek한 값이 시작시간보다 작거나 같다면 해당 객실써도 됨.(해당 객실 제거하고 새로 넣기)
			else{
				hotelQueue.poll();
				hotelQueue.add(currentNode.endTime + 10);
			}

		}


		//PQ에 남아있는 수의 개수가 객실 수가 됨.
		return hotelQueue.size();
	}

	public static void main(String[] args) {
		Prog_호텔대실 s = new Prog_호텔대실();

		String[][] book_time1 = {{"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"}};
		System.out.println(s.solution(book_time1));

		String[][] book_time2 = {{"09:10", "10:10"}, {"10:20", "12:20"}};
		System.out.println(s.solution(book_time2));

		String[][] book_time3 = {{"10:20", "12:30"}, {"10:20", "12:30"}, {"10:20", "12:30"}};
		System.out.println(s.solution(book_time3));
	}
}
