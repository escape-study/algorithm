import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_20437 {
    static String input;
    static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 0; testcase < T; testcase++) {
            input = br.readLine();
            k = Integer.parseInt(br.readLine());
            int len = input.length();
            if (k == 1 || len == 1) {
                sb.append(1 + " " + 1 + "\n");
                continue;
            }

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < len; i++) {
                int temp = search(input.charAt(i), i);
                if (temp == -1) {
                    continue;
                }

                if (temp > max) {
                    max = temp;
                }

                if (temp < min) {
                    min = temp;
                }
            }

            if (min == Integer.MAX_VALUE || max == Integer.MIN_VALUE) {
                sb.append(-1 + "\n");
            } else {
                sb.append(min + " " + max + "\n");
            }
        }   // end of testcase

        System.out.println(sb);
    }   // end of main

    private static int search(char c, int start) {
        int index = start;
        int count = 1;
        while (count != k) {
            index = input.indexOf(c, index + 1);
            if (index == -1) {
                return -1;
            }
            count++;
        }

        return index - start + 1;
    }   // end of search
}