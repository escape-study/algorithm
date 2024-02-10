import java.util.*;
class Solution_징검다리건너기 {
    class Stone {
        int index, count;

        public Stone(int index, int count) {
            this.index = index;
            this.count = count;
        }
    }

    public int solution(int[] stones, int k) {
        PriorityQueue<Stone> q = new PriorityQueue<>((a, b) -> b.count - a.count);
        for (int i = 0; i < k; i++) {
            q.add(new Stone(i, stones[i]));
        }

        int answer = q.peek().count;
        int left = 1, right = k;
        while (right < stones.length) {
            if (q.peek().index < left) {
                while (!q.isEmpty() && q.peek().index < left) {
                    q.poll();
                }
            }

            q.add(new Stone(right, stones[right]));

            if (answer > q.peek().count) {
                answer = q.peek().count;
            }

            left++;
            right++;
        }


        return answer;
    }   // end of solution

}