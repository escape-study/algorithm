package week09;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 비트마스킹 + dfs + dp (dfs + dp인데 경로를 표현하려면 비트마스킹이 필요하다.)
 * 우선, 모든 위치에서 다 탐색해볼 필요는 없다.
 * 순회할 수 있는 경로만 준다는 것은, 어떤 점을 택하더라도 자기자신으로 돌아올수 있는, 즉 사이클이 생긴다는 뜻이다.
 * 어떤점에서 측정하더라고 결국에는 최단 경로는 하나가 나온다.
 * 가령 1에서 시작해서 1 - 4 - 2 - 5 - 3 - 1 이라는 경로가 최단경로라면
 * 4에서 시작하더라도 4 - 2 - 5 - 3 - 1 - 4 가 되고, 결국 선택하는 간선 즉, 경로는 시작점만 다르지 동일한것을 알 수 있다.
 *
 *
 * (추가 - 시간초과 해결안)
 * 위의 방법만으로는 줄일 수 없었다.
 * dp를 이용해서 한번 탐색했다면 더이상 탐색하지 않는 방법을 강구해야 한다.
 * 위의 방법은 탐색시마다 지금까지 누적한 코스트를 저장하는식으로 했는데, 매번 배열에 저장하지 않고,
 * 모든 노드를 탐색했다면, 그때 리턴해서 저장하는 식으로 하는 것이 좋을 것 같다.
 * 가령 1 - 3 - 4 - 2 - 5 - 1 와 1 -4 - 3 - 2 - 5 - 1
 * 이 두 경로를 보면, 2 - 5 - 1 경로가 중복됨을 알 수 있다.
 * 이 경우는 한번 구해두면 2로 진입시에 5 - 1까지 경로는 구할 필요가 없어진다.
 */
public class BOJ2098_외판원_순회 {

    //최대값
    private final static int INF = Integer.MAX_VALUE;


    //도시 수.
    private static int N;

    //비트마스킹으로 표현할 최대 수
    private static int maxBit;

    //비용정보
    private static int[][] costArray;

    //각 방문 정보를 저장할 배열
    private static int[][] visited;

    //dfs할 메서드
    private static int dfs(int currentNode, int currentPath, int startNode){

        //모든 노드를 탐색했다면, 시작노드로 돌아가는 비용 저장 - 기존값 비교 필요 없음, 단 한가지 방법만 있기 떄문
        if(currentPath == maxBit){

            //해당위치에서 출발지로 이동 불가능하면 Max_Value로 표시(최대값으로 반환해주어야, min으로 걸러낼수 있어서 편함.)
            if(costArray[currentNode][startNode] == 0){
                return visited[currentNode][maxBit] = INF;
            }

            return visited[currentNode][maxBit] = costArray[currentNode][startNode];
        }

        //저장된 값이 있으면 반환.
        if(visited[currentNode][currentPath] != 0){
            return visited[currentNode][currentPath];
        }


        for(int nextNode = 0; nextNode < N; nextNode++) {

            int nextPath = currentPath | (1 << nextNode);

            //자기자신이거나 갈수 없는 곳이면 패스
            if (costArray[currentNode][nextNode] == 0 || visited[nextNode][nextPath] == INF) continue;

            //다음위치가 이미 탐색한 곳이라면 패스
            if (((1 << nextNode) & currentPath) != 0) continue;

            //다음 노드 재귀로 탐색.
            int tempCost =  dfs(nextNode, nextPath, startNode);

            //현재 위치에 저장된 값이 없으면 재귀호출에서 반환된 값 저장.
            if(visited[currentNode][currentPath] == 0){

                //INF가 반환된것은 도달 할 수 없다는 뜻.
                if(tempCost == INF){
                    visited[currentNode][currentPath] = INF;
                }
                else{
                    visited[currentNode][currentPath] = costArray[currentNode][nextNode] + tempCost;
                }

            }
            //값이 있으면 비교해서 작은 값 넣기
            else if(tempCost != INF){
                visited[currentNode][currentPath] = Math.min(visited[currentNode][currentPath], costArray[currentNode][nextNode] + tempCost);
            }
        }

        if(visited[currentNode][currentPath] == 0) return INF;

        return visited[currentNode][currentPath];
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        costArray = new int[N][N];
        visited = new int[N][1<<N];
        maxBit = (1<<N) - 1;

        for(int i = 0; i < N; i++){

            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                costArray[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int startNode = 0;
        int savePath = 1<<startNode;
        System.out.println(dfs(0, savePath, startNode));
    }
}
