package week03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * pq를 활용한다.
 * M칸에서 시작하고, 시야의 범위가 M -1 이므로 한칸을 이동하면 무조건 칸 하나가 버려지고, 다음 칸이 추가된다(추가는 안될 수도 잇다.)
 * 초기에 칸을 넣어두고, 이동할때 빠지는 칸을 버려준다. pq에서 해당 칸을 알 수 없기 때문에 방문 처리를 하는 식으로 처리해준다.
 *
 * (추가) 목표위치가 시작 위치보다 작을때, 이 케이스는 인덱스가 작은 쪽에서 큰쪽으로 증가하는 식이 아닌 반대로 가야 한다고 생각했는데,
 * 제출해보니, 케이스가 없는 것으로 보아, 방향은 작은 인덱스 -> 큰인덱스 인듯 하다.
 */

public class BOJ1306_달려라홍준_pq풀이 {

    //값과 삭제처리 되었는지 확인할 객체
    private static class Node{
        private int value;
        private boolean isDelete;

        public Node(int value){
            this.value = value;
            this.isDelete = false;
        }
        public void deleteNode(){
            this.isDelete = true;
        }

        public int getValue() {
            return value;
        }

        public boolean isDelete() {
            return isDelete;
        }
    }

    private static int N;//칸수
    private static int M;//시야 범위

    private static Node[] maps;//값(빛의 세기), 삭제처리 유무를 저장할 배열

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int startLocation = M - 1;
        int targetLocation = N - M; //목표 위치

        maps = new Node[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            maps[i] = new Node(Integer.parseInt(st.nextToken()));
        }

        //노드를 담고, 최대 값을 뽑을 pq선언
        PriorityQueue<Node> pq = new PriorityQueue<Node>((o1,o2) -> o2.getValue() - o1.getValue());

        //초기 범위의 값들을 큐에 넣기
        for(int i = startLocation - (M - 1); i <= startLocation + (M - 1); i++){

            //전체 칸을 넘어가면 종료.
            if(i >= N) break;

            pq.add(maps[i]);
        }

        //두가지의 경우로 나뉘게 되는데, 1. 목표위치가 시작 위치보다 작을때, 2. 목표 위치가 시작 위치보다 클때

        StringBuilder result = new StringBuilder();
        result.append(pq.peek().getValue()).append(" ");

        int start = 0;
        int end = 0;

        //목표위치가 시작위치보다 클때.
        for(int index = startLocation + 1; index <= targetLocation; index++){

            start = index - (M - 1);
            end = index + (M - 1);

            //전진 방향쪽 값 넣기 - N이상이면 패스
            if(end < N){
                pq.add(maps[end]);
            }

            //전진 반대방향 값 빼기
            maps[start - 1].deleteNode();

            //다음 최대값 찾기 - 삭제처리된 것은 버리고 삭제처리 안된값만 뽑기.
            while(pq.peek().isDelete()){
                pq.poll();
            }

            result.append(pq.peek().getValue()).append(" ");
        }

        System.out.println(result);

    }
}
