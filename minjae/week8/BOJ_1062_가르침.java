package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1062_가르침 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int  N , M , Max;

    static String anti[];
    static boolean word[];

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        if(M < 5){
            System.out.println(0);
            return;
        }else if(M == 26){
            System.out.println(N);
            return;
        }

        word = new boolean[26];
        anti = new String[N];
        Max = Integer.MIN_VALUE;

        for (int i = 0; i < N; i++) {
            anti[i]  = br.readLine();

        }

        find(0 ,0);

        System.out.println(Max);

    }

    static public void find(int num, int cnt){
        if(cnt == M){
            check();
            return;
        }

        for (int i = num; i < 26; i++) {
            if(!word[i]){
                word[i] = true;
                find(i, cnt + 1 );
                word[i] = false;
            }
        }
    }
    static public void check(){
        int sum = 0;
        for (int i = 0; i < anti.length; i++) {
            boolean flag = true;
            for (int j = 0; j < anti[i].length(); j++) {
                if(!word[anti[i].charAt(j) - 'a']){
                    flag = false;
                    break;
                }
            }

            if(flag){
                sum++;
            }
        }
        Max = Math.max(Max, sum);
    }
}