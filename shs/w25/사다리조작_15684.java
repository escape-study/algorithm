package y2024.이공이사일월.w4;

import java.util.Scanner;

public class 사다리조작_15684 {

	
	static int N, M, H;
	static int [][] board;
	static int ans;
	static boolean flag;
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		N = s.nextInt();
		M = s.nextInt();
		H = s.nextInt();
		
		board = new int[H+1][N+1];
		
		for(int i=0; i<M; i++) {
			int r = s.nextInt();
			int c = s.nextInt();
			
			board[r][c] = 1;			
			board[r][c+1] = 2;			
			
		}
		
		for (int i=0; i<=3; i++) {
			ans = i;
			DFS(0);
			if(flag) {
				break;
			}
			
		}
		if(flag) {
			System.out.println(ans);
		}else {
			System.out.println(-1);
		}
		
	}
	
	
	static void DFS(int d) {
		
		if(d == ans) {
			if(visited()) {
				flag = true;
			}
			return;
		}
		
		for(int i=1; i< H+1; i++) {
			for(int j=1; j< N; j++) {
				
				if(board[i][j]==0 && board[i][j+1] ==0) {
					board[i][j] = 1;
					board[i][j+1] =2;
					DFS(d+1);
					board[i][j] = 0;
					board[i][j+1] =0;
				}
				
			}
		}
		
	}
	
	
	static boolean visited() {
		for(int i=1; i<=N; i++) {
			int nr=0, nc=i;
			
			for(int j=nr; j<=H; j++) {
				if(board[nr][nc]==1) {
					nc++;
				}else if(board[nr][nc]==2) {
					nc--;
				}
				nr++;
			}
			
			if(nc != i) {
				return false;
			}
			
		}
		return true;
	}
	
	
}



