import java.util.*;

class Solution_도넛과막대그래프 {
    class Node {
        int in, out;

        public Node(int in, int out) {
            this.in = in;
            this.out = out;
        }

        public void addIn() {
            this.in++;
        }

        public void addOut() {
            this.out++;
        }
    }

    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        Map<Integer, Node> node = new HashMap<>(); // key : 정점번호, value : 진입, 진출 차수
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            if (!node.containsKey(start)) {
                node.put(start, new Node(0, 0));
            }
            if (!node.containsKey(end)) {
                node.put(end, new Node(0, 0));
            }

            node.get(start).addOut();
            node.get(end).addIn();
        }

        node.forEach((key, value) -> {
            // 진출 간선이 2개 이상이고 진입간선은 없으면 생성 정점
            if (value.out >= 2 && value.in == 0) {
                answer[0] = key;
            }

            // 진입간선만 있고 진출간선이 없는건 막대그래프의 끝점
            else if (value.out == 0) {
                answer[2]++;
            }

            // 진입간선과 진출간선이 2개씩 있으면 8자모양 그래프의 중심점
            else if (value.in >= 2 && value.out >= 2) {
                answer[3]++;
            }
        });

        // 도넛그래프 개수 = 전체 그래프의 수(= 생성 정점의 진출간선 ) - 막대그래프 - 8자그래프
        answer[1] = node.get(answer[0]).out - answer[2] - answer[3];
        return answer;
    }
}