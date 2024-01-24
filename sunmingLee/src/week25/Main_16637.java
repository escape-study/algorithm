import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_16637 {
    static int N, max;
    static char[] input;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        input = br.readLine().toCharArray();

        max = Integer.MIN_VALUE;
        dfs(2, input[0] - '0');
        System.out.println(max);
    }   // end of main

    private static void dfs(int index, int sum) {
        if (index >= N) {
            max = Math.max(max, sum);
            return;
        }

        // 괄호를 치지 않은 경우
        dfs(index + 2, calc(sum, input[index] - '0', input[index - 1]));

        // 괄호를 친 경우
        if (index + 2 < N) {
            // 괄호안을 먼저 계산해주고, 왼쪽 숫자(sum) 와 합산해서 넘겨줌
            int first = calc(input[index] - '0', input[index + 2] - '0', input[index + 1]);
            dfs(index + 4, calc(sum, first, input[index - 1]));
        }

    }   // end of dfs

    private static int calc(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            default:
                return -1;
        }
    }   // end of calc
}