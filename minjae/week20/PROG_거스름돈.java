package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PROG_거스름돈 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;


    public static int solution(int n, int[] money) {
        int answer = 0;
        Arrays.sort(money);

        long[] d = new long[n+1];

        for(int i =0; i<=n ;i++){
            if(i % money[0] == 0)
                d[i]=1;
        }

        // 화폐단위 개수별로 for문
        for(int i=1; i<money.length ; i++){
            for(int j=money[i]; j<=n; j++){
                d[j] += d[j-money[i]];
            }
        }

        // 형 변환
        answer = (int)(d[n] % 1000000007);
        return answer;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(solution(5, new int[]{1,2,5}));
    }


}