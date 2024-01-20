import java.util.*;

class Solution_두큐합같게만들기 {
    Queue<Integer> q1 = new ArrayDeque<>();
    Queue<Integer> q2 = new ArrayDeque<>();
    long sum1;
    long sum2;
    int max;

    public int solution(int[] queue1, int[] queue2) {
        sum1 = 0;
        sum2 = 0;
        max = 0;

        initWhileSum(queue1, queue2);   // 큐 세팅 및 배열의 합 구하기

        long half = (sum1 + sum2) / 2;
        if (max > half) {
            return -1;
        }

        int answer = makeSame();

        return answer;
    }   // end of solution

    /**
     * int 배열을 큐로 만들면서 각 큐의 합과 전체 값 중 최댓값 구하기
     */
    private void initWhileSum(int[] queue1, int[] queue2) {
        for (int i = 0; i < queue1.length; i++) {
            q1.add(queue1[i]);
            q2.add(queue2[i]);
            sum1 += queue1[i];
            sum2 += queue2[i];

            if (max < queue1[i]) {
                max = queue1[i];
            }

            if (max < queue2[i]) {
                max = queue2[i];
            }
        }
    }   // end of initWhileSum

    /**
     * 두 큐의 합을 같게 만들기 위해 필요한 작업 횟수 반환
     */
    private int makeSame() {
        int maxCount = q1.size() * 3;    // 최대 작업 횟수
        int count = 0;  // 작업 횟수

        while (sum1 != sum2) {    // 두 큐의 합이 같을때까지 값이 큰곳에서 pop
            count++;
            if (count == maxCount) {  // 두 배열이 위치만 바뀜
                return -1;
            }

            if (q1.isEmpty()) {
                pop(2);
                continue;
            } else if (q2.isEmpty()) {
                pop(1);
                continue;
            }

            if (sum1 > sum2) {
                pop(1);
            } else {
                pop(2);
            }
        }

        return count;
    }   // end of makeSame

    /**
     * queueNum 번호의 큐에서 원소 하나를 빼고 다른 큐에 넣어줌
     */
    private void pop(int queueNum) {
        int temp = 0;
        switch (queueNum) {
            case 1:
                temp = q1.poll();
                sum1 -= temp;
                sum2 += temp;
                q2.add(temp);
                break;
            case 2:
                temp = q2.poll();
                sum2 -= temp;
                sum1 += temp;
                q1.add(temp);
                break;
        }
    }   // end of pop

}