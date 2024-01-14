import java.util.*;

class Solution_연속펄스부분수열의합 {
    final long MIN = (long) -100_000 * 500_000 - 1;

    public long solution(int[] sequence) {
        int[] parsePlus = Arrays.copyOf(sequence, sequence.length); // 1로 시작하는 펄스 수열 곱한 배열
        int[] parseMinus = Arrays.copyOf(sequence, sequence.length); // -1로 시작하는 펄스 수열 곱한 배열
        init(parsePlus, 1);
        init(parseMinus, 0);

        if (sequence.length == 1) {
            return Math.max(parsePlus[0], parseMinus[0]);
        }

        long plus = findMax(parsePlus, new long[sequence.length]);
        long minus = findMax(parseMinus, new long[sequence.length]);

        return Math.max(plus, minus);
    }   // end of solution

    /**
     * start 인덱스부터 건너뛰며 -1을 곱해줌
     */
    private void init(int[] parse, int start) {
        for (int i = start; i < parse.length; i += 2) {
            parse[i] *= -1;
        }
    }   // end of init

    /**
     * dp값 중 가장 큰 값 반환
     */
    private long findMax(int[] sequence, long[] dp) {
        long max = MIN;
        dp[0] = sequence[0];
        for (int i = 1; i < sequence.length; i++) {
            dp[i] = Math.max(sequence[i], dp[i - 1] + sequence[i]);
            max = Math.max(max, dp[i]);
        }

        return max;
    }   // end of findMax

}