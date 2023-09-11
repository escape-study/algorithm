package chansik.src.week05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_2812_크게만들기 {
    public static void main(String[] args) throws IOException {
        /**
         * n 은 최대 500,000 이고 제한시간은 1초
         * O(n) or O(nlogn) 으로 문제를 풀어야함
         * 1. 첫번째 자리수에 올 수 있는 가장 큰 수를 찾는다.
         * 2. Stack를 이용하여 새로 들어오는 수와 직전 수 중 큰 수를 넣는다.
         * 3. 2번 과정을 진행하다 남은 숫자들의 개수와 넣을 수 있는 숫자들의 개수가 같아지면 순서대로 수를 넣는다.
         */
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int size = n-k;
        String input = bf.readLine();

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]) return o1[1] - o2[1];
            return o2[0] - o1[0];
        });
        for(int i=0;i<input.length();i++) pq.add(new int[]{Character.getNumericValue(input.charAt(i)), i});
        int start = 0;
        while(!pq.isEmpty()) {
            int[] info = pq.poll();
            int number = info[0]; int index = info[1];
            if (n - index >= size) {
                start = index;
                break;
            }
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(Character.getNumericValue(input.charAt(start)));

        for(int i=start+1;i<input.length();i++) {
            int number = Character.getNumericValue(input.charAt(i));
            int possibleLength = n - i;
            if (stack.size() + possibleLength == size) {
                stack.push(number);
            } else {
                if (stack.peek() >= number) {
                    if (stack.size() < size) stack.push(number);
                } else {
                    while (true) {
                        if (stack.peek() >= number || stack.size() + possibleLength == size) {
                            stack.push(number);
                            break;
                        }
                        stack.pop();
                    }
                }
            }
        }
        for (int i : stack) sb.append(i);
        System.out.print(sb);
    }
}