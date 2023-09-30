package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_17281_야구 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Min, Max, M , K, X , P;
    static int Map[][];
    static boolean checked[];

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        N = Integer.parseInt(br.readLine());

        Map = new int[N+1][10];
        checked = new boolean[10];
        Max = Integer.MIN_VALUE;

        for (int i = 1; i <= N ; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 9; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int order[] = new int[10];
        order[4] = 1;
        checked[1] = true;
        sequence(1 , order );
        System.out.println(Max);
    }

    static public void sequence(int cnt , int order[]){
        if(cnt == 10){ // 끝
            Max = Math.max( culculate(order) , Max);
            return;
        }
        if(cnt == 4){
            sequence(cnt+1 ,order);
        }else{
            for (int i = 2; i <= 9 ; i++) {
                if(!checked[i]){
                    checked[i] = true;
                    order[cnt] = i;
                    sequence(cnt+1 , order);
                    checked[i] = false;
                }
            }

        }




    }

    static public int culculate(int[] order){
        int idx = 1;
        int sum = 0;
        for(int i = 1; i <= N ; i++){
            int out = 0;
            int l[] = new int[5];
            int inningScore = 0;
            boolean[] base = new boolean[4];


            while(out < 3) {
                switch(Map[i][order[idx]]) {
                    // 아웃
                    case 0:
                        out++;
                        break;
                    // 1루타
                    case 1:
                        if(base[3]) {
                            inningScore++;
                            base[3] = false;
                        }
                        if(base[2]) {
                            base[3] = true;
                            base[2] = false;
                        }
                        if(base[1]) {
                            base[2] = true;
                        }
                        base[1] = true;
                        break;
                    // 2루타
                    case 2:
                        if(base[3]) {
                            inningScore++;
                            base[3] = false;
                        }
                        if(base[2]) {
                            inningScore++;
                        }
                        if(base[1]) {
                            base[3] = true;
                            base[1] = false;
                        }
                        base[2] = true;
                        break;
                    // 3루타
                    case 3:
                        if(base[3]) {
                            inningScore++;
                        }
                        if(base[2]) {
                            inningScore++;
                            base[2] = false;
                        }
                        if(base[1]) {
                            inningScore++;
                            base[1] = false;
                        }
                        base[3] = true;
                        break;
                    // 홈런
                    case 4:
                        if(base[3]) {
                            inningScore++;
                            base[3] = false;
                        }
                        if(base[2]) {
                            inningScore++;
                            base[2] = false;
                        }
                        if(base[1]) {
                            inningScore++;
                            base[1] = false;
                        }
                        inningScore++;
                        break;
                }

                // 다음 타자로
                idx++;
                // 만약 10번 타자가 되면 다시 1번 타자로 되돌림
                if(idx >= 10) {
                    idx = 1;
                }
            }

            // 해당 이닝에서 얻은 점수를 총 게임 점수에 더해줌
            sum += inningScore;

        }


        return sum;
    }

}