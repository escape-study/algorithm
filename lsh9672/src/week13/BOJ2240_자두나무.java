package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 디비
 * 시간과 움직이는 횟수를 이용해서 2차원 배열을 구성해서 풀이
 * 시간이 흐름에 따라 특정시간의 자두 획득량은 이전 시간대의 자두 획득량에서 누적되는 식이므로, 디피로 해결이 가능하다.
 * 행을 움직이는 횟수, 열을 시간으로 잡고 문제를 해결한다.
 * 이때 중요한 것은 움직이는 횟수의 경우, 횟수%2를 했을때 0이면 1번에 서있고, 1이면 2번에 서있다.
 * 시작점이 1번이기 때문이다.
 * 현재 이동했을때 1번자리인 경우와 2번자리인 경우를 나눈다
 * 각 시간별로 1번자리일때와 2번자리 일 경우 각각 구해준다.
 * 해당 시간에 1번자리에 사과가 떨어지면, 자리는 그대로(이동횟수 그대로) 1초전 값에 사과 하나를 추가 한 값과, 자리가 이동(이동횟수 하나 전)하고 1초전의 값과 비교한다.
 * 이와 같은 방식으로 배열을 채워준다.
 */

public class BOJ2240_자두나무 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        //순서대로 떨어지는 사과 위치를 저장할 배열.
        int[] appleOrderArray = new int[T + 1];
        for(int i = 1; i <= T; i++){
            appleOrderArray[i] = Integer.parseInt(br.readLine());
        }

        //획득한 사과를 저장할 배열.
        int[][] dp = new int[W + 1][T + 1];
        //초기값
        for(int i = 1; i <= T; i++){

            if(appleOrderArray[i] == 1) dp[0][i] = dp[0][i - 1] + 1;
            else dp[0][i] = dp[0][i] = dp[0][i - 1];
        }


        for(int i = 1; i <= W; i++){
            for(int j = 1; j <= T; j++){

                //1번자리라면
                if(i % 2 == 0){

                    //1번에 사과가 떨어지는 경우 - 현재 움직임 횟수에서 1초전과, 한번 덜 움직였을때 두 값을 비교.
                    if(appleOrderArray[j] == 1) dp[i][j] = Math.max(dp[i][j - 1] + 1, dp[i - 1][j - 1]);
                    //2번에 사과과 떨어지는 경우.
                    else dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - 1] + 1);
                }

                //2번자리라면,
                else{
                    //2번에 사과가 떨어지는 경우 - 현재 움직임 횟수에서 1초전과, 한번 덜 움직였을때 두 값을 비교.
                    if(appleOrderArray[j] == 2) dp[i][j] = Math.max(dp[i][j - 1] + 1, dp[i - 1][j - 1]);
                    //1번에 사과과 떨어지는 경우.
                    else dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - 1] + 1);

                }
            }
        }

        System.out.println(dp[W][T]);
    }
}
