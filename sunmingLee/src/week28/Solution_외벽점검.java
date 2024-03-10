package week28;

import java.util.Arrays;
import java.util.Collections;

public class Solution_외벽점검 {
    public static void main(String[] args) {
//        int n = 12;
//        int[] weak = {1, 3, 4, 9, 10};
//        int[] dist = {3, 5, 7};
//        int[] weak = {1, 5, 6, 10};
//        int[] dist = {1, 2, 3, 4};
        int n = 200;
        int[] weak = {0, 100};
        int[] dist = {1, 1};
        Solution_외벽점검 s = new Solution_외벽점검();
        System.out.println(s.solution(n, weak, dist));
    }

    int len, answer;
    int[] weakLong;

    public int solution(int n, int[] weak, int[] dist) {
        len = weak.length;
        weakLong = new int[len * 2 - 1];    // 순회할 수 있는 모든 취약점(한바퀴를 넘을경우 n을 더해서 표현)
        for (int i = 0; i < len; i++) {
            weakLong[i] = weak[i];
            if (i == len - 1) {
                continue;
            }
            weakLong[i + len] = weakLong[i] + n;
        }

        Integer[] dists = new Integer[dist.length];
        for (int i = 0; i < dist.length; i++) {
            dists[i] = dist[i];
        }
        Arrays.sort(dists, Collections.reverseOrder());

        answer = Integer.MAX_VALUE;
        permutation(0, new int[dists.length], new boolean[dists.length], dists);

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }   // end of solution

    /**
     * 순열로 취약지점을 점검할 순서 정하기
     */
    private void permutation(int count, int[] friend, boolean[] visited, Integer[] dists) {
        if (count == dists.length) {
            checkWeak(friend);
        }

        for (int i = 0; i < friend.length; i++) {
            if (visited[i]) {
                continue;
            }

            visited[i] = true;
            friend[count] = dists[i];
            permutation(count + 1, friend, visited, dists);
            visited[i] = false;
        }
    }   // end of permutation

    /**
     * 취약지점을 점검하기 위해 보내야하는 친구의 수 반환
     **/
    private void checkWeak(int[] friend) {
        for (int start = 0; start < len; start++) {
            int count = 0;  // 투입한 친구의 수
            int index = start;  // 현재 점검할 취약지점 위치
            boolean canGo = true;

            loop:
            while (index < start + len) {
                if (count == friend.length) {
                    canGo = false;
                    break;
                }

                int next = weakLong[index] + friend[count]; // 다음 점검 위치
                count++;

                while (next >= weakLong[index]) {
                    index++;
                    if (index == start + len) {
                        break loop;
                    }
                }
            }

            if (!canGo) {
                continue;
            }
            answer = Math.min(answer, count);
        }
    }   // end of checkWeak
}
