package week28;

import java.util.LinkedList;


/**
 * 아이디어
 * 완탐
 * 각 친구들을 취약점에 배치하고 탐색하는 것을 고려.
 * 원형으로 하면 중복으로 이미 탐색한 지점에 사람을 배치하는 것을 판단하기 어려움
 * 따라서 원형의 벽을 직선형태로 만들어서 생각.
 * 배치 할때마다 거리상 어디까지 처리가 되는지 누적하고, 다음 배치떄 해당 위치를 넘어서면 다음 위치에 배치하는 식으로 처리 가능
 * 배치할 친구들이 최댜 8명이라 배치하는데에는 문제가 없음.
 * weak 배열에서 각 시작점에 따라 배열을 일자 형태로 정렬해줘야 함
 * => 배열길이가 길지 않아서 매번 반복문을 돌려서 처리해도 되지만, arrayDeque를 이용해서 하나씩 빼고 뒤에 넣는 식으로 하면 효율적임
 * 배열로 반복해서 최악의 경우라도 대략, 각 기준점을 기준으로 최대길이만큼 반복해도,각 기준점 별로 15번반복, 이를 15번 반복이므로 225번만 반복해주면 됨
 * 배치 순서에 따라서 달라지기 때문에 순열이다.
 * 각 시작점을 다르게 한 weak에 마다 경우의 수를 계산 해줘야 하기 때문에 8! * 15 => 대략 60만이 나온다
 */
public class Prog_외벽_점검 {

	//최소의 친구수
	private static int result;

	//취약지점 정보에서 각 시작점 별로 처리하기 위해 arrayDeque로 설정.
	private static LinkedList<Integer> weakDeque;

	//방문처리
	private static boolean[] visited;

	//뽑은 친구와, 위치값을 보고 다음 위치 뽑기.
	private static int getNextIndex(int[] dist, int weakIndex, int distIndex){


		int nextDist = weakDeque.get(weakIndex) + dist[distIndex];
		for(int i = weakIndex; i < weakDeque.size(); i++){

			//처음으로 취약점 위치가 현재 측정한 거리보다 작으면 해당 인덱스 리턴.
			if(weakDeque.get(i) > nextDist){
				return i;
			}
		}

		//없으면 마지막 값 리턴.
		return weakDeque.size();
	}
	//재귀 호출 함수.
	private static void recursive(int weakIndex, int[] dist, int people){

		//이전에 누적된 사람보다 현재 사람이 많거나 같으면 패스.
		if(result <= people) return;

		//현재 탐색 거리가, 마지막 취약지점보다 크거가 같으면 인원 업데이트.
		if(weakIndex >= weakDeque.size()){

			result = Math.min(result, people);
			return;
		}

		//모든 사람을 다 했는데 안되면 패스
		if(people >= dist.length) return;

		//각 위치에 사람 뽑기.
		for(int i = 0; i < dist.length; i++){

			if(visited[i]) continue; //뽑은 사람이면 패스
			visited[i] = true;

			//사람을 뽑을때마다. 해당 취약 지점에 배치하고, 다음 위치는 어디인지 확인.
			//현재위치에 다음 사람 위치 넣어서 체크.
			int nextIndex = getNextIndex(dist, weakIndex, i);

			recursive(nextIndex, dist, people + 1);

			visited[i] = false;
		}

	}


	public int solution(int n, int[] weak, int[] dist) {

		//weak 배열을 ArrayDeque로 만들기.
		weakDeque = new LinkedList<>();
		for(int i = 0; i < weak.length; i++){
			weakDeque.add(weak[i]);
		}

		//최소 친구수
		result = 9;

		//출발 기준점을 바꾼 리스트 만들고 각각에 대해 재귀호출.
		for(int i = 0; i < weak.length; i++){
			visited = new boolean[dist.length];

			//맨 첫번째 값을 시작점으로 이동시켜봄.
			recursive(0, dist, 0);

			//앞에꺼 빼서 맨 뒤에 두기.
			weakDeque.addLast(weakDeque.pollFirst() + n);

		}

		//최종적으로 탐색한 결과가 9명 업데이트 되지 않음.
		if(result == 9) result = -1;

		return result;
	}

	public static void main(String[] args) {
		Prog_외벽_점검 s = new Prog_외벽_점검();

		// int n1 = 12;
		// int[] weak1 = {1, 5, 6, 10};
		// int[] dist1 = {1, 2, 3, 4};
		// System.out.println(s.solution(n1, weak1, dist1));
		//
		int n2 = 12;
		int[] weak2 = {1, 3, 4, 9, 10};
		int[] dist2 = {3, 5, 7};
		System.out.println(s.solution(n2, weak2, dist2));

		//
		// int n3 = 100;
		// int[] weak3 = {1, 2, 3, 50};
		// int[] dist3 = {5, 10};
		// System.out.println(s.solution(n3, weak3, dist3));

	}
}
