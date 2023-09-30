package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_21608_상어초등학교 {

    static int delta[][] = {{1,0},{0,1},{-1,0},{0,-1}};
    static int student[][], Map[][], N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        student = new int[N*N][5];
        Map = new int[N][N];

        for(int i = 0 ; i < N*N ; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int k = 0 ; k < 5 ;k++){
                student[i][k] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);

    }
    static void dfs(int cnt){
        if(cnt == N*N){
            Arrays.sort(student, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[0] - o2[0];
                }
            });

//            for(int i = 0 ; i < N ; i++){
//                for(int j = 0 ; j < N ;j++){
//                    System.out.print(Map[i][j] + " ");
//                }
//                System.out.println();
//            }
            print();
            return;
        }
        int blank = -1;
        int friend = -1;

        int nextX = 0;
        int nextY = 0;

        for(int i = N-1; i >= 0 ;i--){
            for(int j = N-1; j >= 0 ;j--){
                if(Map[i][j] != 0) continue;

                int b = 0;
                int f = 0;

                for(int k = 0 ; k < 4; k++){
                    int x = i + delta[k][0];
                    int y = j + delta[k][1];
                    if(x < 0 || x >= N || y < 0 || y >= N) continue;

                    if(Map[x][y] == 0){
                        b++;
                    }else{
                        for(int l = 1; l < 5; l++){
                            if(Map[x][y] == student[cnt][l]){
                                f++;
                                break;
                            }
                        }
                    }
                }

                if(f > friend){
                    nextX = i;
                    nextY = j;
                    friend = f;
                    blank = b;
                } else if (friend == f) {
                    if(blank <= b){
                        nextX = i;
                        nextY = j;
                        blank = b;
                    }
                }

            }
        }

        Map[nextX][nextY] = student[cnt][0];

        dfs(cnt+1);

    }

    static void print() {
        int sum = 0;
        for(int i = 0 ; i < N ;i++){
            for (int j= 0 ; j < N ;j++){
                int f = 0;
                for(int k = 0 ; k < 4; k++) {
                    int x = i + delta[k][0];
                    int y = j + delta[k][1];
                    if (x < 0 || x >= N || y < 0 || y >= N) continue;

                    for(int l = 1; l < 5; l++){
                        if(Map[x][y] == student[Map[i][j]-1][l]){
                            f++;
                            break;
                        }
                    }

                }
                if(f != 0) {
                    sum += Math.pow(10, f - 1);
                }
            }
        }

        System.out.println(sum);
    }


}