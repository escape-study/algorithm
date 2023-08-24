import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2470 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] solution = new int[N];
        for (int i = 0; i < N; i++) {
            solution[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(solution);
        if (solution[0] < 0 && solution[N - 1] < 0) {   // 용액이 모두 알칼리성
            System.out.println(solution[N - 2] + " " + solution[N - 1]);
        } else if (solution[0] > 0 && solution[N - 1] > 0) {    // 용액이 모두 산성
            System.out.println(solution[0] + " " + solution[1]);
        } else {
            int min = Integer.MAX_VALUE;
            int minLeft = -1, minRight = N;
            int left = 0, right = N - 1;
            while (left < right) {
                int result = solution[left] + solution[right];
                if (Math.abs(min) > Math.abs(result)) {
                    minLeft = left;
                    minRight = right;
                    min = result;
                }

                if (result == 0) {
                    break;
                } else if (result < 0) {
                    left++;
                } else {
                    right--;
                }
            }   // end of while

            System.out.println(solution[minLeft] + " " + solution[minRight]);
        }
    }
}
