import java.util.Arrays;

class Solution_입국심사 {
    public long solution(int n, int[] times) {
        Arrays.sort(times); // 심사시간 기준으로 오름차순 정렬
        long answer = (long) times[times.length - 1] * n;

        long left = 1, right = answer; // 최악의 경우는 n명 모두 가장 오래 걸리는 심사대에서 심사받는 경우
        while (left <= right) {
            long mid = (left + right) / 2;  // 총 심사시간
            long count = 0;  // 총 심사시간(mid)동안 심사받을수 있는 사람 총합
            for (int i = 0; i < times.length; i++) {
                count += mid / times[i];    // 해당 시간동안 각 심사대에서 심사받을 수 있는 사람 수
            }

            if (count >= n) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }
}
