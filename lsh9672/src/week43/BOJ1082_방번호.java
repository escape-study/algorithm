package week43;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디
 *
 * 1. 자리수가 길어야 된다.
 * 2. 앞에 큰수가 나와야 한다.
 *
 * 이 규칙에 따라서 그리디로 계산한다.
 * 먼저 자리수가 길어야 하기 때문에 가격이 가장 싼 것을 기준으로 자리수를 만든다.
 * 주의할 점은 맨 앞에는 0이 올 수 없기 때문에, 맨 앞자리의 경우에는 그 다음 값으로 한다.
 * 이렇게 자리수가 맞춰지면, 맨 앞에 올 수를 구한다.
 * 큰수부터 확인을 하는데, 해당 수를 놓고 남은 금액으로 제일 작은 수로 다 채웠을때 채워지는지 확인해서 되면 두고 아니면 다음 수로 넘어간다.
 *
 */

public class BOJ1082_방번호 {

	private static class Node{
		int price, number;

		public Node(int price, int number){
			this.price = price;
			this.number = number;
		}
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		Node[] infoArray = new Node[N];
		st = new StringTokenizer(br.readLine());

		for(int i = 0; i < N; i++){
			infoArray[i] = new Node(Integer.parseInt(st.nextToken()), i);
		}

		int M = Integer.parseInt(br.readLine()); //총 금액.

		//가격순으로 오름차순 정렬.
		Arrays.sort(infoArray, (node1, node2) -> {
			return node1.price - node2.price;
		});

		//반복문 돌면서 자리수 구하기.
		int len = 0; //자리수
		int tempPrice = 0;
		Node minNode = infoArray[0];

		if(N == 1){
			System.out.println(0);
			return;
		}

		while(tempPrice < M){

			for(int i = 0; i < infoArray.length; i++){


				if(len == 0 && infoArray[i].number == 0) continue;


				tempPrice += infoArray[i].price;
				break;
			}

			if(tempPrice <= M){
				len++;
			}
		}

		//자리수가 0이라면 0
		if(len == 0){
			System.out.println(0);
			return;
		}

		//자리수에 맞게 배정 - 앞에 최대한 큰 수가 와야 됨
		Arrays.sort(infoArray, (node1, node2) -> {
			return node2.number - node1.number;
		});

		int[] result = new int[len];

		//자리수 만큼 반복.
		for(int i = 0; i < len; i++){

			for(Node node : infoArray){

				if(M - node.price < 0) continue;

				//남은 자리수에 가장 싼걸로 채웠을떄,남은 비용을 넘어가지 않는다면,
				if((len - (i + 1)) * minNode.price <= M - node.price){
					M -= node.price;
					result[i] = node.number;
					break;
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for(int str : result){
			sb.append(str);
		}

		System.out.println(sb);

	}
}
