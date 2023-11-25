package week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 정확히 M개의 구간을 선택해야 한다
 * 구간에 속한 수들의 총합이 최대가 되려고하면 모든 경우를 다해봐야 한다
 * 특정 위치까지 구간을 1개만드는 경우와, 2개만드는경우.... 이와 같이 구간의 크기를 늘려가면서 해봐야 한다.
 * 이때 구간이 연속되어야 하기 떄문에 구간의 합계산시에 빠르게 계산하기 위해서 누적합을 이용해야 한다.
 * 각 수들의 합은 아무리 커봐야 32767*100 이므로 int로도 처리가 가능하다.
 */
public class BOJ2228_구간나누기 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        //구간 합을 위해 수들의 누적합을 구하는 배열.
        int[] numArray = new int[N + 1];
        for(int i = 1; i <= N; i++){
            int num = Integer.parseInt(br.readLine());
            numArray[i] = numArray[i - 1] + num;
        }

        //dp 배열 - 행이 M(구간 수), 열이 해당 수의 인덱스
        int[][] dp = new int[M + 1][N + 1];
        for(int i = 1; i <= M; i++){
            Arrays.fill(dp[i], 32767 * (-1) * 101);
        }

        //초기값
        dp[1][1] = numArray[1]; //1위치에서 구간 1개를 사용하는 경우는 한가지임.

        //구간수를 하나씩 늘려봄.
        for(int i = 1; i <= M; i++){
            //배열의 현재위치에서 나올수 있는 구간들 중 최대 값을 구함.
            for(int j = 2; j <= N; j++){

                dp[i][j] = dp[i][j - 1]; //현재 위치가 구간에 포함이 안되는 경우 이전의 값을 저장 해줌

                int minK = 0;
                if(i == 1){
                    minK = -1;
                }


                for(int k = j - 2; k >= minK; k--){

                    if(k < 0) dp[i][j] = Math.max(dp[i][j], numArray[j]);
                    else dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + (numArray[j] - numArray[k + 1]));

                }
            }
        }

        int result = Integer.MIN_VALUE;

        for(int i = 1; i <= N; i++){
            result = Math.max(result, dp[M][i]);
        }

        System.out.println(result);
    }
}
