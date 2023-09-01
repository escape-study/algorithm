package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2580_스도쿠 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Min, Max, M , K, X , P;
    static int Map[][];
    static boolean checked[][][];

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub

        Map = new int[9][9];
        checked = new boolean[3][10][10];

       for(int i = 0 ; i < 9;i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9 ; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());

                if(Map[i][j] != 0){
                    checked[0][i][Map[i][j]] = true;
                    checked[1][j][Map[i][j]] = true;

                    checked[2][(i/3)*3 + j/3][Map[i][j]] = true;

                }
            }
        }
       findS(0);

    }
    static public void findS(int now){
        if(now == 81){

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(Map[i][j] + " ");
                }
                System.out.println();
            }
            System.exit(0);
            return;
        }
        int x = now/9;
        int y = now%9;


        if(Map[x][y] != 0){
            findS(now+1);
        }else{
            for(int i = 1; i<= 9 ; i ++){
                if(!checked[0][x][i] && !checked[1][y][i] && !checked[2][(x/3)*3 + y/3][i]){
                    checked[0][x][i] = true;
                    checked[1][y][i]  = true;
                    checked[2][(x/3)*3 + y/3][i]  = true;
                    Map[x][y] = i;
                    findS(now+1);
                    Map[x][y] = 0;
                    checked[0][x][i] = false;
                    checked[1][y][i]  = false;
                    checked[2][(x/3)*3 + y/3][i]  = false;
                }
            }
        }


    }

}