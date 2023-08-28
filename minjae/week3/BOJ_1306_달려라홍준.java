import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.util.*;

public class BOJ_1306_달려라홍준 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int N , Min, Max, M , K, X , P;

	static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};

	static int Map[][];
	static boolean checked[];

	static class SegmentTree{
		int[] tree;
		int treeSize;

		SegmentTree(int arrSize){
			//트리 높이 구하기
			int h = (int)Math.ceil(Math.log(arrSize)/ Math.log(2)) + 1;
			//높이를 통해 배열 사이즈 구하기
			this.treeSize = (int)Math.pow(2, h);
			// 배열 생성
			tree = new int[treeSize];
		}

		/*
		 *  arr = 원소 배열
		 *  node = 현재노드
		 *  start = 현재 구간 배열 시작
		 *  end = 현재 구간 배열 끝
		 */
		int init(int[] arr , int node, int start, int end){
			// 시작과 끝이 같다 = leaf노드
			// 원소 배열 값 그대로 삽입
			if(start == end){
//                System.out.println(node + " : " + arr[start]);
				return tree[node] = arr[start];
			}else{
				//leaf노드가 아닐경우 자식노드 합 삽입
				return tree[node] = Math.max(init(arr, node*2 , start ,(start + end)/ 2)
						,init(arr, node*2 + 1, (start+ end)/2 + 1 , end));
			}
		}

		/*
		 *  arr = 원소 배열
		 *  node = 현재노드
		 *  start = 현재 구간 배열 시작
		 *  end = 현재 구간 배열 끝
		 *  left = 원하는 누적합의 시작
		 *  right = 원하는 누적합의 끝
		 */
		long sum(int node , int start , int end , int left , int right){
			if(end < left || right < start){
				return Integer.MIN_VALUE;
			}else if (left <= start && end <= right){
				return tree[node];
			}else{
				return Math.max(sum(node*2 , start , (start+ end)/ 2, left, right)
						,sum(node*2+1 , (start+end)/2 +1 , end, left, right));
			}
		}

		int maxNum(int node , int start , int end , int left , int right){
			//리프 노드일 경우 해당 값을 반환
			if(end == start){
//                System.out.println(node + " " + start + " " + end + " : " + tree[node]);
				return tree[node];
			}else if(end < left || right < start){
				//범위를 벗어나버릴 경우
				return Integer.MIN_VALUE;
			}else{
				return Math.max(maxNum(node*2 , start , (start+ end)/ 2, left, right)
						,maxNum(node*2+1 , (start+end)/2 +1 , end, left, right));
			}
		}


	}

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());


		int arr[] = new int[N+1];

		for(int i = 0 ; i < N ;i++){
			arr[i+1] = Integer.parseInt(st.nextToken());
		}
		SegmentTree seg = new SegmentTree(N);
		seg.init(arr, 1 , 1 , N);
		StringBuilder sb = new StringBuilder();
//        Arrays.stream(seg.tree).forEach(System.out::println);
		for(int i = M ;  i <= N - M + 1 ; i++){
//            System.out.println((i - M + 1) + " " + (i + M -1));
			sb.append(seg.sum(1,1,N,i - M + 1,i + M -1) + " ");
		}

		System.out.println(sb);




	}
}