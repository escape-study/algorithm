package week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 팰린드롬을 구하기 위해서 시작인덱스, 끝인덱스로 잡고 하나씩 줄여가며 구하면 시간초과가 난다.
 * 펠린드롬판별에 n/2, 들어오는 질문이 M개만해도 10^3 * 10 ^ 6 = 10^9 로 10억의 반복이 발생한다.
 * 따라는 이문제는 dp를 이용해서 해결해야 한다.
 * dp 배열의 행을 팰린드롬문자의 시작인덱스, 열을 끝인덱스로 잡고, 모든 경우에 대해서 다 구한후에, 입력된 질문으로 배열을 조회해서 출력해준다.
 */
public class BOJ10942_팰린드롬 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] numArray = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++){
            numArray[i] = Integer.parseInt(st.nextToken());
        }

        //dp 배열 설정
        boolean[][] dp = new boolean[N + 1][N + 1];

        //초기값 - 길이1
        for(int i = 1; i <= N; i++){
            dp[i][i] = true;
        }
        //초기 값 - 길이2
        for(int i = 2; i <= N; i++){
            if(numArray[i] == numArray[i - 1]){
                dp[i - 1][i] = true;
            }
        }
        //길이3 이상부터 탐색시작
        for(int i = 3; i <= N; i++){
            for(int j = 1; j <= N - i + 1; j++){
                //새로 탐색하는 위치의 값이 같고, 그 안의 문자열이 팰린드롬이라면 팰린드롬임
                if(numArray[j] == numArray[j + i - 1] && dp[j + 1][j + i - 2]){
                    dp[j][j + i - 1] = true;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            if(dp[start][end]){
                result.append(1);
            }
            else{
                result.append(0);
            }

            result.append("\n");
        }


        System.out.println(result);

    }
}
