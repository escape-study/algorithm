package week26;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 아이디어
 * 그리디
 * 친구들이 징검다리를 건널떄는 이동할 수 있는 돌중 가장 가까운 곳으로 건너야 한다.
 * 즉, 이동이 가능하다면 1칸, 불가능하면 2칸....k칸까지 건널수 있다는 뜻이다.
 * 매번 사람이 건널때마다 1씩 줄여가면서 시뮬레이션한다면, 배열의 크기가 20만이고, 각 원소는 2억이므로,
 * 모든 징검다리의 돌이 2억의 내구도를 가지면 2억명이 건널수 있다는 뜻이 된다.
 * 따라서 k개의 칸만큼 이동하면서 확인하여, k개의 칸이 모두 0이 되는 값을 구한다 -> 이는 구간중 최대값이 될것이다.
 * 그리고 이 값을 중 최소값을 구하면 된다 -> 최소값이라는 뜻은 가장 먼저 k칸이 0이 된다는 뜻이다.
 * 즉, 그리디로 생각하고 이를 슬라이딩 윈도우로 구현하면 된다.
 * 그렇게 되면, 대략 n의 값을 가지게 된다.
 */
public class Prog_징검다리_건너기 {

    private static class Node {
        int index, value;

        public Node(int index, int value){
            this.index = index;
            this.value = value;
        }
    }

    public int solution(int[] stones, int k) {
        int answer = Integer.MAX_VALUE; //0이 되는 구간들의 최댓값들 중 최소 값.

        Deque<Node> deque = new ArrayDeque<>();
        deque.addLast(new Node(0, stones[0]));

        //1일 경우에는 돌의 내구도만큼 건널 수 있음.
        if(stones.length == 1) answer = stones[0];

        for(int i = 1; i < stones.length; i++){


            //새로운 값을 que에 넣을때.
            //1.현재 넣는 값의 인덱스 - k값보다 첫번째값의 인덱스가 작거나 같으면 제거한다.
            if(deque.peekFirst().index <= i - k){
                deque.pollFirst();
            }
            //2. 마지막 값을 확인해서 현재 값보다 작으면 빼버린다.
            while(!deque.isEmpty() && deque.peekLast().value <= stones[i]){
                deque.pollLast();
            }

            deque.addLast(new Node(i,stones[i]));

            if(i >= k - 1) answer = Math.min(answer, deque.peekFirst().value);

        }

        return answer;
    }

    public static void main(String[] args) {
        Prog_징검다리_건너기 s = new Prog_징검다리_건너기();
        int[] stones = {2, 4, 5, 3, 2, 1, 4, 2, 5, 1};
        int k = 3;
        System.out.println(s.solution(stones,k));
//
//        int[] stones2 = {7,2,8,7,5,2,9};
//        int k2 = 3;
//        System.out.println(s.solution(stones2,k2));

        int[] stones3 = {7,2,8,7,5,2,9};
        int k3 = 1;
        System.out.println(s.solution(stones3,k3));





    }
}
