package week24;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 아이디어
 * 구현
 * 빼고 넣는 위치는 고정이다.
 * 맨앞의 값을 빼고, 넣을때는 무조건 뒤에 넣는다.
 * 각 큐의 합을 구해두고, 두 크기를 맞추기 위해서, 더 큰쪽에서 빼서 작은 쪽에 넣으면서 두 수의 합을 맞춰나간다.
 * 점점 두수가 같아지도록 근사치로 처리가 가능하다.
 * 단, 두 큐가 완전히 교체 되면 같은 수로 만들수 없다는 뜻이다
 * 이를 위해서 각 큐에 pop 한 횟수를 체크 해야 한다.
 */
public class Prog_듀_큐_합_같게_만들기 {

    //큐 합 구하기
    private static long sumQue(int[] queue){
        long result = 0;

        for(int que : queue){
            result += que;
        }

        return result;
    }

    private static void makeQue(int[] queue, Queue<Integer> que){


        for(int value : queue){
            que.add(value);
        }

    }

    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;

        long sum1 = sumQue(queue1);
        long sum2 = sumQue(queue2);
        int index1 = 0;
        int index2 = 0;
        Queue<Integer> que1 = new LinkedList<>();
        Queue<Integer> que2 = new LinkedList<>();
        makeQue(queue1, que1);
        makeQue(queue2, que2);


        while(sum1 != sum2){

            if(sum1 > sum2){

                if(que1.isEmpty()) break;

                int pop1 = que1.poll();
                que2.add(pop1);
                sum1 -= pop1; //첫번째 큐에서 값하나 뺴기.
                sum2 += pop1; //두번째 큐에 값 추가.
                index1++;
            }
            else if(sum1 < sum2){

                if(que2.isEmpty()) break;
                int pop2 = que2.poll();
                que1.add(pop2);
                sum1 += pop2;
                sum2 -= pop2;
                index2++;
            }

            if(index1 == queue1.length && index2 == queue2.length){
                answer = -1;
                break;
            }

            answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        Prog_듀_큐_합_같게_만들기 s = new Prog_듀_큐_합_같게_만들기();

        int[] queue11 = {3, 2, 7, 2};
        int[] queue21 = {4, 6, 5, 1};
        System.out.println(s.solution(queue11, queue21));

        int[] queue12 = {1, 2, 1, 2};
        int[] queue22 = {1, 10, 1, 2};
        System.out.println(s.solution(queue12, queue22));

        int[] queue13 = {1, 1};
        int[] queue23 = {1, 5};
        System.out.println(s.solution(queue13, queue23));
    }
}
