package week01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * 아이디어
 * 여기서 주목해야될 문장은 "적어도" 이다
 * 즉, C를 넘어가도 상관이 없다는 뜻이다.
 * 가령 97까지 계산을 했고, 목표 인원이 적어도 100명을 채우는 것이다.
 * 이때 3명에게 홍보했을때 50원을 내야하고, 50명에게 홍보했을때 2원을 내는 도시가 각각 있다고 가정해보자
 * 이런 경우에 100에 딱 맞게 3명이 아닌 50명을 선탱해도 된다는 뜻이다.
 * 즉 , 모든 경우를 탐색해 볼때, C까지가 아닌, 문제에서 주어진 각 도시별로 가능한 최대 인원인 C+100명일때까지 구해봐야 된다는 뜻이다.
 *
 * 풀이는 다음과 같다.
 * 반복문을 돌면서 입력값을 받는다.
 * 값을 받으면 해당 도시의 인원수부터 최대치인 c+100까지 반복을 한다.
 * 점화식은 다음과 같이 세울 수 있다.
 * dp[i] = Math.min(dp[i], 입력으로 들어온 비용 + dp[i - 입력으로 들어온 인원수])
 *
 * => dp배열의 인덱스는 인원수, 값은 최소 비용을 저장하게 된다.
 */

public class BOJ1106_호텔 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[] dp = new int[C + 101];
        Arrays.fill(dp, 10000001);//Integer.MAX_VALUE로 하면 오버플로우 발생

        dp[0] = 0;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int money = Integer.parseInt(st.nextToken());
            int peopleCount = Integer.parseInt(st.nextToken());

            //입력하면 dp배열 업데이트.
            for(int j = peopleCount; j <= C + 100; j++){

                dp[j] = Math.min(dp[j], money + dp[j - peopleCount]);
            }
        }

        int minValue = Integer.MAX_VALUE;
        for(int i = C; i <= C + 100; i++){
            minValue = Math.min(minValue, dp[i]);
        }

        System.out.println(minValue);
    }
}
