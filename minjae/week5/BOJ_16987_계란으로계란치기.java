package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class BOJ_16987_계란으로계란치기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Min, Max, M , K, X , P;
    static int Map[][];
    static boolean checked[];

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        N = Integer.parseInt(br.readLine());

        Map = new int[N][2];
        checked = new boolean[N];
        Max = Integer.MIN_VALUE;
        for (int i = 0; i < N ; i++) {
            st = new StringTokenizer(br.readLine());
            Map[i][0] = Integer.parseInt(st.nextToken());
            Map[i][1] = Integer.parseInt(st.nextToken());
        }



        egg(0);
        System.out.println(Max);


    }

    static public void egg(int cnt){
        if(cnt == N){ // 끝
            int result = 0;
            for (int i = 0 ; i < N ;i++){
                if(checked[i]){
                    result++;
                }
            }
            Max = Math.max(Max, result);
            return;
        }
        if(!checked[cnt]){ // 손에 든 계란이 안 깨졌을경우
            boolean flag = false;
            for(int i = 0 ; i < N ;i++){
                if(i != cnt && !checked[i]){
                    flag = true;
                    int handEgg = Map[cnt][0];
                    int groundEgg = Map[i][0];

                    Map[cnt][0] -= Map[i][1];
                    Map[i][0] -= Map[cnt][1];
                    checked[cnt] = Map[cnt][0] <= 0;
                    checked[i] = Map[i][0] <= 0;

                    egg(cnt + 1);

                    Map[cnt][0] = handEgg;
                    Map[i][0] = groundEgg;
                    checked[cnt] = false;
                    checked[i] = false;
                }
            }
            if (!flag){  // 깰 계란이 없을때
                egg(cnt + 1);
            }
        }else{ // 손에 든게 꺠졌을 경우
            egg(cnt + 1);
        }


    }

}