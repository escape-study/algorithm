import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2589_보물섬 {
	static int N, M, Max = Integer.MIN_VALUE;
	static boolean Map[][];
	static class Node{
		int x;
		int y;
		int dis;
		public Node(int x, int y, int dis){
			this.x = x;
			this.y = y;
			this.dis = dis;
		}
	}
	static int[][] delta = {{1,0},{0,1},{-1,0},{0,-1}};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		Map = new boolean[N][M];

		for(int i = 0 ; i < N; i++){
			String s = br.readLine();
			for(int j = 0 ; j < M ; j++){
				if(s.charAt(j) == 'L'){
					Map[i][j] = true;
				}
			}
		}


		for(int i = 0 ; i < N; i++){
			for(int j = 0 ; j < M ; j++){

				if(Map[i][j]){
					bfs(i,j);
				}

			}
		}

		System.out.println(Max);
	}
	static public void bfs(int x , int y){
		boolean visited[][] =new boolean[N][M];

		Queue<Node> queue = new LinkedList<>();
		queue.add(new Node(x,y,0));
		visited[x][y] = true;

		while(!queue.isEmpty()){
			Node now = queue.poll();
			Max = Math.max(now.dis, Max);

			for(int i = 0 ; i < delta.length ; i++){
				int nextX = now.x + delta[i][0];
				int nextY = now.y + delta[i][1];
				if(nextX < 0 || nextX >= N || nextY <0 || nextY >=M) continue;
				if(!Map[nextX][nextY] || visited[nextX][nextY]) continue;
				queue.add(new Node(nextX,nextY,now.dis+1));
				visited[nextX][nextY] = true;

			}

		}

	}
}