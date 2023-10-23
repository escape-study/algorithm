package week11;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 아이디어
 * dp문제
 * 색상의 수를 n이라고 했을,
 * 1번째 부터 N까지의 색상에서 K개를 뽑을 수 있는 경우의 수를 dp[k][N]에 저장한다.
 * N번째에서 할 수 있는 경우는 두가지이다.
 * 1.N번째를 뽑는 경우.
 * 2.N번째를 뽑지 않는 경우.
 *
 * 1번의 경우는 N-1번째를 뽑을수가 없기 때문에, N - 2 번째까지 K-1개를 뽑는 경우의 수가 된다.
 * 2번의 경우에는 N-1번째를 뽑을 수가 있기 때문에 N - 1번째까지 K개를 뽑은 경우의 수가 된다.
 *
 * 즉 N번째 위치에 저장되는 값은, 1번과 2번의 경우가 된다.
 */
public class BOJ2482_색상환 {

    private static final int MOD= 1_000_000_003;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //색의 수
        int N = Integer.parseInt(br.readLine());
        //선택할 색의 수
        int K = Integer.parseInt(br.readLine());

        //선택할 수는 색상수/2를 넘을 수 없다.
        //가령, 4의 경우를 생각해보면 1,2,3,4에서 문제의 조건을 만족하면서 뽑을 수 있는 최대 개수는 2개이다.
        if(K > (N / 2)){
            System.out.println(0);
            return;
        }

        //경우의 수를 저장할 배열
        int[][] dp = new int[K + 1][N + 1]; // 인덱스는 1부터 사용.

        //초기값 설정 - 각 색상(N)까지 1개를 뽑는 경우의 수는 N이다
        for(int i = 1; i <= N; i++){
            dp[1][i] = i;

            //점화식떄문에 넣어준다.
            //1로 초기화 해준다. - 아예 뽑지 않는 경우.
            dp[0][i] = 1;
        }

        //반복문을 돌면서 쌓아간다.

        for(int i = 2; i <= N; i++){

            //1개를 뽑는 경우는 위에서 초기화 해주었기 떄문에 2개 뽑는 경우부터 따지면 된다.
            for(int j = 2; j <= K ; j++){

                dp[j][i] = (dp[j - 1][i - 2] + dp[j][i - 1]) % MOD;
            }
        }


        //최종적으로 값을 뽑을 때는 원형 큐 형태라는 것을 생각해야 한다.
        //N번째를 뽑을때는 1번과도 연결되어있기 때문에 해당 경우를 고려한다.
        //해당 경우를 뽑는 경우와, 뽑지 않는 경우를 더한다.
        System.out.println((dp[K - 1][N - 3] + dp[K][N - 1]) % MOD);

    }
}
