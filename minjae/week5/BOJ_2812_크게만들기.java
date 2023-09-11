package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_2812_크게만들기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Min, Max, M , K, X , P;
    static int Map[][];
    static boolean checked[][][];

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0},{0,0}};
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        String s = br.readLine();
        Stack<Integer> stack = new Stack<>();
        stack.push(s.charAt(0) -'0');

        for (int i = 1; i < s.length(); i++) {
            int now = s.charAt(i) - '0';
            while (stack.size() != 0 && stack.peek() < now && K != 0){
                stack.pop();
                K--;
            }
            stack.push(now);
        }


        while (K != 0){
            stack.pop();
            K--;
        }

        for (int a : stack){
            System.out.print(a);
        }
    }

}