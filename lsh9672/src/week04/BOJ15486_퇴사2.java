package week04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 *
 * 전형적인 디피
 * i가 상담에 Ti가 걸린다 하면 비용은 i + Ti 위치에 누적한다.
 * 누적할때는 해당 위치에 이전에 누적된 비용과, 현재까지 누적된 값과 해당비용을 합친 값중 큰 값을 저장하면 된다.
 */

public class BOJ15486_퇴사2 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] dp = new int[N + 51];

        int max = 0;

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            int T = Integer.parseInt(st.nextToken());
            int P = Integer.parseInt(st.nextToken());

            //dp배열의 i번째에는 그 이전까지 구한 최대값을 저장함.
            dp[i] = Math.max(dp[i], max);

            //
            dp[i + T] = Math.max(dp[i + T], dp[i] + P);

            //다음 반복때 dp배열에 기존값과 비교해서 채워넣기 위함.
            max = Math.max(max, dp[i]);
        }

        System.out.print(Math.max(dp[N], max));

    }
}
