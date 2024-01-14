package week23;

import java.util.Arrays;

/**
 * 아이디어
 * 구현
 * 그래프 문제라고 무작정 dfs,bfs로 해결하려고 하면 시간초과가 난다.
 * 1백만이고, 만약 각 그래프 별로 bfs탐색을 돌게 하면,시간초과가 날수도 있다.
 * 따라서 다른 방법을 생각해야 한다.
 * 그래프 유형별로 잘 보면, 특징이 있다.
 * 우선 각 그래프로 연결되는 정점은, 최소 두개 이상의 그래프로 연결된다.
 * 정점의 경우에는 진입간선은 없지만, 진출 간선이 2개 이상인 점을 찾으면 된다.
 * 도넛모양은 명확히 특징 지을수 없기 때문에, 다른 모양의 수를 세고, 전체에서 뺴면된다.
 * 막대모양은 진입1+진출0, 진입0+진출1이 두개 존재한다
 * 8자모양은 진출2+진입2가 하나 존재한다.
 */

public class Prog_도넛과_막대_그래프 {

    private static class Node{
        int inDegree, outDegree;
        boolean isUse;

        public Node(int inDegree, int outDegree){
            this.inDegree = inDegree;
            this.outDegree = outDegree;
            this.isUse = false;
        }

        public void addInDegree(){
            this.isUse = true;
            this.inDegree++;
        }
        public void addOutDegree(){
            this.isUse = true;
            this.outDegree++;
        }

        public int getInDegree() {
            return this.inDegree;
        }

        public int getOutDegree() {
            return this.outDegree;
        }
    }

    public int[] solution(int[][] edges) {
        int[] answer = new int[4];

        //최대 간선번호가 100만
        Node[] degreeArray = new Node[1_000_001];
        for(int i = 1; i < degreeArray.length; i++){
            degreeArray[i] = new Node(0,0);
        }
        //진출, 진입차수 기록.
        for(int[] edge : edges){
            degreeArray[edge[0]].addOutDegree();
            degreeArray[edge[1]].addInDegree();
        }


        int totalGraph = 0;//연결된 그래프 수
        int stickGraph = 0;//막대 그래프 수.
        int eightGraph = 0;//8자 그래프 수.

        for(int i = 1; i < degreeArray.length; i++){
            Node node = degreeArray[i];

            if(!node.isUse) continue;

            //생성된 번호와 정점수.
            if(node.getOutDegree() >= 2 && node.getInDegree() == 0){
                answer[0] = i;
                totalGraph = node.getOutDegree();
            }

            //막대그래프 수 - 진출차수가 없는 경우.
            if(node.getOutDegree() == 0){
                stickGraph++;
            }
            //8자 그래프수
            if(node.getOutDegree() == 2 && node.getInDegree() >= 2){
                eightGraph++;
            }
        }
        answer[1] = totalGraph - (stickGraph + eightGraph);
        answer[2] = stickGraph;
        answer[3] = eightGraph;

        return answer;
    }

    public static void main(String[] args) {
        Prog_도넛과_막대_그래프 s = new Prog_도넛과_막대_그래프();
        int[][] edges1 = {{2, 3}, {4, 3}, {1, 1}, {2, 1}};
        System.out.println(Arrays.toString(s.solution(edges1)));
    }
}
