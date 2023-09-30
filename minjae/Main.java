import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , M , Map[][] ,delta[][] = {{1,0},{0,1}, {-1,0},{0,-1}} , Max;


    public static void main(String[] args) throws IOException {

        while (true){
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            Max = Integer.MAX_VALUE;
            if(M == 0 && N == 0)break;

            int cnt = 0;
            int idx = 0;
            List<int[]> list = new ArrayList<>();
            Map = new int[N][M];
            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                for (int j = 0; j < M; j++) {
                    if (s.charAt(j) == '*') {
                        Map[i][j] = 1;
                        list.add(new int[]{i, j});
                        cnt++;
                    }else if (s.charAt(j) == 'x') {
                        Map[i][j] = 2;
                    }else if (s.charAt(j) == 'o'){
                        Map[i][j] = 3;
                        list.add(new int[]{i, j});
                        idx = cnt;
                        cnt++;
                    }
                }
            }


            int matrix[][] = new int[list.size()][list.size()];

            for (int i = 0; i < list.size(); i++) {
                int [][]dis = new int[N][M];
                boolean [][]checked = new boolean[N][M];

                Queue<int[]> queue = new LinkedList<>();
                int start[] = list.get(i);
                queue.add(new int[] {start[0] , start[1]});
                checked[start[0]][start[1]] = true;

                while (!queue.isEmpty()){
                    int now[] = queue.poll();

                    for (int j = 0; j < 4; j++) {
                        int x = now[0] + delta[j][0];
                        int y = now[1] + delta[j][1];

                        if(x < 0 || x >=N || y < 0 || y >= M || checked[x][y] || Map[x][y] == 2) continue;
                        queue.add(new int[]{x , y});
                        dis[x][y] = dis[now[0]][now[1]] + 1;
                        checked[x][y] = true;
                    }
                }

                for (int j = 0; j < list.size(); j++) {
                    if(i == j || matrix[i][j] != 0) continue;

                    int temp[] = list.get(j);
                    matrix[i][j] = dis[temp[0]][temp[1]];
                    matrix[j][i] = dis[temp[0]][temp[1]];
                }
            }
            boolean flag = false;
            for(int i = 0 ; i < list.size() ; i++){
                if( i == idx) continue;
                if(matrix[idx][i] == 0){
                    flag = true;
                    break;
                }
            }

            if(flag){
                System.out.println(-1);
                continue;
            }

            find(idx , 0,0 , matrix , new boolean[list.size()]);

            System.out.println(Max);




        }


    }
    static public void find(int start,int cnt, int result , int Matrix[][] , boolean []visited){
        if (cnt == visited.length-1){
            Max = Math.min(result, Max);
            return;
        }

        visited[start] = true;
        for(int i = 0 ; i < visited.length ; i++){
            if(!visited[i]){
                find(i , cnt+1 , result + Matrix[start][i], Matrix , visited);
            }
        }
        visited[start] = false;

    }


}