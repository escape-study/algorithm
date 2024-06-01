package week42;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디
 * 날짜를 증가시켜나가는 방식으로 하면 어렵다.
 * 1일차에 할수 있는 과제는 마감일이 1일보다 큰 값들이므로 그중 어떤 것이 최대가 될지 고민해야 한다 => 이렇게 생각하면 dp 처럼 보일수 가 있음.
 * 역순으로 N일차부터 고민을 하게 되면 문제가 쉬운 그리디가 된다.
 * N일차에 할수 있는 과제는 N일과 같거나 큰 마감일을 가졌고, 이전에 처리하지 않은 과제가 된다.
 * 이렇게 되면 선택할 수 있는 과제수가 제한 적이고 줄어들게 되기 때문에,선택가능한 과제중 가장 큰 것을 선택해나가면 최대가 된다.
 */
public class BOJ13904_과제 {

	private static class Node implements Comparable<Node>{

		int d, w;

		public Node(int d, int w){
			this.d = d;
			this.w = w;
		}

		@Override
		public int compareTo(Node node) {
			return node.d - this.d;
		}
	}

	//현재일보다 마감일이 같거나 작으면서, 그중에 가장 높은 점수 추출
	private static int maxScore(Node[] nodeArray, int currentDay){

		int max = -1;
		int index = -1;

		for(int i = 0; i < nodeArray.length; i++){

			if(nodeArray[i] == null || currentDay > nodeArray[i].d) continue;

			if(max < nodeArray[i].w){
				max = nodeArray[i].w;
				index = i;
			}
		}
		if(index != -1) nodeArray[index] = null;

		return max;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		Node[] nodeArray = new Node[N];


		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			nodeArray[i] = new Node(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
		}

		Arrays.sort(nodeArray);

		int totalScore = 0;
		for(int i = N; i > 0; i--){

			int score = maxScore(nodeArray, i);

			System.out.println("i : " + i + ", score : " +score);

			if(score == -1) continue;


			totalScore += score;

		}

		System.out.println(totalScore);
	}
}
