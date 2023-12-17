import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1781 {
    static class Problem {
        int index, deadline, cupramen;

        public Problem(int index, int deadline, int cupramen) {
            this.index = index;
            this.deadline = deadline;
            this.cupramen = cupramen;
        }
    }

    static int N, answer;
    static PriorityQueue<Problem> deadlineQueue, cupramenQueue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        deadlineQueue = new PriorityQueue<>((o1, o2) -> {   // 1차 정렬. 데드라인 오름차순, 같은 데드라인이면 컵라면이 많은 순으로
            if (o1.deadline == o2.deadline) {
                return o2.cupramen - o1.cupramen;
            }
            return o1.deadline - o2.deadline;
        });

        cupramenQueue = new PriorityQueue<>((o1, o2) -> o1.cupramen - o2.cupramen); // 2차 정렬. 컵라면 오름차순

        StringTokenizer st;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int deadline = Integer.parseInt(st.nextToken());
            int cupramen = Integer.parseInt(st.nextToken());
            deadlineQueue.add(new Problem(i, deadline, cupramen));
        }

        while (!deadlineQueue.isEmpty()) {
            Problem cur = deadlineQueue.poll();
            if (cupramenQueue.size() < cur.deadline) {    // 현재 문제를 풀어도 컵라면을 얻을 수 있음
                cupramenQueue.add(cur);
            } else { // 현재 문제가 데드라인을 넘김
                Problem temp = cupramenQueue.peek();    // 풀기로 했던 문제

                // 지금 문제(cur)가 풀기로 했던 문제(temp)보다 컵라면을 더 준다면 지금 문제를 푸는게 이득임
                if (temp.cupramen < cur.cupramen) {
                    cupramenQueue.poll();
                    cupramenQueue.add(cur);
                }
            }
        }

        answer = 0;
        while (!cupramenQueue.isEmpty()) {
            answer += cupramenQueue.poll().cupramen;
        }

        System.out.println(answer);
    }   // end of main
}