import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2461 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] students = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                students[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            Arrays.sort(students[i]);
        }

        int answer = Integer.MAX_VALUE;
        int[] choice = new int[N];  // 인덱스 반에서 고른 학생의 번호
        int minIndex = -1;  // 능력치 최솟값 학생의 반번호
        while (true) {
            int max = -1;
            int min = Integer.MAX_VALUE;

            // 반 돌면서 최댓값, 최솟값 갱신
            for (int i = 0; i < N; i++) {
                if (max < students[i][choice[i]]) {
                    max = students[i][choice[i]];
                }

                if (min > students[i][choice[i]]) {
                    min = students[i][choice[i]];
                    minIndex = i;
                }
            }

            answer = Math.min(answer, max - min);
            choice[minIndex]++;
            if (choice[minIndex] == M) {
                break;
            }
        }

        System.out.println(answer);

    }   // end of main
}