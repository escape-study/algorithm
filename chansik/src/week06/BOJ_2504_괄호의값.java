package chansik.src.week06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class BOJ_2504_괄호의값 {
    static class Node {
        private char word;
        private int index;

        public Node(char word, int index) {
            this.word = word;
            this.index = index;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = bf.readLine();
        // 합들을 저장할 배열
        int[] score = new int[input.length()];
        Stack<Node> stack = new Stack<>();

        for(int i=0;i<input.length();i++) {
            // 스택이 비어있으면 해당 문자열을 넣고 다음으로 이동처리
            if (stack.isEmpty()) {
                stack.push(new Node(input.charAt(i), i));
                continue;
            }

            Node node = stack.peek();
            char currentWord = input.charAt(i);
            // 이전 단어와 현재 단어가 매칭이 되는 경우
            if ((node.word == '(' && currentWord == ')') || (node.word == '[' && currentWord == ']')) {
                // 현재 단어와 매칭된 단어가 직전의 단어인 경우
                if (node.index + 1 == i) {
                    // '(' 인 경우는 score[앞괄호 인덱스] 에 2를 넣는다.
                    // '[' 인 경우는 score[앞괄호 인덱스] 에 3을 넣는다.
                    score[node.index] = node.word == '(' ? 2 : 3;
                    // score[뒤괄호 인덱스]는 0으로 초기화한다.
                    score[node.index+1] = 0;
                }
                // 현재 단어와 매칭된 단어가 직전의 단어가 아닌 경우 [중간에 매칭된 괄호들이 존재하는 경우]
                else {
                    int sum = 0;
                    // 앞괄호 인덱스 +1 부터 뒷괄호 인덱스 -1 까지의 합을 더한다.
                    for(int j=node.index+1;j<i;j++) sum += score[j];
                    // score[앞괄호 인덱스] = (중간에 매칭된 괄호들의 합) * (가중치['(' => 2 , '[' => 3])
                    score[node.index] = sum * (node.word == '(' ? 2 : 3);
                    // 앞괄호 인덱스 +1 부터 뒷괄호 인덱스 까지 0으로 초기화한다.
                    for(int j=node.index+1;j<=i;j++) score[j] = 0;
                }
                stack.pop();
            }
            // 매칭이 안된다면 스택에 넣는다.
            else stack.push(new Node(currentWord, i));

        }
        System.out.println(!stack.isEmpty() ? 0 : Arrays.stream(score).sum());
    }
}
