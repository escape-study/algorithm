package week47;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 누적합 + 정렬
 * 데이터 개수가 20만이라서 n^2이면 시간초과이다.
 * 특정 공의 점수를 구하기위해서는,
 * 1. 자신보다 작은 값
 * 2. 자신과 다른 색
 * 2가지를 동시에 만족해야 한다.
 * 1번은 정렬을 통해서 자신보다 작은 값을 구할 수 있다.
 * 이때 누적합을 이용하여 매번 다른 공을선택할때의 연산을 줄일 수 있다.
 * 특정한 볼의 크기보다 작은 크기의 볼들을 누적시킴과 동시에 자신보다 작은 공들의 색깔별 크기를 누적시킨다.
 * 자신보다 작은 공의 합 - 같은 색깔공의 합 이 답이 된다.,
 */
public class BOJ10800_컬러볼 {

	private static class Node implements Comparable<Node>{
		int index, color, size;

		public Node(int index, int color, int size){
			this.index = index;
			this.color = color;
			this.size = size;
		}


		@Override
		public int compareTo(Node node) {

			if(this.size == node.size) {

				if(this.color == node.color) return this.index - node.index;

				return this.color - node.color;
			}

			return this.size - node.size;
		}

		@Override
		public String toString() {
			return "Node{" +
				"index=" + index +
				", color=" + color +
				", size=" + size +
				'}';
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		Node[] nodes = new Node[N]; //정보 배열.
		int[] sizeSumArray = new int[N + 1];//크기 누적합 배열
		int[] colorSumArray = new int[N + 1];// 색깔별 크기 합


		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			nodes[i] = new Node(
				i,
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
		}

		Arrays.sort(nodes);

		int lastSumIndex = 1;//마지막 누적합을 구한 인덱스 위치
		int[] resultArray = new int[N];//최종적으로 값을 저장할 배열

		for(int i = 0; i < N; i++){

			//마지막으로 구한 위치에서 최대 현재 위치까지 누적합을 이어서 구함
			//단, 구하는 과정에서 자신과 같은 수가 나오면 누적하지 않고, 종료(작은 수일때만 구함.)
			while(lastSumIndex - 1 <= i){

				//현재 크기와 같은 크기면 멈춤
				if((lastSumIndex - 1 == i) || (nodes[lastSumIndex - 1].size == nodes[i].size)) break;

				// System.out.println(lastSumIndex);
				sizeSumArray[lastSumIndex] = nodes[lastSumIndex - 1].size + sizeSumArray[lastSumIndex - 1];

				colorSumArray[nodes[lastSumIndex - 1].color] += nodes[lastSumIndex - 1].size;

				lastSumIndex++;
			}

			resultArray[nodes[i].index] = sizeSumArray[lastSumIndex - 1] - colorSumArray[nodes[i].color];

		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append(resultArray[i]).append("\n");
		}
		System.out.println(sb);
	}
}
