import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10986 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] sum = new long[N + 1];   // 합 배열
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= N; i++) {
            sum[i] = sum[i - 1] + Integer.parseInt(st.nextToken());
        }

        long answer = 0;
        long[] remain = new long[M];  // 인덱스 번호와 같은 나머지의 개수
        for (int i = 1; i <= N; i++) {
            int temp = (int) (sum[i] % M);
            if (temp == 0) {
                answer++;
            }
            remain[temp]++;
        }

        for (int i = 0; i < M; i++) {
            if (remain[i] > 1) {
                answer += remain[i] * (remain[i] - 1) / 2;
            }
        }

        System.out.println(answer);
    }
}
