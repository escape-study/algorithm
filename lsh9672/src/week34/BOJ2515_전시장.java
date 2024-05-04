package week34;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 그리디 백트래킹으로는 해결불가
 * 모든 경우를 다 해야봐야 알 수 있는데 데이터의 수가 크기 때문에 DP를 고려한다.
 * 디피로 모든 경우를 해보기전에, 그림의 높이순으로 오름차순 정렬해서, 높이가 낮은 그림부터 설치한다.
 *
 * 구할때는 해당위치의 그림을 설치할때와 설치하지 않을때의 최대값을 구하는 식으로 두가지로 나눠서 처리하면 된다.
 * 2차원 배열을 사용함
 * dp[i][0] => i번째 그림을 설치했을떄 최대값
 * dp[i][1] => i번째 그림을 설치하지 않았을 때, 최대값
 * i번째를 구할떄는 max(dp[i - 1][0], dp[i - 1][1]) 이와 같이 구할 수 있다.
 * 그림을 설치 하지 않는다면 그냥 이전까지의 최대값을 가져오면 되지만,
 * 그림을 설치한다면 높이를 고려해야 한다.
 * 해당 그림을 설치하는데 드는 최대의 비용을 구하기 위해서는 , i번째 그림의 높이 - S 이 값보다 더 높은 높이를 가지면 안된다.
 * 이를 찾기 위해서 개수가 많기 때문에 파라메트릭 서치를 이용한다
 * upper bound를 사용하여, 해당하는 높이를 가지는 그림의 상한선을 구해서 처리를 한다.
 */
public class BOJ2515_전시장 {

	//그림 정보
	private static class Node implements Comparable<Node>{
		int height, price;

		public Node(int height, int price){
			this.height = height;
			this.price = price;
		}

		@Override
		public int compareTo(Node node) {

			if(this.height == node.height) return this.price - node.price;

			return this.height - node.height;
		}
	}

	//정보 배열
	private static Node[] infoList;

	//상한선 구하기.
	private static int upperBound(int start, int end, int targetHeight){

		while(start < end){
			int mid = (start + end) / 2;

			if(infoList[mid - 1].height <= targetHeight) start = mid + 1;
			else end = mid;
		}

		return end;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());

		infoList = new Node[N];

		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			int tempHeight = Integer.parseInt(st.nextToken());
			int tempPrice = Integer.parseInt(st.nextToken());
			infoList[i] = new Node(tempHeight, tempPrice);
		}

		//높이순으로 정렬
		Arrays.sort(infoList);

		//dp 저장
		int[][] dp = new int[N + 1][2];

		dp[1][0] = infoList[0].price; //초기값
		for(int i = 2; i <= N; i++){

			int index = upperBound(1, i, infoList[i - 1].height - S);//놓을 위치 구하기.

			//TODO : 이해안되는 부분 -> 왜 i+1이랑 같을때인지....
			//새로 놓을 그림이 이전의 그림들과 비교했을떄 차이가 S이상이라면 그냥 놓으면 됨.
			if(index == i + 1) dp[i][0] = infoList[i - 1].price;
			//그게 아니라면 비교 필요(이전것을 놓았을때랑 안놓았을떄)
			else dp[i][0] = Math.max(dp[index - 1][0], dp[index - 1][1]) + infoList[i - 1].price;

			//그림을 놓지 않는 경우는 그냥 이전의 경우로 보면 됨
			dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][1]);
		}

		System.out.println(Math.max(dp[N][0], dp[N][1]));

	}
}
