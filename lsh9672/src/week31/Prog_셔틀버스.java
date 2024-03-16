package week31;

import java.util.PriorityQueue;

/**
 * 아이디어
 * 구현
 * 주어진 시간을 분으로 변경후, 우선순위큐
 * n번 반복을 돌면서 버스가 오는 시간을 기준으로 해당 시간이하의 크루를 전부 추출한다.
 * 가장 마지막 시간을 추출한다.
 */
public class Prog_셔틀버스 {

	//분 -> 시
	private static String minToTime(int minute){

		int tempTime = minute / 60;
		int tempMin = minute % 60;

		return String.format("%02d", tempTime) + ":" + String.format("%02d", tempMin);
	}

	//시 -> 분
	private static int timeToMin(String time){
		String[] tempTime = time.split(":");

		return Integer.parseInt(tempTime[0]) * 60 + Integer.parseInt(tempTime[1]);
	}

	public String solution(int n, int t, int m, String[] timetable) {
		String answer = "";

		//모든 시간 넣기.
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(String time : timetable){
			pq.add(timeToMin(time));
		}

		//배차 횟수만큼 반복.
		int startMinute = timeToMin("09:00");
		int lastCrewMin = 0;//콘이 나올 시간을 저장하기 위해, 마지막 크루의 시간을 저장한다.
		int currentMinute = 0;
		int count = 0;
		for(int i = 0; i < n; i++){
			currentMinute = startMinute + (t * i);

			//주어진 시간까지, 최대 m명의 크루를 추출함.
			count = 0;
			lastCrewMin = 0;

			while(!pq.isEmpty() && pq.peek() <= currentMinute && count < m){

				//다음으로 뽑을 크루의 시간이 주어진 셔틀 시간보다 크면 종료.

				int currentCrewMin = pq.poll();

				//마지막 크루 시간 저장.
				lastCrewMin = currentCrewMin;
				count++;
			}
		}

		//마지막 차에서 최대 탑승인원이 탔다면
		if(count == m) return minToTime(lastCrewMin - 1);
			//마지막 인원보다 1분 빨리 와야 됨.
		//마지막 차에서 최대 탑승인원이 안탔다면, 해당 차가 도착하는시간리턴.
		else return minToTime(currentMinute);

	}

	public static void main(String[] args) {
		Prog_셔틀버스 s = new Prog_셔틀버스();

		int n1 = 1;
		int t1 = 1;
		int m1 = 5;
		String[] timetable1 = {"08:00", "08:01", "08:02", "08:03"};
		System.out.println(s.solution(n1, t1, m1, timetable1));

		int n2 = 2;
		int t2 = 10;
		int m2 = 2;
		String[] timetable2 = {"09:10", "09:09", "08:00"};
		System.out.println(s.solution(n2, t2, m2, timetable2));

		int n3 = 2;
		int t3 = 1;
		int m3 = 2;
		String[] timetable3 = {"09:00", "09:00", "09:00", "09:00"};
		System.out.println(s.solution(n3, t3, m3, timetable3));

		int n4 = 1;
		int t4 = 1;
		int m4 = 5;
		String[] timetable4 = {"00:01", "00:01", "00:01", "00:01", "00:01"};
		System.out.println(s.solution(n4, t4, m4, timetable4));

		int n5 = 1;
		int t5 = 1;
		int m5 = 1;
		String[] timetable5 = {"23:59"};
		System.out.println(s.solution(n5, t5, m5, timetable5));

		int n6 = 10;
		int t6 = 60;
		int m6 = 45;
		String[] timetable6 = {"23:59","23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"};
		System.out.println(s.solution(n6, t6, m6, timetable6));

	}
}
