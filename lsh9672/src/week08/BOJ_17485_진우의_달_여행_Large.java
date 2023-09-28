package week08;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp를 이용하여 처리.
 * 특정위치에 올수 있는 값들 중 최소가 되는 값을 저장.
 * 예를 들어 문제 예시에서 2,1이라고 한다면, 해당위치로 올 수 있는 경우의 수는, 6가지 이다.
 * 같은 방향으로 연속해서 이동은 불가능 하기 때문에, 현재 행에서 -2행 위치에서 계산해야 한다.
 *
 * 5,5 배열에서 2,2로 오기 위한 경우를 나열하면 다음과 같다.
 * 1. (0,1) -> (1,2) -> (2,2)
 * 2. (0,1) -> (1,1) -> (2,2)
 * 3. (0,2) -> (1,1) -> (2,2)
 * 4. (0,2) -> (1,3) -> (2,2)
 * 5. (0,3) -> (1,2) -> (2,2)
 * 6. (0,3) -> (1,3) -> (2,2)
 *
 * 3번째 칸부터 계산을 해주면 된다.
 *
 * (수정)
 * 위의 해설은 1%에서 틀렸다
 * 위의 방법으로 하면, 확인이 안되는 부분이 있다.
 * i를 구할 때, i-2위치 값은, dp 배열에서 가져오는데, 해당값이 어떤 방향으로 진입한 값인지 알수 없어,
 * 다음 연산시에 연속된 방향의 값을 선택하는 경우가 생긴다.
 * 이를 해결하기 위해서 3차원 배열로 해결한다.
 * 기존 값만 표시한 2차원 배열에서 차원을 추가해서 이전에 진입한 방향도 표시하여 처리하도록 한다.
 *
 */
public class BOJ_17485_진우의_달_여행_Large {

    //행
    private static int N;
    //렬
    private static int M;
    //지도
    private static int[][] maps;
    //누적할 dp 배열
    private static int[][][] dp;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maps = new int[N][M];
        dp = new int[N][M][3]; // 0: 왼쪽 아래, 1: 아래, 2: 오른쪽 아래.

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                for(int dir = 0; dir < 3; dir++){
                    dp[i][j][dir] = Integer.MAX_VALUE;
                }
            }
        }

        //최대 값 출력.
        int result = Integer.MAX_VALUE;

        //초기 값 저장 - 첫행
        for(int i = 0; i < M; i++){
            for(int j = 0; j < 3; j++){
                dp[0][i][j] = maps[0][i];
            }
        }


        //디피 계산 - 두번째 행부터 시작.
        for(int i = 1; i < N; i++){
            for(int j = 0; j < M; j++){

                //0방향으로 들어올때,
                if(j + 1 < M) dp[i][j][0] = maps[i][j] + Math.min(dp[i - 1][j + 1][1], dp[i - 1][j + 1][2]);

                //1방향으로 들어올때,
                dp[i][j][1] = maps[i][j] + Math.min(dp[i - 1][j][0], dp[i - 1][j][2]);

                //2방향으로 들어올때,
                if(j - 1 >= 0) dp[i][j][2] = maps[i][j] + Math.min(dp[i - 1][j - 1][0], dp[i - 1][j - 1][1]);

            }
        }


        for(int i = 0; i < M; i++){
            for(int dir = 0; dir < 3; dir++){

                result = Math.min(result, dp[N - 1][i][dir]);
            }
        }

        System.out.println(result);

    }
}
