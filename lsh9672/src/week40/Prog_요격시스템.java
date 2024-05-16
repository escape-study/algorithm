package week40;

import java.util.PriorityQueue;

/**
 * 아이디어
 * pg를 활용한 그리디
 * 개수가 적거나, 열린구간 (s,e)에서 s,e의 값이 작았다면, 누적합을 이용한 완탐을 사용할 수 있지만,
 * 해당 문제에서는 너무 크기 때문에 그리디를 이용한다.
 * e를 기준으로 오름차순 정렬하는 pg에 넣는다.
 * 하나씩 꺼내면서 현재e와 큐에 담긴 다음 객체의 s와 비교를 한다.
 * 현재e < 다음s 라면, 겹치는 구간이므로 그 다음 큐의 값을 반복해서 확인한다.
 * 현재e >= 다음s 라면, 겹치는 구간이 안생기므로 미사일 한번발사로 전부 요격이 불가능하기 떄문에 발사 가능 미사일 수를 늘리고,
 * 현재 객체를 다음 객체로 업데이트 하고 동일하게 비교해나간다.
 *
 * (추가)
 * 배열이므로 굳이 pq사용하지 않고 정렬로 해결하면 훨씬 빠르다.
 */
public class Prog_요격시스템 {

	public int solution(int[][] targets) {
		int answer = 0;

		PriorityQueue<int[]> missilePQ = new PriorityQueue<>(
			(o1, o2) -> {
				return o1[1] - o2[1];
			}
		);

		for(int[] target : targets){
			missilePQ.add(target);
		}

		int[] currentNode = missilePQ.poll();

		answer = 1;

		while(!missilePQ.isEmpty()){


			int[] nextNode = missilePQ.peek();

			if(currentNode[1] <= nextNode[0]){
				answer++;
				currentNode = missilePQ.poll();
			}
			else{
				missilePQ.poll();
			}

		}


		return answer;
	}

	public static void main(String[] args) {
		Prog_요격시스템 s = new Prog_요격시스템();

		int[][] targets = {{4,5},{4,8},{10,14},{11,13},{5,12},{3,7},{1,4}};
		System.out.println(s.solution(targets));
	}
}
