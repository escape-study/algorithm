package y2024.이공이사일월.w4;
//돼지생키...
import java.util.Scanner;


public class 욕심쟁이판다_1937 {
	
    static int n,m;
    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {-1, 1, 0, 0};
    static int[][] arr;
    static int[][] dp;
    static int ans = 0;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        n = s.nextInt();
        arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = s.nextInt();
            }
        }
        
        dp = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                ans = Math.max(DFS(i,j), ans);
            }
        }
        System.out.println(ans);
    }
    
    
    public static int DFS(int x, int y) {
        if (dp[x][y] != 0) {
            return dp[x][y];
        }
        dp[x][y] = 1;
        for (int i = 0; i < 4; i++) {
            int nr = x + dr[i];
            int nc = y + dc[i];
            if(nr >= 0 && nc >= 0 && nr < n && nc < n){
                if(arr[nr][nc] > arr[x][y])
                    dp[x][y] = Math.max(dp[x][y], DFS(nr,nc) + 1);
            }
        }
        return dp[x][y];
    }
}