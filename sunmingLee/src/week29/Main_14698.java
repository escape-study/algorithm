package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_14698 {
    static StringBuilder sb;
    static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < T; testCase++) {
            int N = Integer.parseInt(br.readLine());    // 슬라임의 수
            st = new StringTokenizer(br.readLine());

            if (N == 1) {
                sb.append(1).append("\n");
                continue;
            }

            PriorityQueue<Long> energy = new PriorityQueue<>();
            for (int i = 0; i < N; i++) {
                energy.add(Long.parseLong(st.nextToken()));
            }

            long answer = 1;

            while (energy.size() > 1) {
                long a = energy.poll();
                long b = energy.poll();
                long res = a * b;
                answer *= res % MOD;
                answer %= MOD;
                energy.add(res);
            }

            sb.append(answer).append("\n");
        }   // end of testcase

        System.out.println(sb);
    }   // end of main
}
