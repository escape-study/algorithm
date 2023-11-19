package week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 1등 풀이와 3배차이나서 참고함
 * 기존에 반복문으로 하던 최소값 찾기를 Pq를 이용해서 개선한다.
 */
public class BOJ2461_대표선수_pq풀이 {

    private static class Node implements Comparable<Node>{
        int index,valueIndex, value;
        public Node(int index,int valueIndex, int value){
            this.index = index;
            this.value = value;
            this.valueIndex = valueIndex;

        }

        @Override
        public int compareTo(Node node) {
            return this.value - node.value;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] playerArray = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                playerArray[i][j] = Integer.parseInt(st.nextToken());
            }
            //배열 정렬.
            Arrays.sort(playerArray[i]);
        }


        int result = Integer.MAX_VALUE;

        PriorityQueue<Node> pq = new PriorityQueue<>();

        int tempMax = -1;
        //초기 데이터 저장
        for (int i = 0; i < N; i++) {
            pq.add(new Node(i, 0,playerArray[i][0]));

            tempMax = Math.max(tempMax, playerArray[i][0]);
        }



        //계속반복 - 단 0이면 종료.
        while(true){

            //큐에서 최소값 하나 꺼냄
            Node currentNode = pq.poll();

            int tempMinIndex = currentNode.index; //학급배열을 가리키는 인덱스.
            int tempMinValueIndex = currentNode.valueIndex;// 해당 학급에서 값이 있는 인덱스
            int tempMin = currentNode.value;

            result = Math.min(result, tempMax - tempMin);

            //0이면 이것보다 더 작은 차이는 존재하지 않음.
            if(result == 0) break;

            //최소값 인덱스를 증가시켜야 함. 최소값 인덱스를 더이상 증가시킬 수 없다면 종료.
            if(tempMinValueIndex == M - 1) break;

            tempMinValueIndex++;

            //최소값의 인덱스를 하나 증가시킨다음에 현재저장된 최대값과 비교.
            tempMax = Math.max(tempMax, playerArray[tempMinIndex][tempMinValueIndex]);

            pq.add(new Node(tempMinIndex, tempMinValueIndex, playerArray[tempMinIndex][tempMinValueIndex]));
        }


        System.out.println(result);
    }
}
