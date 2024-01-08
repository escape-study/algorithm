import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2616 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int total = Integer.parseInt(br.readLine());    // 전체 객차의 수
        int[] trains = new int[total + 1];  // 객차 인덱스까지의 손님수 누적합
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= total; i++) {
            int temp = Integer.parseInt(st.nextToken()); // 객차별 손님의 수
            trains[i] = trains[i - 1] + temp;
        }
        int limit = Integer.parseInt(br.readLine());    // 소형기관차가 끌수있는 객차의 수

        int[][] dp = new int[4][total + 1];
        for (int small = 1; small <= 3; small++) {  // 소형 기관차 번호
            for (int train = small * limit; train <= total; train++) {  // 객차 번호
                dp[small][train] = Math.max(dp[small][train - 1], dp[small - 1][train - limit] + (trains[train] - trains[train - limit]));
            }
        }

        System.out.println(dp[3][total]);
    }   // end of main
}
